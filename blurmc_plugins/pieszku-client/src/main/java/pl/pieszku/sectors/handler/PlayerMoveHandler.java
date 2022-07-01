package pl.pieszku.sectors.handler;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.pieszku.api.sector.Sector;
import org.pieszku.api.sector.SectorService;
import org.pieszku.api.service.UserService;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.holder.BorderHolder;
import pl.pieszku.sectors.service.MasterConnectionHeartbeatService;
import pl.pieszku.sectors.transfer.TransferPlayerSectorUtilities;
import pl.pieszku.sectors.utilities.ChatUtilities;

public class PlayerMoveHandler implements Listener {

    private final UserService userService = BukkitMain.getInstance().getUserService();
    private final SectorService sectorService = BukkitMain.getInstance().getSectorService();
    private final MasterConnectionHeartbeatService masterConnectionHeartbeatService = BukkitMain.getInstance().getMasterConnectionHeartbeatService();
    private final TransferPlayerSectorUtilities transferPlayerSectorUtilities = new TransferPlayerSectorUtilities();

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location from = event.getFrom();
        Location to = event.getTo();
        if (from.getBlockX() == to.getBlockX() && from.getBlockY() == to.getBlockY() && from.getBlockZ() == to.getBlockZ())
            return;
        if (!masterConnectionHeartbeatService.isConnected()) {
            event.setTo(from.clone().setDirection(to.clone().getDirection()));
            return;
        }
        BorderHolder.update(player);
        this.sectorService.getSectorByLocation(to.getWorld().getName(), to.getBlockX(), to.getBlockZ()).ifPresent(sector -> {
            this.transferPlayerSectorUtilities.addTransferPlayer(player, sector, to);
        });

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        Location location = event.getTo();


        if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.END_PORTAL)) {
            event.setCancelled(true);
            player.sendTitle(ChatUtilities.colored("&d&lENDER-PORTAL"), ChatUtilities.colored("&cAktualnie jest wyłączony!"));
            return;
        }
        if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL)) {
            event.setCancelled(true);
            player.sendTitle(ChatUtilities.colored("&d&lNETHER-PORTAL"), ChatUtilities.colored("&cAktualnie jest wyłączony!"));
            return;
        }

        if (masterConnectionHeartbeatService.isConnected()) {
            this.sectorService.getSectorByLocation(location.getWorld().getName(), location.getBlockX(), location.getBlockZ()).ifPresent(sector -> {

                this.userService.findUserByNickName(player.getName()).ifPresent(user -> {


                    if(!user.getUserAntiLogout().hasAntiLogout()){
                        player.sendMessage(ChatUtilities.colored("&4Błąd: &cPodczas walki nie można zmieniać sektora!"));
                        event.setCancelled(true);
                        return;
                    }

                    if (!sector.isOnline()) {
                        if (sector.isSpawn()) {
                            Sector findSector = this.sectorService.findSectorSpawn();
                            if (findSector == null) {
                                event.setCancelled(true);
                                player.sendMessage(ChatUtilities.colored("&4Blad: &cWszystkie sektory &4spawn &csa aktualnie offline!"));
                                return;
                            }
                            this.transferPlayerSectorUtilities.addTransferPlayer(player, findSector, location);
                            return;
                        }
                        player.sendMessage(ChatUtilities.colored("&4&lSEKTOR&8: &cAktualnie sektor: &4" + sector.getName() + " &cjest offline!"));
                        event.setCancelled(true);
                        return;
                    }
                    this.transferPlayerSectorUtilities.addTransferPlayer(player, sector, location);
                });
            });

        }
    }
}

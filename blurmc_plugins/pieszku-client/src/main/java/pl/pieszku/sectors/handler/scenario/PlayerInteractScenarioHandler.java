package pl.pieszku.sectors.handler.scenario;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.material.Button;
import org.bukkit.potion.PotionEffect;
import org.pieszku.api.sector.Sector;
import org.pieszku.api.service.UserService;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.helper.RandomTeleportHelper;
import pl.pieszku.sectors.inventory.EnderchestInventory;
import pl.pieszku.sectors.service.MasterConnectionHeartbeatService;
import pl.pieszku.sectors.utilities.ChatUtilities;
import pl.pieszku.sectors.utilities.RandomUtilities;

public class PlayerInteractScenarioHandler implements Listener {

    private final UserService userService = BukkitMain.getInstance().getUserService();
    private final MasterConnectionHeartbeatService masterConnectionHeartbeatService = BukkitMain.getInstance().getMasterConnectionHeartbeatService();
    private final EnderchestInventory enderchestInventory = new EnderchestInventory();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onOpenInventory(InventoryOpenEvent event){
        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();

        this.userService.findUserByNickName(player.getName()).ifPresent(user -> {
        if(inventory.equals(player.getEnderChest())) {
            if (!user.getUserAntiLogout().hasAntiLogout()) {
                event.setCancelled(true);
                player.sendMessage(ChatUtilities.colored("&4Błąd: &cPodczas walki nie można otwierać enderchesta!"));
                return;
            }
            event.setCancelled(true);
            this.enderchestInventory.show(player);
        }
        });
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        if (!masterConnectionHeartbeatService.isConnected()) {
            event.setCancelled(true);
            return;
        }

        Player player = event.getPlayer();
        this.userService.findUserByNickName(player.getName()).ifPresent(user -> {
            if (!user.isInteractSector()) {
                event.setCancelled(true);
            }
        });
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInteractEntity(PlayerInteractAtEntityEvent event) {
        if (!masterConnectionHeartbeatService.isConnected()) {
            event.setCancelled(true);
            return;
        }

        Player player = event.getPlayer();
        this.userService.findUserByNickName(player.getName()).ifPresent(user -> {
            if (!user.isInteractSector()) {
                event.setCancelled(true);
            }
        });
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onArmorStandManipulate(PlayerArmorStandManipulateEvent event) {
        if (!masterConnectionHeartbeatService.isConnected()) {
            event.setCancelled(true);
            return;
        }

        Player player = event.getPlayer();
        this.userService.findUserByNickName(player.getName()).ifPresent(user -> {
            if (!user.isInteractSector()) {
                event.setCancelled(true);
            }
        });
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInteract(PlayerInteractEvent event) {
        if (!masterConnectionHeartbeatService.isConnected()) {
            event.setCancelled(true);
            return;
        }

        Player player = event.getPlayer();
        this.userService.findUserByNickName(player.getName()).ifPresent(user -> {
            if (!user.isInteractSector()) {
                event.setCancelled(true);
            }
        });
        if (!event.hasBlock()) {
            return;
        }
        Block clicked = event.getClickedBlock();


        if (clicked.getType() == Material.STONE_BUTTON) {
            Button btn = (Button) clicked.getState().getData();
            Block base = clicked.getRelative(btn.getAttachedFace());
            if (base.getType() != Material.JUKEBOX) {
                return;
            }
            if (BukkitMain.getInstance().getCurrentSector().get().isSpawn()) {
                randomTp(event.getPlayer());
            }
        }
        clicked = event.getClickedBlock();
        if (clicked.getType() == Material.OAK_BUTTON) {
            Button btn = (Button) clicked.getState().getData();
            Block base = clicked.getRelative(btn.getAttachedFace());
            if (base.getType() != Material.JUKEBOX) {
                return;
            }

            if (event.getClickedBlock().getState().getLocation().distance(player.getLocation()) > 2) {
                player.sendMessage(ChatUtilities.colored("&4Blad: &cPrzybliż się do guzika"));
                return;
            }

            Sector sector = BukkitMain.getInstance().getSectorService().findSectorTeleport();
            if (sector == null) {
                event.setCancelled(true);
                player.sendMessage(ChatUtilities.colored("&4Blad: &cNie mozna odnależć sektora teparkowego!!"));
                return;
            }
            new RandomTeleportHelper(player, player.getWorld(), -1500, 1500, -1500, 1500).apply(randomTeleportHelper -> {
                int x = RandomUtilities.getRandInt((int) Math.min(sector.getLocationMinimum().getX(), sector.getLocationMaximum().getX()), (int) Math.max(sector.getLocationMinimum().getX(), sector.getLocationMaximum().getX()));
                int z = RandomUtilities.getRandInt((int) Math.min(sector.getLocationMinimum().getZ(), sector.getLocationMaximum().getZ()), (int) Math.max(sector.getLocationMinimum().getZ(), sector.getLocationMaximum().getZ()));
                double y = player.getWorld().getHighestBlockYAt(x, z) + 1.5f;
                randomTeleportHelper.setFoundedLocation(new Location(player.getWorld(), x, y, z));
                randomTeleportHelper.setCurrent(event.getClickedBlock().getState().getLocation());

                randomTeleportHelper.findPlayers(5, 2);

                if (randomTeleportHelper.getPlayerList().size() < 2) {
                    player.sendMessage(ChatUtilities.colored("&3&lGrupowyTeleport &8>> &fPoczekaj na swojego przeciwnika!"));
                    return;
                }
                randomTeleportHelper.sendMessagePlayers("&3&lGrupowyTeleport &8>> &fNa pojedynek ida: ");
                for (Player players : randomTeleportHelper.getPlayerList()) {
                    randomTeleportHelper.sendMessagePlayers("&8- &b" + players.getName());

                    for (PotionEffect effect : players.getActivePotionEffects())
                        players.removePotionEffect(effect.getType());
                }
                if (BukkitMain.getInstance().getCurrentSector().get().isSpawn()) {
                    randomTeleportHelper.teleport();
                }
            });
        }

    }

    public static void randomTp(Player player) {
        int x = RandomUtilities.getRandInt(-1500, 1500);
        int z = RandomUtilities.getRandInt(-1500, 1500);
        double y = player.getWorld().getHighestBlockYAt(x, z) + 1.5f;
        Location loc = new Location(player.getWorld(), x, y, z);
        player.teleport(loc, PlayerTeleportEvent.TeleportCause.PLUGIN);
    }
}

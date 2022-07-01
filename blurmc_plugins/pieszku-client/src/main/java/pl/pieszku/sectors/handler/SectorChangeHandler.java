package pl.pieszku.sectors.handler;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;
import org.pieszku.api.sector.Sector;
import org.pieszku.api.service.UserService;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.handler.events.SectorChangeEvent;
import pl.pieszku.sectors.transfer.TransferPlayerSectorUtilities;
import pl.pieszku.sectors.utilities.ChatUtilities;

public class SectorChangeHandler implements Listener {


    private final UserService userService = BukkitMain.getInstance().getUserService();
    private final TransferPlayerSectorUtilities transferPlayerSectorUtilities = new TransferPlayerSectorUtilities();

    @EventHandler
    public void onSectorChange(SectorChangeEvent event){
        if(event.isCancelled())return;
        Player player = event.getPlayer();
        Location location = event.getLocation();
        Sector sector = event.getSector();

        this.userService.findUserByNickName(player.getName()).ifPresent(user -> {

            if(BukkitMain.getInstance().getCurrentSector().get().equals(sector))return;

            if(user.getChangeSector() > System.currentTimeMillis()){
                return;
            }

            if(!user.getUserAntiLogout().hasAntiLogout()){
                Location locationKnockBack = new Location(player.getWorld(), (sector.getLocationMinimum().getX() + sector.getLocationMaximum().getX()) / 2, 80, (sector.getLocationMinimum().getZ() + sector.getLocationMaximum().getZ()) / 2);
                this.knockBackPlayer(player, locationKnockBack);
                player.sendMessage(ChatUtilities.colored("&4Błąd: &cPodczas walki nie można zmieniać sektora!"));
                event.setCancelled(true);
                return;
            }
            user.setInteractSector(false);
            Bukkit.getScheduler().scheduleSyncDelayedTask(BukkitMain.getInstance(), () -> this.transferPlayerSectorUtilities.transferPlayer(player, sector, location));
        });

    }
    public void knockBackPlayer(Player player, Location location) {
        Location locationVector = player.getLocation().subtract(location);
        double distance = player.getLocation().distance(location);
        if ((1.0 / distance <= 0)) return;
        if (distance <= 0) return;
        player.setVelocity(locationVector.toVector().add(new Vector(0, 0.10, 0)).multiply(1.20 / distance));
    }
}

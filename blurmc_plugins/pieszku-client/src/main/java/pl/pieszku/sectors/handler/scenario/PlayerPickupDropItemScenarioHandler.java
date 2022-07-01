package pl.pieszku.sectors.handler.scenario;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.pieszku.api.service.UserService;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.service.MasterConnectionHeartbeatService;
import pl.pieszku.sectors.utilities.ChatUtilities;

public class PlayerPickupDropItemScenarioHandler implements Listener {

    private final UserService userService = BukkitMain.getInstance().getUserService();
    private final MasterConnectionHeartbeatService masterConnectionHeartbeatService = BukkitMain.getInstance().getMasterConnectionHeartbeatService();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPickupItem(PlayerPickupItemEvent event){
        if (!masterConnectionHeartbeatService.isConnected()) {
            event.setCancelled(true);
            return;
        }
        if(BukkitMain.getInstance().getCurrentSector().get().isSpawn()){
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatUtilities.colored("&4Błąd: &cNa spawnie nie można tego zrobić!"));
            return;
        }

        Player player = event.getPlayer();
        this.userService.findUserByNickName(player.getName()).ifPresent(user -> {
            if(!user.isInteractSector()){
                event.setCancelled(true);
            }
        });
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void onDropItem(PlayerDropItemEvent event){
        if (!masterConnectionHeartbeatService.isConnected()) {
            event.setCancelled(true);
            return;
        }
        if(BukkitMain.getInstance().getCurrentSector().get().isSpawn()){
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatUtilities.colored("&4Błąd: &cNa spawnie nie można tego zrobić!"));
            return;
        }

        Player player = event.getPlayer();
        this.userService.findUserByNickName(player.getName()).ifPresent(user -> {
            if(!user.isInteractSector()){
                event.setCancelled(true);
            }
        });
    }
}

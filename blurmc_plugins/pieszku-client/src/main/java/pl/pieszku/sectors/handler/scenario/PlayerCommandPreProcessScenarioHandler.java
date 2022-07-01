package pl.pieszku.sectors.handler.scenario;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.pieszku.api.service.UserService;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.service.MasterConnectionHeartbeatService;

public class PlayerCommandPreProcessScenarioHandler implements Listener {

    private final UserService userService = BukkitMain.getInstance().getUserService();
    private final MasterConnectionHeartbeatService masterConnectionHeartbeatService = BukkitMain.getInstance().getMasterConnectionHeartbeatService();

    @EventHandler(priority = EventPriority.HIGH)
    public void onCommandPreProcess(PlayerCommandPreprocessEvent event){
        if (!masterConnectionHeartbeatService.isConnected()) {
            event.setCancelled(true);
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

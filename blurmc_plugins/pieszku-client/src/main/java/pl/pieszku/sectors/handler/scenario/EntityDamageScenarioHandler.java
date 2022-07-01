package pl.pieszku.sectors.handler.scenario;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.pieszku.api.service.UserService;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.service.MasterConnectionHeartbeatService;
import pl.pieszku.sectors.utilities.ChatUtilities;

public class EntityDamageScenarioHandler implements Listener {

    private final UserService userService = BukkitMain.getInstance().getUserService();
    private final MasterConnectionHeartbeatService masterConnectionHeartbeatService = BukkitMain.getInstance().getMasterConnectionHeartbeatService();

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
        if (!masterConnectionHeartbeatService.isConnected()) {
            event.setCancelled(true);
            return;
        }
        if(BukkitMain.getInstance().getCurrentSector().get().isSpawn()){
            event.setCancelled(true);
            event.getDamager().sendMessage(ChatUtilities.colored("&4Błąd: &cNa spawnie nie można tego zrobić!"));
            return;
        }
        if(event.getEntity() instanceof Player && event.getDamager() instanceof Player){
            this.userService.findUserByNickName(event.getEntity().getName()).ifPresent(user -> {
                if(!user.isInteractSector()){
                    event.setCancelled(true);
                }
            });
            this.userService.findUserByNickName(event.getDamager().getName()).ifPresent(user -> {
                if(!user.isInteractSector()){
                    event.setCancelled(true);
                }
            });
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDamage(EntityDamageEvent event){
        if (!masterConnectionHeartbeatService.isConnected()) {
            event.setCancelled(true);
            return;
        }
        if(BukkitMain.getInstance().getCurrentSector().get().isSpawn()){
            event.setCancelled(true);
            return;
        }

        if(event.getEntity() instanceof Player){
            this.userService.findUserByNickName(event.getEntity().getName()).ifPresent(user -> {
                if(!user.isInteractSector()){
                    event.setCancelled(true);
                }
            });
        }
    }
}

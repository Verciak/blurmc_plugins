package pl.pieszku.sectors.handler.guild;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.pieszku.api.objects.guild.impl.Guild;
import org.pieszku.api.service.GuildService;
import org.pieszku.api.service.UserService;
import org.pieszku.api.type.TimeType;
import org.pieszku.api.utilities.DataUtilities;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.util.Optional;

public class GuildEntityDamageHandler implements Listener {


    private final GuildService guildService = BukkitMain.getInstance().getGuildService();
    private final UserService userService = BukkitMain.getInstance().getUserService();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
        if(event.isCancelled())return;
        if(event.getDamager() instanceof Player && event.getEntity() instanceof Player){

            Player damagerPlayer = (Player) event.getDamager();
            Player entityPlayer = (Player) event.getEntity();

            Optional<Guild> guildEntityOptional = this.guildService.findGuildByMember(entityPlayer.getName());
            Optional<Guild> guildDamagerOptional = this.guildService.findGuildByMember(damagerPlayer.getName());

            Guild guildDamager= null;
            Guild guildEntity = null;

            if(guildDamagerOptional.isPresent() && guildEntityOptional.isPresent()){
                 guildDamager = guildDamagerOptional.get();
                 guildEntity = guildEntityOptional.get();

            }
            if(guildEntity != null && guildEntity.equals(guildDamager) && !guildEntity.isFriendlyFire()){
                event.setDamage(0);
                return;
            }

            this.userService.findUserByNickName(damagerPlayer.getName()).ifPresent(userDamager -> {
                this.userService.findUserByNickName(entityPlayer.getName()).ifPresent(userEntity -> {

                    if(!userDamager.hasProtection()){
                        event.setCancelled(true);
                        damagerPlayer.sendMessage(ChatUtilities.colored("&6&lOCHRONA&8: &fPosiadasz ochrone startową przez: &e" + DataUtilities.getTimeToString(userDamager.getProtectionTime())));
                        return;
                    }
                    if(!userEntity.hasProtection()){
                        event.setCancelled(true);
                        damagerPlayer.sendMessage(ChatUtilities.colored("&6&lOCHRONA&8: &fTen gracz posiada ochrone startową przez: &e" + DataUtilities.getTimeToString(userEntity.getProtectionTime())));
                        return;
                    }

                    userDamager.getUserAntiLogout().setAttackerNickName(userEntity.getNickName());
                    userEntity.getUserAntiLogout().setAttackerNickName(userDamager.getNickName());
                    userDamager.getUserAntiLogout().setAntiLogoutTime(System.currentTimeMillis() + TimeType.SECOND.getTime(20));
                    userEntity.getUserAntiLogout().setAntiLogoutTime(System.currentTimeMillis() + TimeType.SECOND.getTime(20));
                });
            });


        }
    }
}

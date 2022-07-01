package pl.pieszku.sectors.runnable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.pieszku.api.objects.guild.impl.Guild;
import org.pieszku.api.service.UserService;
import org.pieszku.api.utilities.DataUtilities;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.cache.BukkitCache;
import pl.pieszku.sectors.impl.UserActionBarType;
import pl.pieszku.sectors.utilities.TablistUtilities;

public class UserInformationSynchronizeUpdateRunnable implements Runnable{

    private final UserService userService = BukkitMain.getInstance().getUserService();
    private final BukkitCache bukkitCache = BukkitMain.getInstance().getBukkitCache();
    private final TablistUtilities tablistUtilities = BukkitMain.getInstance().getTablistUtilities();

    public void start(){
        Bukkit.getScheduler().runTaskTimer(BukkitMain.getInstance(), this, 1, 20L);
    }

    @Override
    public void run() {
        for(Player player : Bukkit.getOnlinePlayers()){
            this.tablistUtilities.update(this.bukkitCache.findBukkitUserByNickName(player.getName()));
            this.userService.findUserByNickName(player.getName()).ifPresent(user -> {


                Guild guild = BukkitMain.getInstance().getGuildService().findGuildByMemberGet(player.getName());
                BukkitMain.getInstance().getTeamService().updateBoard(player, guild, user);

                this.bukkitCache.findBukkitUserByNickName(player.getName()).ifPresent(bukkitUser -> {

                    if(!user.getUserAntiLogout().hasAntiLogout()){
                        bukkitUser.updateActionBar(UserActionBarType.ANTILOGOUT, " &c&lANTYLOGOUT &8:: &fTrwa jeszcze&8(&c" + DataUtilities.getTimeToString(user.getUserAntiLogout().getAntiLogoutTime()) + "&7, &c" + user.getUserAntiLogout().getAttackerNickName() + "&8) ");
                    }else{
                        bukkitUser.removeActionBar(UserActionBarType.ANTILOGOUT);
                    }
                    if(!user.hasProtection()){
                        bukkitUser.updateActionBar(UserActionBarType.PROTECTION, " &6&lOCHRONA &8:: &fTrwa jeszcze&8(&e" + DataUtilities.getTimeToString(user.getProtectionTime()) + "&8) ");
                    }else{
                        bukkitUser.removeActionBar(UserActionBarType.PROTECTION);
                    }

                });
            });
        }
    }
}

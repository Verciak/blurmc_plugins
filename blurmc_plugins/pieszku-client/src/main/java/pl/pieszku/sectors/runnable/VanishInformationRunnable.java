package pl.pieszku.sectors.runnable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.pieszku.api.objects.user.User;
import org.pieszku.api.service.UserService;
import org.pieszku.api.type.GroupType;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.cache.BukkitCache;
import pl.pieszku.sectors.impl.UserActionBarType;

public class VanishInformationRunnable implements Runnable {

    private final UserService userService = BukkitMain.getInstance().getUserService();
    private final BukkitCache bukkitCache = BukkitMain.getInstance().getBukkitCache();

    public void start(){
        Bukkit.getScheduler().runTaskTimer(BukkitMain.getInstance(), this, 0, 0);
    }

    @Override
    public void run() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {

            for (User user : this.userService.getUserMap().values()) {
                this.userService.findUserByNickName(onlinePlayer.getName()).filter(userTarget -> GroupType.hasPermission(userTarget, GroupType.HELPER)).ifPresent(userTarget -> {
                    if (!userTarget.isVanish()) {
                        this.bukkitCache.findBukkitUserByNickName(userTarget.getNickName()).ifPresent(bukkitUser -> {
                            bukkitUser.removeActionBar(UserActionBarType.VANISH);
                        });
                        return;
                    }
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        if(!GroupType.hasPermission(user, GroupType.HELPER)) {
                            player.hidePlayer(onlinePlayer);
                        }
                    });
                    this.bukkitCache.findBukkitUserByNickName(userTarget.getNickName()).ifPresent(bukkitUser -> {
                        bukkitUser.updateActionBar(UserActionBarType.VANISH, " &b&lVANISH &3&l:: &aJesteś aktualnie niewidzialny&8. ");
                    });
                });
            }
        }
    }
}

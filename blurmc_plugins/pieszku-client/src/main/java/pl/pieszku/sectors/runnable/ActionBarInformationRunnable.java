package pl.pieszku.sectors.runnable;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.cache.BukkitCache;
import pl.pieszku.sectors.utilities.ChatUtilities;

public class ActionBarInformationRunnable implements Runnable{


    private final BukkitCache bukkitCache = BukkitMain.getInstance().getBukkitCache();

    public void start(){
        Bukkit.getScheduler().runTaskTimer(BukkitMain.getInstance(), this, 0, 0);
    }

    @Override
    public void run() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.setMaximumNoDamageTicks(20);

            this.bukkitCache.findBukkitUserByNickName(onlinePlayer.getName()).ifPresent(bukkitUser -> {


                onlinePlayer.spigot().sendMessage(net.md_5.bungee.api.ChatMessageType.ACTION_BAR, new TextComponent(
                        String.join( "::", ChatUtilities.colored(bukkitUser.collectActiveActionBars()))));
            });
        }
    }
}

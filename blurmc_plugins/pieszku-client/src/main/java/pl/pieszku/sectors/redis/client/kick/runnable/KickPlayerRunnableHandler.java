package pl.pieszku.sectors.redis.client.kick.runnable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.utilities.ChatUtilities;

public class KickPlayerRunnableHandler implements Runnable {

    private final Player player;
    private final String reason;

    public KickPlayerRunnableHandler(Player  player, String reason){
        this.player = player;
        this.reason = reason;
    }

    public void start(){
        Bukkit.getScheduler().runTaskLater(BukkitMain.getInstance(), this, 1L);
    }

    @Override
    public void run() {
        this.player.kickPlayer(ChatUtilities.colored(reason));
    }
}

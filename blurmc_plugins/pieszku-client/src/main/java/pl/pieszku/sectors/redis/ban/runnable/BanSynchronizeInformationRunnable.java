package pl.pieszku.sectors.redis.ban.runnable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.pieszku.api.objects.ban.Ban;
import org.pieszku.api.utilities.DataUtilities;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.utilities.ChatUtilities;

public class BanSynchronizeInformationRunnable implements Runnable {

    private final Player player;
    private final Ban ban;

    public BanSynchronizeInformationRunnable(Player player, Ban ban) {
        this.player = player;
        this.ban = ban;
    }

    @Override
    public void run() {
        player.kickPlayer(ChatUtilities.colored(
                "   &cZostałeś zbanowany\n" +
                        "&7Powód: &c" +ban.getReason()  + "\n" +
                        "&7Admininstrator: &c" + ban.getAdminNickName() + "\n" +
                        "&7Wygasa za: &c" + DataUtilities.getTimeToString(ban.getTempDelay()) + "\n" +
                        "&d\n" +
                        "   &cNiesluszny ban? udaj się na discord: &4dc.blurmc.pl\n" +
                        "       &club zakup unban na: &4www.blurmc.pl"));
    }

    public void start() {
        Bukkit.getScheduler().runTaskLater(BukkitMain.getInstance(), this, 1L);
    }
}

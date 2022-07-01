package pl.pieszku.sectors.runnable;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.utilities.ChatUtilities;

public class AlphaTestInformationRunnable implements Runnable{

    private float progress;
    private final BossBar bossBar;

    public void start() {
        Bukkit.getScheduler().runTaskTimer(BukkitMain.getInstance(), this, 0, 0);
    }
    public AlphaTestInformationRunnable(){
        this.progress = 1;
        this.bossBar = Bukkit.createBossBar(ChatUtilities.colored("&4&l:: &c&lALPHA-TESTY &4&l::"), BarColor.RED, BarStyle.SOLID, BarFlag.DARKEN_SKY);
        this.bossBar.setProgress(this.progress);
    }

    @Override
    public void run() {
        float progressMinus = this.progress -= 0.01;
        boolean updateMinus = (this.progress <= 1.00 && this.progress < -0);
        this.progress = updateMinus ? 1 : progressMinus;
        Bukkit.getOnlinePlayers().forEach(player -> {

            if(!this.bossBar.getPlayers().contains(player)){
                this.bossBar.addPlayer(player);
            }
            this.bossBar.setProgress(this.progress);
        });
    }
}

package pl.pieszku.sectors.runnable;

import org.bukkit.Bukkit;
import org.pieszku.api.utilities.DataUtilities;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.service.MasterConnectionHeartbeatService;
import pl.pieszku.sectors.utilities.ChatUtilities;

public class MasterConnectionCheckTask implements Runnable{

    private final MasterConnectionHeartbeatService masterConnectionHeartbeatService = BukkitMain.getInstance().getMasterConnectionHeartbeatService();

    @Override
    public void run() {
        if (masterConnectionHeartbeatService.isConnected()) return;

        Bukkit.getOnlinePlayers().forEach(player -> {
        ChatUtilities.sendActionBar(player, "&4&l:: &c&lAWARIA &4&l:: &fTrzeba poczekać aż serwer główny zostanie zrestartowany...");

            player.sendTitle(ChatUtilities.colored("&4&l:: &c&lAWARIA &4&l::"),
                    ChatUtilities.colored("&fSerwer główny od sektorów nie odpowiada od&8(&c" + DataUtilities.getTimeToString(
                            System.currentTimeMillis() - masterConnectionHeartbeatService.getLastHeartbeat(), false) + "&8)"));
     });
    }

    public void start(){
        BukkitMain.getInstance().getServer().getScheduler().runTaskTimerAsynchronously(BukkitMain.getInstance(), this, 1L, 20L);
    }
}

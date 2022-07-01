package pl.pieszku.sectors.runnable;

import org.bukkit.Bukkit;
import org.pieszku.api.redis.packet.sector.sync.SectorInformationSynchronizePacket;
import org.pieszku.api.type.TimeType;
import pl.pieszku.sectors.BukkitMain;

public class SectorSynchronizeRunnable implements Runnable{

    @Override
    public void run() {
        BukkitMain.getInstance().getCurrentSector().ifPresent(sector -> {
            sector.getPlayers().clear();
            Bukkit.getOnlinePlayers().forEach(onlinePlayer -> sector.getPlayers().add(onlinePlayer.getName()));
            new SectorInformationSynchronizePacket(sector.getName(), sector.getPlayers(), true, System.currentTimeMillis() + TimeType.SECOND.getTime(3))
                    .sendToChannel("MASTER");
        });
    }
    public void start(){
        BukkitMain.getInstance().getServer().getScheduler().runTaskTimer(BukkitMain.getInstance(), this, 1L, 1L);
    }
}

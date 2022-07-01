package org.pieszku.server.runnable;

import org.pieszku.api.redis.packet.sector.sync.SectorInformationDiscordSynchronizePacket;
import org.pieszku.api.redis.packet.sector.sync.SectorInformationSynchronizePacket;
import org.pieszku.api.sector.SectorService;
import org.pieszku.server.ServerMain;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SectorInformationRunnable implements Runnable{

    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    private final SectorService sectorService = ServerMain.getInstance().getSectorService();

    public void start() {
        this.scheduledExecutorService.scheduleAtFixedRate(this, 1,1, TimeUnit.MILLISECONDS);
    }

    @Override
    public void run() {
        this.sectorService.getSectorList().forEach(sector -> {
            if(sector.getLatestInformation() <= System.currentTimeMillis()) {
                sector.setLatestInformation(0);
                sector.setOnline(false);
                sector.setPlayers(new ArrayList<>());
            }
            new SectorInformationSynchronizePacket(sector.getName(), sector.getPlayers(), sector.isOnline(), sector.getLatestInformation()).sendToAllSectors(this.sectorService);
            new SectorInformationDiscordSynchronizePacket(sector.getName(), sector.getPlayers(), sector.isOnline(), sector.getLatestInformation()).sendToChannel("DISCORD");
        });
    }
}

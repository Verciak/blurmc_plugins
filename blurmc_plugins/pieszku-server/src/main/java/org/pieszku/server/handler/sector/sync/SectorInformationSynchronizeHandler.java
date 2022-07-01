package org.pieszku.server.handler.sector.sync;

import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.sector.sync.SectorInformationSynchronizePacket;
import org.pieszku.api.sector.SectorService;
import org.pieszku.server.ServerMain;

public class SectorInformationSynchronizeHandler extends RedisListener<SectorInformationSynchronizePacket> {

    private final SectorService sectorService = ServerMain.getInstance().getSectorService();

    public SectorInformationSynchronizeHandler() {
        super("MASTER", SectorInformationSynchronizePacket.class);
    }

    @Override
    public void onDecode(SectorInformationSynchronizePacket packet) {
        this.sectorService.findSectorByName(packet.getSectorName()).ifPresent(sector -> {
            sector.setPlayers(packet.getPlayers());
            sector.setOnline(packet.isOnline());
            sector.setLatestInformation(packet.getLatestInformation());
        });
    }
}

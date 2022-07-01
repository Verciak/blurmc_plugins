package org.pieszku.server.handler.sector;

import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.sector.request.SectorInformationRequestPacket;
import org.pieszku.api.redis.packet.sector.sync.SectorInformationSynchronizePacket;
import org.pieszku.api.sector.SectorService;
import org.pieszku.server.ServerMain;

public class SectorInformationRequestHandler extends RedisListener<SectorInformationRequestPacket> {


    private final SectorService sectorService = ServerMain.getInstance().getSectorService();

    public SectorInformationRequestHandler(){
        super("MASTER", SectorInformationRequestPacket.class);
    }

    @Override
    public void onDecode(SectorInformationRequestPacket packet) {
        this.sectorService.findSectorByName(packet.getSectorRequestName()).ifPresent(sector ->{
            new SectorInformationSynchronizePacket(sector.getName(), sector.getPlayers(), sector.isOnline(), sector.getLatestInformation())
                    .sendToSector(packet.getSectorSenderName());
        });
    }
}

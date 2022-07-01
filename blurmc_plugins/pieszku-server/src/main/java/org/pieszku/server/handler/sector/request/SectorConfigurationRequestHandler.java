package org.pieszku.server.handler.sector.request;

import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.sector.SectorConfigurationPacket;
import org.pieszku.api.redis.packet.sector.request.SectorConfigurationRequestPacket;
import org.pieszku.api.sector.SectorService;
import org.pieszku.api.sector.json.SectorJson;
import org.pieszku.server.ServerMain;

import java.util.logging.Logger;

public class SectorConfigurationRequestHandler extends RedisListener<SectorConfigurationRequestPacket> {


    private final SectorService sectorService = ServerMain.getInstance().getSectorService();
    private final SectorJson sectorJson = ServerMain.getInstance().getSectorJson();


    public SectorConfigurationRequestHandler() {
        super("MASTER", SectorConfigurationRequestPacket.class);
    }

    @Override
    public void onDecode(SectorConfigurationRequestPacket packet) {
        new SectorConfigurationPacket(packet.getSenderSectorName(), this.sectorJson).sendToSector(packet.getSenderSectorName());
        Logger.getAnonymousLogger().info("[MASTER-SERVER] Server received request dependency add: " + packet.getSenderSectorName());
    }
}

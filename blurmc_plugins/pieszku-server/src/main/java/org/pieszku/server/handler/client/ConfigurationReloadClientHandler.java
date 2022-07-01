package org.pieszku.server.handler.client;

import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.client.load.ConfigurationLoadClientInformationPacket;
import org.pieszku.api.redis.packet.client.load.ConfigurationReloadClientPacket;
import org.pieszku.api.sector.SectorService;
import org.pieszku.server.ServerMain;

public class ConfigurationReloadClientHandler extends RedisListener<ConfigurationReloadClientPacket> {


    private final SectorService sectorService = ServerMain.getInstance().getSectorService();

    public ConfigurationReloadClientHandler() {
        super("MASTER", ConfigurationReloadClientPacket.class);
    }

    @Override
    public void onDecode(ConfigurationReloadClientPacket packet) {
        ServerMain.getInstance().setupConfiguration();
        new ConfigurationLoadClientInformationPacket(ServerMain.getInstance().getConfigurationData()).sendToAllSectors(this.sectorService);
    }
}

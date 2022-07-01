package org.pieszku.server.handler.sector.request;

import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.sector.SectorConfigurationPacket;
import org.pieszku.api.redis.packet.sector.request.SectorConfigurationDiscordRequestPacket;
import org.pieszku.server.ServerMain;

public class SectorConfigurationDiscordRequestHandler extends RedisListener<SectorConfigurationDiscordRequestPacket> {


    public SectorConfigurationDiscordRequestHandler() {
        super("MASTER", SectorConfigurationDiscordRequestPacket.class);
    }

    @Override
    public void onDecode(SectorConfigurationDiscordRequestPacket packet) {
        new SectorConfigurationPacket("DISCORD", ServerMain.getInstance().getSectorJson()).sendToChannel(packet.getChannelRequestInfo());
        System.out.println("[MASTER-SERVER] Success sending packet discord-sectors configuration...");
    }
}

package org.pieszku.api.redis.packet.sector.request;

import org.pieszku.api.redis.packet.Packet;

public class SectorConfigurationDiscordRequestPacket extends Packet {

    private final String channelRequestInfo;

    public SectorConfigurationDiscordRequestPacket(String channelRequestInfo){
        this.channelRequestInfo = channelRequestInfo;
    }

    public String getChannelRequestInfo() {
        return channelRequestInfo;
    }

    @Override
    public String toString() {
        return "SectorConfigurationDiscordRequestPacket{" +
                "channelRequestInfo='" + channelRequestInfo + '\'' +
                '}';
    }
}

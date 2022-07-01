package org.pieszku.api.redis.packet.whitelist.request;

import org.pieszku.api.redis.packet.Packet;

public class WhitelistLoadInformationRequestPacket extends Packet {

    private final String channelName;

    public WhitelistLoadInformationRequestPacket(String channelName){
        this.channelName = channelName;
    }

    public String getChannelName() {
        return channelName;
    }

    @Override
    public String toString() {
        return "WhitelistLoadInformationRequestPacket{" +
                "channelName='" + channelName + '\'' +
                '}';
    }
}

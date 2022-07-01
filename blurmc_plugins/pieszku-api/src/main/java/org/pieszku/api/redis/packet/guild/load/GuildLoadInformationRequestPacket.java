package org.pieszku.api.redis.packet.guild.load;

import org.pieszku.api.redis.packet.Packet;

public class GuildLoadInformationRequestPacket extends Packet {

    private final String sectorName;

    public GuildLoadInformationRequestPacket(String sectorName){
        this.sectorName = sectorName;
    }

    public String getSectorName() {
        return sectorName;
    }

    @Override
    public String toString() {
        return "GuildLoadInformationRequestPacket{" +
                "sectorName='" + sectorName + '\'' +
                '}';
    }
}

package org.pieszku.api.redis.packet.guild.request;

import org.pieszku.api.redis.packet.Packet;

public class GuildInformationRequestPacket extends Packet {

    private final String guildName;
    private final String sectorSender;

    public GuildInformationRequestPacket(String guildName, String sectorSender){
        this.guildName = guildName;
        this.sectorSender = sectorSender;
    }

    public String getGuildName() {
        return guildName;
    }

    public String getSectorSender() {
        return sectorSender;
    }

    @Override
    public String toString() {
        return "GuildInformationRequestPacket{" +
                "guildName='" + guildName + '\'' +
                ", sectorSender='" + sectorSender + '\'' +
                '}';
    }
}

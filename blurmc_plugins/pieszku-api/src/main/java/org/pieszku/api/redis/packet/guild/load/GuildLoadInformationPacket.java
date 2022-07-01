package org.pieszku.api.redis.packet.guild.load;

import org.pieszku.api.objects.guild.impl.Guild;
import org.pieszku.api.redis.packet.Packet;

import java.util.List;

public class GuildLoadInformationPacket extends Packet {

    private final List<Guild> guildLoadList;

    public GuildLoadInformationPacket(List<Guild> guildLoadList){
        this.guildLoadList = guildLoadList;
    }

    public List<Guild> getGuildLoadList() {
        return guildLoadList;
    }

    @Override
    public String toString() {
        return "GuildLoadInformationPacket{" +
                "guildLoadList=" + guildLoadList +
                '}';
    }
}

package org.pieszku.api.redis.packet.guild.synchronize;

import org.pieszku.api.redis.packet.Packet;
import org.pieszku.api.redis.packet.type.UpdateType;

public class GuildInformationSynchronizePacket extends Packet {

    private final String guildName;
    private final String serializedGuild;
    private final UpdateType updateType;

    public GuildInformationSynchronizePacket(String guildName, String serializedGuild, UpdateType updateType){
        this.guildName = guildName;
        this.serializedGuild = serializedGuild;
        this.updateType = updateType;
    }

    public String getSerializedGuild() {
        return serializedGuild;
    }

    public UpdateType getUpdateType() {
        return updateType;
    }

    public String getGuildName() {
        return guildName;
    }


    @Override
    public String toString() {
        return "GuildInformationSynchronizePacket{" +
                "guildName='" + guildName + '\'' +
                ", serializedGuild='" + serializedGuild + '\'' +
                ", updateType=" + updateType +
                '}';
    }
}

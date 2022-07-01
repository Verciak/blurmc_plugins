package org.pieszku.api.redis.packet.whitelist.sync;

import org.pieszku.api.redis.packet.Packet;
import org.pieszku.api.redis.packet.type.UpdateType;

public class WhitelistSynchronizeInformationPacket extends Packet {

    private final String channelName;
    private final UpdateType updateType;
    private final String serializedWhitelist;

    public WhitelistSynchronizeInformationPacket(String channelName, UpdateType updateType, String serializedWhitelist){
        this.channelName = channelName;
        this.updateType = updateType;
        this.serializedWhitelist = serializedWhitelist;
    }

    public String getChannelName() {
        return channelName;
    }

    public UpdateType getUpdateType() {
        return updateType;
    }

    public String getSerializedWhitelist() {
        return serializedWhitelist;
    }
    @Override
    public String toString() {
        return "WhitelistSynchronizeInformationPacket{" +
                "channelName='" + channelName + '\'' +
                ", updateType=" + updateType +
                ", serializedWhitelist='" + serializedWhitelist + '\'' +
                '}';
    }
}

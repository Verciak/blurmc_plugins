package org.pieszku.api.redis.packet.mute.sync;

import org.pieszku.api.redis.packet.Packet;
import org.pieszku.api.redis.packet.type.UpdateType;

public class MuteInformationSynchronizePacket extends Packet {

    private final String nickName;
    private final UpdateType updateType;
    private final String serializedMute;

    public MuteInformationSynchronizePacket(String nickName, UpdateType updateType, String serializedMute){
        this.nickName = nickName;
        this.updateType = updateType;
        this.serializedMute = serializedMute;
    }

    public String getNickName() {
        return nickName;
    }

    public String getSerializedMute() {
        return serializedMute;
    }

    public UpdateType getUpdateType() {
        return updateType;
    }

    @Override
    public String toString() {
        return "MuteInformationSynchronizePacket{" +
                "nickName='" + nickName + '\'' +
                ", updateType=" + updateType +
                ", serializedMute='" + serializedMute + '\'' +
                '}';
    }
}

package org.pieszku.api.redis.packet.user.sync;

import org.pieszku.api.redis.packet.Packet;
import org.pieszku.api.redis.packet.type.UpdateType;

public class UserInformationSynchronizePacket extends Packet {

    private final String nickName;
    private final String serializedUser;
    private final UpdateType updateType;

    public UserInformationSynchronizePacket(String nickName, String serializedUser, UpdateType updateType){
        this.nickName = nickName;
        this.serializedUser = serializedUser;
        this.updateType = updateType;
    }


    public String getNickName() {
        return nickName;
    }

    public String getSerializedUser() {
        return serializedUser;
    }
    public UpdateType getUpdateType() {
        return updateType;
    }

    @Override
    public String toString() {
        return "UserInformationSynchronizePacket{" +
                "nickName='" + nickName + '\'' +
                ", serializedUser='" + serializedUser + '\'' +
                ", updateType=" + updateType +
                '}';
    }
}

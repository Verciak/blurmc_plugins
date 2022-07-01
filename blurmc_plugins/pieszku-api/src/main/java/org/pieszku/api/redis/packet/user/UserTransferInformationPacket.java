package org.pieszku.api.redis.packet.user;

import org.pieszku.api.redis.packet.Packet;

public class UserTransferInformationPacket extends Packet {

    private final String nickName;
    private final String serializedUser;

    public UserTransferInformationPacket(String nickName, String serializedUser){
        this.nickName = nickName;
        this.serializedUser = serializedUser;
    }

    public String getNickName() {
        return nickName;
    }

    public String getSerializedUser() {
        return serializedUser;
    }

    @Override
    public String toString() {
        return "UserTransferInformationPacket{" +
                "nickName='" + nickName + '\'' +
                ", serializedUser='" + serializedUser + '\'' +
                '}';
    }
}

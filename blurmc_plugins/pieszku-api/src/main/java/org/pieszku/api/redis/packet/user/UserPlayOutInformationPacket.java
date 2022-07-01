package org.pieszku.api.redis.packet.user;

import org.pieszku.api.redis.packet.Packet;

public class UserPlayOutInformationPacket extends Packet {

    private final String nickName;
    private final String userSerialized;

    public UserPlayOutInformationPacket(String nickName, String userSerialized){
        this.nickName = nickName;
        this.userSerialized = userSerialized;
    }

    public String getUserSerialized() {
        return userSerialized;
    }

    public String getNickName() {
        return nickName;
    }

    @Override
    public String toString() {
        return "UserPlayOutInformationPacket{" +
                "nickName='" + nickName + '\'' +
                ", userSerialized='" + userSerialized + '\'' +
                '}';
    }
}

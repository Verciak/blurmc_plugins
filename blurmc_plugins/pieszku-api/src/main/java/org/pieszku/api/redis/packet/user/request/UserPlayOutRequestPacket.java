package org.pieszku.api.redis.packet.user.request;

import org.pieszku.api.redis.packet.Packet;

public class UserPlayOutRequestPacket extends Packet {

    private final String nickName;
    private final String sectorSender;

    public UserPlayOutRequestPacket(String nickName, String sectorSender){
        this.nickName = nickName;
        this.sectorSender = sectorSender;
    }

    public String getSectorSender() {
        return sectorSender;
    }

    public String getNickName() {
        return nickName;
    }

    @Override
    public String toString() {
        return "UserPlayOutRequestPacket{" +
                "nickName='" + nickName + '\'' +
                ", sectorSender='" + sectorSender + '\'' +
                '}';
    }
}

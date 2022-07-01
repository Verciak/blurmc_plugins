package org.pieszku.api.redis.packet.ban;

import org.pieszku.api.redis.packet.Packet;

public class BanInformationPacket extends Packet {

    private final String nickName;
    private final String banGsonSerializer;

    public BanInformationPacket(String nickName, String banGsonSerializer){
        this.nickName = nickName;
        this.banGsonSerializer = banGsonSerializer;
    }

    public String getNickName() {
        return nickName;
    }

    public String getBanGsonSerializer() {
        return banGsonSerializer;
    }

    @Override
    public String toString() {
        return "BanInformationPacket{" +
                "nickName='" + nickName + '\'' +
                ", banGsonSerializer=" + banGsonSerializer +
                '}';
    }
}

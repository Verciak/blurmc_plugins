package org.pieszku.api.redis.packet.client;

import org.pieszku.api.redis.packet.Packet;

public class KickPlayerPacket extends Packet {

    private final String nickName;
    private final String reason;

    public KickPlayerPacket(String nickName, String reason){
        this.nickName = nickName;
        this.reason = reason;
    }

    public String getNickName() {
        return nickName;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "KickPlayerPacket{" +
                "nickName='" + nickName + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}

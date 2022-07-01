package org.pieszku.api.redis.packet.ban.request;

import org.pieszku.api.redis.packet.Packet;

public class BanInformationRequestPacket extends Packet {

    private final String sectorSender;
    private final String nickName;

    public BanInformationRequestPacket(String sectorSender, String nickName){
        this.sectorSender = sectorSender;
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }

    public String getSectorSender() {
        return sectorSender;
    }

    @Override
    public String toString() {
        return "BanInformationRequestPacket{" +
                "sectorSender='" + sectorSender + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}

package org.pieszku.api.redis.packet.bazaar.request;

import org.pieszku.api.redis.packet.Packet;

public class BazaarInformationRequestPacket extends Packet {


    private final int id;
    private final String nickName;
    private final String sectorSender;


    public BazaarInformationRequestPacket(int id, String nickName, String sectorSender) {
        this.id = id;
        this.nickName = nickName;
        this.sectorSender = sectorSender;
    }

    public int getId() {
        return id;
    }

    public String getNickName() {
        return nickName;
    }

    public String getSectorSender() {
        return sectorSender;
    }

    @Override
    public String toString() {
        return "BazaarInformationRequestPacket{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", sectorSender='" + sectorSender + '\'' +
                '}';
    }
}

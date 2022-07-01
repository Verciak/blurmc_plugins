package org.pieszku.api.redis.packet.ban.load;

import org.pieszku.api.redis.packet.Packet;

public class BanInformationLoadRequestPacket extends Packet {

    private String sectorSender;

    public BanInformationLoadRequestPacket(String sectorSender){
        this.sectorSender = sectorSender;
    }

    public String getSectorSender() {
        return sectorSender;
    }

    @Override
    public String toString() {
        return "BanInformationLoadRequestPacket{" +
                "sectorSender='" + sectorSender + '\'' +
                '}';
    }
}

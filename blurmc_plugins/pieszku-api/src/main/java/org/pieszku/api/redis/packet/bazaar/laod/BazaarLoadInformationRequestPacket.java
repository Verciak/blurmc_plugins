package org.pieszku.api.redis.packet.bazaar.laod;

import org.pieszku.api.redis.packet.Packet;

public class BazaarLoadInformationRequestPacket extends Packet {

    private final String sectorSender;


    public BazaarLoadInformationRequestPacket(String sectorSender){
        this.sectorSender = sectorSender;
    }

    public String getSectorSender() {
        return sectorSender;
    }

    @Override
    public String toString() {
        return "BazaarLoadInformationRequestPacket{" +
                "sectorSender='" + sectorSender + '\'' +
                '}';
    }
}

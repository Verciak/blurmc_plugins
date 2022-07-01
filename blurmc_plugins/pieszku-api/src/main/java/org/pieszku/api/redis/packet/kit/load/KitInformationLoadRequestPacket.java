package org.pieszku.api.redis.packet.kit.load;

import org.pieszku.api.redis.packet.Packet;

public class KitInformationLoadRequestPacket extends Packet {

    private final String sectorSender;

    public KitInformationLoadRequestPacket(String sectorSender){
        this.sectorSender = sectorSender;
    }

    public String getSectorSender() {
        return sectorSender;
    }

    @Override
    public String toString() {
        return "KitInformationLoadRequestPacket{" +
                "sectorSender='" + sectorSender + '\'' +
                '}';
    }
}

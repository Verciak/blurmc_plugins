package org.pieszku.api.redis.packet.sector.request;

import org.pieszku.api.redis.packet.Packet;

public class SectorInformationRequestPacket extends Packet {

    private final String sectorSenderName;
    private final String sectorRequestName;

    public SectorInformationRequestPacket(String sectorSenderName, String sectorRequestName) {
        this.sectorSenderName = sectorSenderName;
        this.sectorRequestName = sectorRequestName;
    }

    public String getSectorRequestName() {
        return sectorRequestName;
    }

    public String getSectorSenderName() {
        return sectorSenderName;
    }

    @Override
    public String toString() {
        return "SectorInformationRequestPacket{" +
                "sectorSenderName='" + sectorSenderName + '\'' +
                ", sectorRequestName='" + sectorRequestName + '\'' +
                '}';
    }
}

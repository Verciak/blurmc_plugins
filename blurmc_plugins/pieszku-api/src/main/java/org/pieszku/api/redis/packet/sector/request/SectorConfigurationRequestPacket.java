package org.pieszku.api.redis.packet.sector.request;

import org.pieszku.api.redis.packet.Packet;

public class SectorConfigurationRequestPacket extends Packet {

    private final String senderSectorName;
    public SectorConfigurationRequestPacket(String senderSectorName){
        this.senderSectorName = senderSectorName;
    }


    public String getSenderSectorName() {
        return senderSectorName;
    }


    @Override
    public String toString() {
        return "SectorConfigurationRequestPacket{" +
                "senderSectorName='" + senderSectorName + '\'' +
                '}';
    }
}
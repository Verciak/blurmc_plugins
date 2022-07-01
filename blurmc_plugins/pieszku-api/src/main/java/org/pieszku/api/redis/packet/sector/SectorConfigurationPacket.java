package org.pieszku.api.redis.packet.sector;

import org.pieszku.api.redis.packet.Packet;
import org.pieszku.api.sector.json.SectorJson;

public class SectorConfigurationPacket extends Packet {

    private final String senderSectorName;
    private final SectorJson sectorJson;

    public SectorConfigurationPacket(String senderSectorName, SectorJson sectorJson){
        this.senderSectorName = senderSectorName;
        this.sectorJson = sectorJson;
    }

    public SectorJson getSectorJson() {
        return sectorJson;
    }

    public String getSenderSectorName() {
        return senderSectorName;
    }

    @Override
    public String toString() {
        return "SectorConfigurationPacket{" +
                "senderSectorName='" + senderSectorName + '\'' +
                ", sectorJson=" + sectorJson +
                '}';
    }
}

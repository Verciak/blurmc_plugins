package org.pieszku.api.redis.packet.client.load;

import org.pieszku.api.redis.packet.Packet;

public class ConfigurationLoadClientInformationRequestPacket extends Packet {

    private final String sectorSender;

    public ConfigurationLoadClientInformationRequestPacket(String sectorSender){
        this.sectorSender = sectorSender;
    }

    public String getSectorSender() {
        return sectorSender;
    }

    @Override
    public String toString() {
        return "ConfigurationLoadClientInformationRequestPacket{" +
                "sectorSender='" + sectorSender + '\'' +
                '}';
    }
}

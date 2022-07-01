package org.pieszku.api.redis.packet.mute.load;

import org.pieszku.api.redis.packet.Packet;

public class MuteLoadInformationRequestPacket extends Packet {

    private final String sectorSender;

    public MuteLoadInformationRequestPacket(String sectorSender){
        this.sectorSender = sectorSender;
    }

    public String getSectorSender() {
        return sectorSender;
    }

    @Override
    public String toString() {
        return "MuteLoadInformationRequestPacket{" +
                "sectorSender='" + sectorSender + '\'' +
                '}';
    }
}

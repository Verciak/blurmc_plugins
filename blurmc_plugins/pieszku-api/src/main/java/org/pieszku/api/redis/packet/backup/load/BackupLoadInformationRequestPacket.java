package org.pieszku.api.redis.packet.backup.load;

import org.pieszku.api.redis.packet.Packet;

public class BackupLoadInformationRequestPacket extends Packet {

    private final String sectorSender;


    public BackupLoadInformationRequestPacket(String sectorSender){
        this.sectorSender = sectorSender;
    }

    public String getSectorSender() {
        return sectorSender;
    }

    @Override
    public String toString() {
        return "BackupLoadInformationRequestPacket{" +
                "sectorSender='" + sectorSender + '\'' +
                '}';
    }
}

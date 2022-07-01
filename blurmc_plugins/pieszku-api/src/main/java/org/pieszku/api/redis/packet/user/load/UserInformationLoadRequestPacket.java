package org.pieszku.api.redis.packet.user.load;

import org.pieszku.api.redis.packet.Packet;

public class UserInformationLoadRequestPacket extends Packet {

    private final String sectorName;

    public UserInformationLoadRequestPacket(String sectorName){
        this.sectorName = sectorName;
    }

    public String getSectorName() {
        return sectorName;
    }

    @Override
    public String toString() {
        return "UserInformationLoadRequestPacket{" +
                "sectorName='" + sectorName + '\'' +
                '}';
    }
}

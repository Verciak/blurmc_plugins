package org.pieszku.api.redis.packet.user.request;

import org.pieszku.api.redis.packet.Packet;

public class UserTransferInformationRequestPacket extends Packet {

    private final String sectorName;
    private final String nickName;

    public UserTransferInformationRequestPacket(String sectorName, String nickName){
        this.sectorName = sectorName;
        this.nickName = nickName;
    }
    public String getNickName() {
        return nickName;
    }
    public String getSectorName() {
        return sectorName;
    }

    @Override
    public String toString() {
        return "UserTransferInformationRequestPacket{" +
                "nickName='" + nickName + '\'' +
                '}';
    }
}

package org.pieszku.api.redis.packet.bazaar;

import org.pieszku.api.redis.packet.Packet;
import org.pieszku.api.redis.packet.bazaar.type.BazaarType;

public class BazaarInformationPacket extends Packet {


    private final int id;
    private final String nickName;
    private final BazaarType bazaarType;

    public BazaarInformationPacket(int id, String nickName, BazaarType bazaarType){
        this.id = id;
        this.nickName = nickName;
        this.bazaarType = bazaarType;
    }

    public BazaarType getBazaarType() {
        return bazaarType;
    }

    public String getNickName() {
        return nickName;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "BazaarInformationPacket{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", bazaarType=" + bazaarType +
                '}';
    }
}

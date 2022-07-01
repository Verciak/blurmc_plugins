package org.pieszku.api.redis.packet.ban.load;

import org.pieszku.api.objects.ban.Ban;
import org.pieszku.api.redis.packet.Packet;

import java.util.List;

public class BanInformationLoadPacket extends Packet {


    private final List<Ban> banList;

    public BanInformationLoadPacket(List<Ban> banList){
        this.banList = banList;
    }

    public List<Ban> getBanList() {
        return banList;
    }

    @Override
    public String toString() {
        return "BanInformationLoadPacket{" +
                "banList=" + banList +
                '}';
    }
}

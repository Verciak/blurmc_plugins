package org.pieszku.api.redis.packet.mute.load;

import org.pieszku.api.objects.mute.Mute;
import org.pieszku.api.redis.packet.Packet;

import java.util.List;

public class MuteLoadInformationPacket extends Packet {

    private final List<Mute> muteList;

    public MuteLoadInformationPacket(List<Mute> muteList){
        this.muteList = muteList;
    }

    public List<Mute> getMuteList() {
        return muteList;
    }

    @Override
    public String toString() {
        return "MuteLoadInformationPacket{" +
                "muteList=" + muteList +
                '}';
    }
}

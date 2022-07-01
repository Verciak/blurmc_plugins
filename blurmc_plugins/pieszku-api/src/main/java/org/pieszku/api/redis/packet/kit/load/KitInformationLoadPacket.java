package org.pieszku.api.redis.packet.kit.load;

import org.pieszku.api.objects.kit.Kit;
import org.pieszku.api.redis.packet.Packet;

import java.util.Set;

public class KitInformationLoadPacket extends Packet {


    private final Set<Kit> kits;

    public KitInformationLoadPacket(Set<Kit> kits){
        this.kits = kits;
    }

    public Set<Kit> getKits() {
        return kits;
    }

    @Override
    public String toString() {
        return "KitInformationLoadPacket{" +
                "kits=" + kits +
                '}';
    }
}

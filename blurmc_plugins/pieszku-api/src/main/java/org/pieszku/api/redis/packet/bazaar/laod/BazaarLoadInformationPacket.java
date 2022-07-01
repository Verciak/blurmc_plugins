package org.pieszku.api.redis.packet.bazaar.laod;

import org.pieszku.api.objects.bazaar.Bazaar;
import org.pieszku.api.redis.packet.Packet;

import java.util.Set;

public class BazaarLoadInformationPacket extends Packet {

    private final Set<Bazaar> bazaars;

    public BazaarLoadInformationPacket(Set<Bazaar> bazaars){
        this.bazaars = bazaars;
    }

    public Set<Bazaar> getBazaars() {
        return bazaars;
    }

    @Override
    public String toString() {
        return "BazaarLoadInformationPacket{" +
                "bazaars=" + bazaars +
                '}';
    }
}

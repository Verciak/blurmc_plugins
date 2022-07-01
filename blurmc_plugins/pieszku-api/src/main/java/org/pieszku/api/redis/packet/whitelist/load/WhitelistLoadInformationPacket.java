package org.pieszku.api.redis.packet.whitelist.load;

import org.pieszku.api.proxy.global.WhitelistServer;
import org.pieszku.api.redis.packet.Packet;

import java.util.LinkedList;

public class WhitelistLoadInformationPacket extends Packet {

    private final LinkedList<WhitelistServer> whitelistServerList;

    public WhitelistLoadInformationPacket(LinkedList<WhitelistServer> whitelistServerList){
        this.whitelistServerList = whitelistServerList;
    }

    public LinkedList<WhitelistServer> getWhitelistServerList() {
        return whitelistServerList;
    }

    @Override
    public String toString() {
        return "WhitelistLoadInformationPacket{" +
                "whitelistServerList=" + whitelistServerList +
                '}';
    }
}

package org.pieszku.api.redis.packet.proxy.request;

import org.pieszku.api.redis.packet.Packet;

public class ProxyConfigurationInformationRequestPacket extends Packet {

    private final String proxySender;

    public ProxyConfigurationInformationRequestPacket(String proxySender){
        this.proxySender = proxySender;
    }

    public String getProxySender() {
        return proxySender;
    }

    @Override
    public String toString() {
        return "ProxyConfigurationInformationRequestPacket{" +
                "proxySender='" + proxySender + '\'' +
                '}';
    }
}

package org.pieszku.api.redis.packet.proxy;

import org.pieszku.api.proxy.json.ProxyJson;
import org.pieszku.api.redis.packet.Packet;

public class ProxyConfigurationInformationPacket extends Packet {

    private final ProxyJson proxyJson;

    public ProxyConfigurationInformationPacket(ProxyJson proxyJson){
        this.proxyJson = proxyJson;
    }

    public ProxyJson getProxyJson() {
        return proxyJson;
    }

    @Override
    public String toString() {
        return "ProxyConfigurationInformationPacket{" +
                "proxyJson=" + proxyJson +
                '}';
    }
}

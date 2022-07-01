package org.pieszku.server.handler.proxy.request;

import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.proxy.ProxyConfigurationInformationPacket;
import org.pieszku.api.redis.packet.proxy.request.ProxyConfigurationInformationRequestPacket;
import org.pieszku.server.ServerMain;

public class ProxyConfigurationInformationRequestHandler extends RedisListener<ProxyConfigurationInformationRequestPacket> {


    public ProxyConfigurationInformationRequestHandler() {
        super("MASTER", ProxyConfigurationInformationRequestPacket.class);
    }

    @Override
    public void onDecode(ProxyConfigurationInformationRequestPacket packet) {
        new ProxyConfigurationInformationPacket(ServerMain.getInstance().getProxyJson()).sendToChannel(packet.getProxySender());
    }
}

package org.pieszku.server.handler.proxy.sync;

import org.pieszku.api.proxy.ProxyService;
import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.proxy.sync.ProxySynchronizeInformationPacket;
import org.pieszku.server.ServerMain;

public class ProxySynchronizeInformationHandler extends RedisListener<ProxySynchronizeInformationPacket> {

    private final ProxyService proxyService = ServerMain.getInstance().getProxyService();

    public ProxySynchronizeInformationHandler() {
        super("MASTER", ProxySynchronizeInformationPacket.class);
    }

    @Override
    public void onDecode(ProxySynchronizeInformationPacket packet) {
        this.proxyService.findProxyByName(packet.getProxyName()).ifPresent(proxy -> {
            proxy.setConnections(packet.getConnections());
            proxy.setLatestInformation(packet.getLatestInformation());
            proxy.setPlayers(packet.getPlayers());
        });
    }
}

package pl.pieszku.proxy.redis;

import org.pieszku.api.proxy.ProxyService;
import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.proxy.sync.ProxySynchronizeInformationPacket;
import pl.pieszku.proxy.ProxyMain;

public class ProxySynchronizeInformationHandler extends RedisListener<ProxySynchronizeInformationPacket> {


    private final ProxyService proxyService = ProxyMain.getInstance().getProxyService();

    public ProxySynchronizeInformationHandler() {
        super(ProxyMain.getInstance().getProxyName(), ProxySynchronizeInformationPacket.class);
    }

    @Override
    public void onDecode(ProxySynchronizeInformationPacket packet) {
        this.proxyService.findProxyByName(packet.getProxyName()).ifPresent(proxy -> {
            proxy.setConnections(packet.getConnections());
            proxy.setPlayers(packet.getPlayers());
            proxy.setLatestInformation(packet.getLatestInformation());
        });
    }
}

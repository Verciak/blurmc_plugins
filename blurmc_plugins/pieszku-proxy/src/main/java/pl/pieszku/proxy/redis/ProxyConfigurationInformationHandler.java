package pl.pieszku.proxy.redis;

import org.pieszku.api.proxy.Proxy;
import org.pieszku.api.proxy.ProxyService;
import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.proxy.ProxyConfigurationInformationPacket;
import pl.pieszku.proxy.ProxyMain;

public class ProxyConfigurationInformationHandler extends RedisListener<ProxyConfigurationInformationPacket> {


    private final ProxyService proxyService = ProxyMain.getInstance().getProxyService();

    public ProxyConfigurationInformationHandler() {
        super(ProxyMain.getInstance().getProxyName(), ProxyConfigurationInformationPacket.class);
    }

    @Override
    public void onDecode(ProxyConfigurationInformationPacket packet) {
        for (Proxy proxy : packet.getProxyJson().getProxies()) {
            this.proxyService.getProxyList().add(proxy);
        }
        System.out.println("[MASTER-SERVER] Sent " + proxyService.getProxyList().size() + " proxies.");
    }
}

package org.pieszku.server.runnable;

import org.pieszku.api.proxy.Proxy;
import org.pieszku.api.proxy.ProxyService;
import org.pieszku.api.redis.packet.proxy.sync.ProxySynchronizeInformationPacket;
import org.pieszku.server.ServerMain;

import java.util.HashSet;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ProxyInformationRunnable implements Runnable {


    private final ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
    private final ProxyService proxyService = ServerMain.getInstance().getProxyService();

    public void start(){
        this.scheduledExecutorService.scheduleAtFixedRate(this, 1, 1, TimeUnit.MILLISECONDS);
    }

    @Override
    public void run() {
        for (Proxy proxy : this.proxyService.getProxyList()) {
            if(proxy.getLatestInformation() <= System.currentTimeMillis()){
                proxy.setPlayers(new HashSet<>());
                proxy.setLatestInformation(0);
                proxy.setConnections(0);
            }
            new ProxySynchronizeInformationPacket(proxy.getName(), proxy.getPlayers(), proxy.getConnections(), proxy.getLatestInformation())
                    .sendToAllProxies(this.proxyService);
        }
    }
}

package pl.pieszku.proxy.runnable;

import org.pieszku.api.redis.packet.proxy.sync.ProxySynchronizeInformationPacket;
import org.pieszku.api.type.TimeType;
import pl.pieszku.proxy.ProxyMain;

import java.util.concurrent.TimeUnit;

public class ProxyInformationRunnable implements Runnable {


    @Override
    public void run() {
        ProxyMain.getInstance().currentProxy().ifPresent(proxy -> {
            new ProxySynchronizeInformationPacket(proxy.getName(), proxy.getPlayers(), proxy.getConnections(), System.currentTimeMillis() + TimeType.SECOND.getTime(3))
                    .sendToChannel("MASTER");
        });
    }

    public void start() {
        ProxyMain.getInstance().getProxy().getScheduler().schedule(ProxyMain.getInstance(), this, 1, 1, TimeUnit.MILLISECONDS);
    }
}

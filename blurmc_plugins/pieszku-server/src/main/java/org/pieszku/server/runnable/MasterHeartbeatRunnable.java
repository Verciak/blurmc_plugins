package org.pieszku.server.runnable;

import org.pieszku.api.redis.packet.master.MasterHeartbeatPacket;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MasterHeartbeatRunnable implements Runnable{

    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    public void start() {
        this.scheduledExecutorService.scheduleAtFixedRate(this, 1,1, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        new MasterHeartbeatPacket().sendToChannel("CLIENT");
    }
}

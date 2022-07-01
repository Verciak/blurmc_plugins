package pl.pieszku.proxy.redis.master;

import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.master.MasterHeartbeatPacket;
import pl.pieszku.proxy.ProxyMain;
import pl.pieszku.proxy.service.MasterConnectionHeartbeatService;

public class MasterHeartbeatHandler extends RedisListener<MasterHeartbeatPacket> {

    private final MasterConnectionHeartbeatService masterConnectionHeartbeatService = ProxyMain.getInstance().getMasterConnectionHeartbeatService();

    public MasterHeartbeatHandler() {
        super("CLIENT", MasterHeartbeatPacket.class);
    }

    @Override
    public void onDecode(MasterHeartbeatPacket packet) {
        masterConnectionHeartbeatService.updateLastHeartbeat();
    }
}
package pl.pieszku.sectors.redis.master;

import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.master.MasterHeartbeatPacket;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.service.MasterConnectionHeartbeatService;

public class MasterHeartbeatHandler extends RedisListener<MasterHeartbeatPacket> {

    private final MasterConnectionHeartbeatService masterConnectionHeartbeatService = BukkitMain.getInstance().getMasterConnectionHeartbeatService();

    public MasterHeartbeatHandler() {
        super("CLIENT", MasterHeartbeatPacket.class);
    }

    @Override
    public void onDecode(MasterHeartbeatPacket packet) {
        masterConnectionHeartbeatService.updateLastHeartbeat();
    }
}
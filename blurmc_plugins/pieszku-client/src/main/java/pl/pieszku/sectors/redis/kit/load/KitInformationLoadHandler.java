package pl.pieszku.sectors.redis.kit.load;

import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.kit.load.KitInformationLoadPacket;
import org.pieszku.api.service.KitService;
import pl.pieszku.sectors.BukkitMain;

public class KitInformationLoadHandler extends RedisListener<KitInformationLoadPacket> {

    private final KitService kitService = BukkitMain.getInstance().getKitService();

    public KitInformationLoadHandler() {
        super(BukkitMain.getInstance().getSectorName(), KitInformationLoadPacket.class);
    }

    @Override
    public void onDecode(KitInformationLoadPacket packet) {
        this.kitService.setKits(packet.getKits());
        System.out.println("[MASTER-SERVER] Sent " + packet.getKits().size() + " kit.");
    }
}

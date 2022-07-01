package pl.pieszku.sectors.redis.ban.load;

import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.ban.load.BanInformationLoadPacket;
import org.pieszku.api.service.BanService;
import pl.pieszku.sectors.BukkitMain;

public class BanInformationLoadHandler extends RedisListener<BanInformationLoadPacket> {


    private final BanService banService = BukkitMain.getInstance().getBanService();

    public BanInformationLoadHandler() {
        super(BukkitMain.getInstance().getSectorName(), BanInformationLoadPacket.class);
    }

    @Override
    public void onDecode(BanInformationLoadPacket packet) {
        this.banService.setBanList(packet.getBanList());
        System.out.println("[MASTER-SERVER] Sent " + this.banService.getBanList().size() + " ban.");
    }
}

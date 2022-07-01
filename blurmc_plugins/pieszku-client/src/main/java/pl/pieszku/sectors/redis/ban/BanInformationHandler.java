package pl.pieszku.sectors.redis.ban;

import com.google.gson.Gson;
import org.pieszku.api.objects.ban.Ban;
import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.ban.BanInformationPacket;
import org.pieszku.api.service.BanService;
import pl.pieszku.sectors.BukkitMain;

public class BanInformationHandler extends RedisListener<BanInformationPacket> {

    private final BanService banService = BukkitMain.getInstance().getBanService();

    public BanInformationHandler() {
        super(BukkitMain.getInstance().getSectorName(), BanInformationPacket.class);
    }

    @Override
    public void onDecode(BanInformationPacket packet) {
        Ban ban = this.banService.findBanByNickName(packet.getNickName());
        if(ban== null)return;
        this.banService.getBanList().remove(ban);
        this.banService.getBanList().add(new Gson().fromJson(packet.getBanGsonSerializer(), Ban.class));
    }
}

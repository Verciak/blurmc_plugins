package pl.pieszku.sectors.redis.ban.sync;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.pieszku.api.objects.ban.Ban;
import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.ban.sync.BanSynchronizeInformationPacket;
import org.pieszku.api.service.BanService;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.redis.ban.runnable.BanSynchronizeInformationRunnable;

public class BanSynchronizeInformationHandler extends RedisListener<BanSynchronizeInformationPacket> {

    private final BanService banService = BukkitMain.getInstance().getBanService();

    public BanSynchronizeInformationHandler() {
        super(BukkitMain.getInstance().getSectorName(), BanSynchronizeInformationPacket.class);
    }

    @Override
    public void onDecode(BanSynchronizeInformationPacket packet) {
        switch (packet.getUpdateType()) {
            case REMOVE: {
                Ban ban = this.banService.findBanByNickName(packet.getNickName());
                if(ban == null)return;
                this.banService.getBanList().remove(ban);
                break;
            }
            case CREATE: {
                Ban ban = new Gson().fromJson(packet.getBanGsonSerializer(), Ban.class);
                this.banService.getBanList().add(ban);
                Player player = Bukkit.getPlayerExact(packet.getNickName());
                if(player == null)return;
                new BanSynchronizeInformationRunnable(player, ban).start();
                break;
            }
        }
    }
}

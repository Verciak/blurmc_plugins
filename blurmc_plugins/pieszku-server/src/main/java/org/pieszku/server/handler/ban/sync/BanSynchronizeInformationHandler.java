package org.pieszku.server.handler.ban.sync;

import com.google.gson.Gson;
import org.pieszku.api.API;
import org.pieszku.api.objects.ban.Ban;
import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.ban.sync.BanSynchronizeInformationPacket;
import org.pieszku.api.redis.packet.type.UpdateType;
import org.pieszku.api.service.BanService;
import org.pieszku.server.ServerMain;

public class BanSynchronizeInformationHandler extends RedisListener<BanSynchronizeInformationPacket> {

    private final BanService banService = ServerMain.getInstance().getBanService();

    public BanSynchronizeInformationHandler() {
        super("MASTER", BanSynchronizeInformationPacket.class);
    }

    @Override
    public void onDecode(BanSynchronizeInformationPacket packet) {
        switch (packet.getUpdateType()) {
            case REMOVE: {
                Ban ban = this.banService.findBanByNickName(packet.getNickName());
                if(ban == null)return;
                this.banService.getBanList().remove(ban);
                API.getInstance().getBanRepository().update(ban, ban.getNickName(), UpdateType.REMOVE);
                break;
            }
            case CREATE: {
                Ban ban = new Gson().fromJson(packet.getBanGsonSerializer(), Ban.class);
                this.banService.getBanList().add(ban);
                API.getInstance().getBanRepository().update(ban, ban.getNickName(), UpdateType.CREATE);
                break;
            }
        }
    }
}

package org.pieszku.server.handler.ban.request;

import com.google.gson.Gson;
import org.pieszku.api.objects.ban.Ban;
import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.ban.BanInformationPacket;
import org.pieszku.api.redis.packet.ban.request.BanInformationRequestPacket;
import org.pieszku.api.service.BanService;
import org.pieszku.server.ServerMain;

public class BanInformationRequestHandler extends RedisListener<BanInformationRequestPacket> {


    private final BanService banService = ServerMain.getInstance().getBanService();

    public BanInformationRequestHandler() {
        super("MASTER", BanInformationRequestPacket.class);
    }

    @Override
    public void onDecode(BanInformationRequestPacket packet) {
        Ban ban = this.banService.findBanByNickName(packet.getNickName());
        if(ban == null)return;
        new BanInformationPacket(ban.getNickName(), new Gson().toJson(ban)).sendToSector(packet.getSectorSender());
    }
}

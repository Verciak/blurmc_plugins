package org.pieszku.server.handler.ban.load;

import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.ban.load.BanInformationLoadPacket;
import org.pieszku.api.redis.packet.ban.load.BanInformationLoadRequestPacket;
import org.pieszku.api.service.BanService;
import org.pieszku.server.ServerMain;

public class BanInformationLoadRequestHandler extends RedisListener<BanInformationLoadRequestPacket> {


    private final BanService banService = ServerMain.getInstance().getBanService();

    public BanInformationLoadRequestHandler() {
        super("MASTER", BanInformationLoadRequestPacket.class);
    }

    @Override
    public void onDecode(BanInformationLoadRequestPacket packet) {
        new BanInformationLoadPacket(this.banService.getBanList()).sendToChannel(packet.getSectorSender());
    }
}

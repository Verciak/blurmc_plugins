package org.pieszku.server.handler.kit.load;

import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.kit.load.KitInformationLoadPacket;
import org.pieszku.api.redis.packet.kit.load.KitInformationLoadRequestPacket;
import org.pieszku.api.service.KitService;
import org.pieszku.server.ServerMain;

public class KitInformationLoadRequestHandler extends RedisListener<KitInformationLoadRequestPacket> {

    private final KitService kitService = ServerMain.getInstance().getKitService();

    public KitInformationLoadRequestHandler() {
        super("MASTER", KitInformationLoadRequestPacket.class);
    }

    @Override
    public void onDecode(KitInformationLoadRequestPacket packet) {
        new KitInformationLoadPacket(this.kitService.getKits()).sendToSector(packet.getSectorSender());
    }
}

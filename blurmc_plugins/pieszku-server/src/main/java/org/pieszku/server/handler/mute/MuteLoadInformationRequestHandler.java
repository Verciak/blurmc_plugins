package org.pieszku.server.handler.mute;

import org.pieszku.api.API;
import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.mute.load.MuteLoadInformationPacket;
import org.pieszku.api.redis.packet.mute.load.MuteLoadInformationRequestPacket;
import org.pieszku.api.service.MuteService;

public class MuteLoadInformationRequestHandler extends RedisListener<MuteLoadInformationRequestPacket> {


    private final MuteService muteService = API.getInstance().getMuteService();

    public MuteLoadInformationRequestHandler() {
        super("MASTER", MuteLoadInformationRequestPacket.class);
    }

    @Override
    public void onDecode(MuteLoadInformationRequestPacket packet) {
        new MuteLoadInformationPacket(this.muteService.getMuteList()).sendToChannel(packet.getSectorSender());
    }
}

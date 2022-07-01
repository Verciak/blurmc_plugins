package org.pieszku.server.handler.whitelist;

import org.pieszku.api.API;
import org.pieszku.api.proxy.global.WhitelistServerService;
import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.whitelist.load.WhitelistLoadInformationPacket;
import org.pieszku.api.redis.packet.whitelist.request.WhitelistLoadInformationRequestPacket;

public class WhitelistLoadRequestInformationHandler extends RedisListener<WhitelistLoadInformationRequestPacket> {


    private final WhitelistServerService whitelistServerService = API.getInstance().getWhitelistServerService();

    public WhitelistLoadRequestInformationHandler() {
        super("MASTER", WhitelistLoadInformationRequestPacket.class);
    }

    @Override
    public void onDecode(WhitelistLoadInformationRequestPacket packet) {
        new WhitelistLoadInformationPacket(this.whitelistServerService.getWhitelistServersList()).sendToChannel(packet.getChannelName());
    }
}

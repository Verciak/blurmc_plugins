package org.pieszku.server.handler.guild.load;

import org.pieszku.api.service.GuildService;
import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.guild.load.GuildLoadInformationPacket;
import org.pieszku.api.redis.packet.guild.load.GuildLoadInformationRequestPacket;
import org.pieszku.server.ServerMain;

public class GuildLoadInformationRequestHandler extends RedisListener<GuildLoadInformationRequestPacket> {

    private final GuildService guildService = ServerMain.getInstance().getGuildService();

    public GuildLoadInformationRequestHandler() {
        super("MASTER", GuildLoadInformationRequestPacket.class);
    }

    @Override
    public void onDecode(GuildLoadInformationRequestPacket packet) {
        new GuildLoadInformationPacket(this.guildService.getGuildList()).sendToChannel(packet.getSectorName());
    }
}

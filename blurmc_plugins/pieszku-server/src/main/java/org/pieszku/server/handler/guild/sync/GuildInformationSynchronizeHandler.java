package org.pieszku.server.handler.guild.sync;

import com.google.gson.Gson;
import org.pieszku.api.API;
import org.pieszku.api.objects.guild.impl.Guild;
import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.guild.synchronize.GuildInformationSynchronizePacket;
import org.pieszku.api.redis.packet.type.UpdateType;
import org.pieszku.api.sector.SectorService;
import org.pieszku.api.service.GuildService;
import org.pieszku.server.ServerMain;

public class GuildInformationSynchronizeHandler extends RedisListener<GuildInformationSynchronizePacket> {

    private final GuildService guildService = ServerMain.getInstance().getGuildService();
    private final SectorService sectorService = ServerMain.getInstance().getSectorService();

    public GuildInformationSynchronizeHandler() {
        super("MASTER", GuildInformationSynchronizePacket.class);
    }

    @Override
    public void onDecode(GuildInformationSynchronizePacket packet) {
        this.guildService.findGuildByName(packet.getGuildName()).ifPresent(guild -> {
            Guild guildSerialized = new Gson().fromJson(packet.getSerializedGuild(), Guild.class);
            switch (packet.getUpdateType()) {
                case UPDATE: {
                    this.guildService.getGuildList().remove(guild);
                    this.guildService.getGuildList().add(guildSerialized);
                    this.synchronize(guildSerialized, UpdateType.UPDATE);
                    break;
                }
                case REMOVE: {
                    this.guildService.getGuildList().remove(guild);
                    this.synchronize(guild, UpdateType.REMOVE);
                    break;
                }
            }
        });
        if (packet.getUpdateType().equals(UpdateType.CREATE)) {
            Guild guildSerialized = new Gson().fromJson(packet.getSerializedGuild(), Guild.class);
            this.guildService.getGuildList().add(guildSerialized);
            this.synchronize(guildSerialized, UpdateType.CREATE);
        }
    }
    public void synchronize(Guild guild, UpdateType updateType){
        new GuildInformationSynchronizePacket(guild.getName(), new Gson().toJson(guild), updateType)
                .sendToAllSectors(this.sectorService);
        API.getInstance().getGuildRepository().update(guild, guild.getName(), updateType);
    }
}

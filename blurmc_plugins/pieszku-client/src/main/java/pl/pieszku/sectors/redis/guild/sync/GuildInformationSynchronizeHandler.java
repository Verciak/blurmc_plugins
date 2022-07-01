package pl.pieszku.sectors.redis.guild.sync;

import com.google.gson.Gson;
import org.pieszku.api.objects.guild.impl.Guild;
import org.pieszku.api.service.GuildService;
import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.guild.synchronize.GuildInformationSynchronizePacket;
import org.pieszku.api.redis.packet.type.UpdateType;
import pl.pieszku.sectors.BukkitMain;

public class GuildInformationSynchronizeHandler extends RedisListener<GuildInformationSynchronizePacket> {

    private final GuildService guildService = BukkitMain.getInstance().getGuildService();

    public GuildInformationSynchronizeHandler() {
        super(BukkitMain.getInstance().getSectorName(), GuildInformationSynchronizePacket.class);
    }

    @Override
    public void onDecode(GuildInformationSynchronizePacket packet) {
        this.guildService.findGuildByName(packet.getGuildName()).ifPresent(guild -> {
            Guild guildSerialized = new Gson().fromJson(packet.getSerializedGuild(), Guild.class);
            switch (packet.getUpdateType()) {
                case UPDATE: {
                    this.guildService.getGuildList().remove(guild);
                    this.guildService.getGuildList().add(guildSerialized);
                    break;
                }
                case REMOVE: {
                    this.guildService.getGuildList().remove(guild);
                    break;
                }
            }
        });
        if (packet.getUpdateType().equals(UpdateType.CREATE)) {
            Guild guildSerialized = new Gson().fromJson(packet.getSerializedGuild(), Guild.class);
            this.guildService.getGuildList().add(guildSerialized);
        }
    }
}

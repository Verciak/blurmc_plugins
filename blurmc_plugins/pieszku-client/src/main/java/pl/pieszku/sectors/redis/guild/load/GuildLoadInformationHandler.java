package pl.pieszku.sectors.redis.guild.load;

import org.pieszku.api.service.GuildService;
import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.guild.load.GuildLoadInformationPacket;
import pl.pieszku.sectors.BukkitMain;

public class GuildLoadInformationHandler extends RedisListener<GuildLoadInformationPacket> {

    private final GuildService guildService = BukkitMain.getInstance().getGuildService();

    public GuildLoadInformationHandler() {
        super(BukkitMain.getInstance().getSectorName(), GuildLoadInformationPacket.class);
    }

    @Override
    public void onDecode(GuildLoadInformationPacket packet) {
        this.guildService.setGuildList(packet.getGuildLoadList());
        System.out.println("[MASTER-SERVER] Sent " + packet.getGuildLoadList().size() + " guilds.");
    }
}

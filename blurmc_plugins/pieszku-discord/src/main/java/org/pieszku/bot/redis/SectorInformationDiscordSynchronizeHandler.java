package org.pieszku.bot.redis;

import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.sector.sync.SectorInformationDiscordSynchronizePacket;
import org.pieszku.api.sector.SectorService;
import org.pieszku.bot.BotMain;

public class SectorInformationDiscordSynchronizeHandler extends RedisListener<SectorInformationDiscordSynchronizePacket> {

    private final SectorService sectorService = BotMain.getInstance().getSectorService();

    public SectorInformationDiscordSynchronizeHandler() {
        super("DISCORD", SectorInformationDiscordSynchronizePacket.class);
    }

    @Override
    public void onDecode(SectorInformationDiscordSynchronizePacket packet) {
        this.sectorService.findSectorByName(packet.getSectorName()).ifPresent(sector -> {
            sector.setPlayers(packet.getPlayers());
            sector.setLatestInformation(packet.getLatestInformation());
            sector.setOnline(packet.isOnline());
        });
    }
}

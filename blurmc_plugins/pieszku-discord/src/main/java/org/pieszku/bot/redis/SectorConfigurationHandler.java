package org.pieszku.bot.redis;

import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.sector.SectorConfigurationPacket;
import org.pieszku.api.sector.Sector;
import org.pieszku.api.sector.SectorService;
import org.pieszku.bot.BotMain;

public class SectorConfigurationHandler extends RedisListener<SectorConfigurationPacket> {

    private final SectorService sectorService = BotMain.getInstance().getSectorService();

    public SectorConfigurationHandler() {
        super("DISCORD", SectorConfigurationPacket.class);
    }

    @Override
    public void onDecode(SectorConfigurationPacket packet) {
        for (Sector sector : packet.getSectorJson().getSectors()) {
            this.sectorService.create(sector.getName(), sector.getSectorType(), sector.getLocationMinimum(), sector.getLocationMaximum(), sector.getMinSlots(), sector.getMaxSlots());
        }
    }
}

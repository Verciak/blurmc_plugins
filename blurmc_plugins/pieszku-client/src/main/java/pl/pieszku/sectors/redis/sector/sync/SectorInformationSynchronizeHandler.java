package pl.pieszku.sectors.redis.sector.sync;

import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.sector.sync.SectorInformationSynchronizePacket;
import org.pieszku.api.sector.SectorService;
import pl.pieszku.sectors.BukkitMain;

public class SectorInformationSynchronizeHandler extends RedisListener<SectorInformationSynchronizePacket> {

    private final SectorService sectorService = BukkitMain.getInstance().getSectorService();

    public SectorInformationSynchronizeHandler() {
        super(BukkitMain.getInstance().getSectorName(), SectorInformationSynchronizePacket.class);
    }

    @Override
    public void onDecode(SectorInformationSynchronizePacket packet) {
        this.sectorService.findSectorByName(packet.getSectorName()).ifPresent(sector -> {
            sector.setPlayers(packet.getPlayers());
            sector.setLatestInformation(packet.getLatestInformation());
            sector.setOnline(packet.isOnline());
        });
    }
}

package pl.pieszku.sectors.redis.sector;

import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.sector.SectorConfigurationPacket;
import org.pieszku.api.sector.Sector;
import org.pieszku.api.sector.SectorService;
import pl.pieszku.sectors.BukkitMain;

public class SectorConfigurationHandler extends RedisListener<SectorConfigurationPacket> {


    private final SectorService sectorService = BukkitMain.getInstance().getSectorService();

    public SectorConfigurationHandler() {
        super(BukkitMain.getInstance().getSectorName(), SectorConfigurationPacket.class);
    }
    @Override
    public void onDecode(SectorConfigurationPacket packet) {
        for (Sector sector : packet.getSectorJson().getSectors()) {
            this.sectorService.create(sector.getName(), sector.getSectorType(), sector.getLocationMinimum(), sector.getLocationMaximum(), sector.getMinSlots(), sector.getMaxSlots());
        }
        System.out.println("New connected: " + packet.getSenderSectorName());
    }
}

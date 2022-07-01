package pl.pieszku.sectors.redis.mute;

import org.pieszku.api.API;
import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.mute.load.MuteLoadInformationPacket;
import org.pieszku.api.service.MuteService;
import pl.pieszku.sectors.BukkitMain;

public class MuteLoadInformationHandler extends RedisListener<MuteLoadInformationPacket> {


    private final MuteService muteService = API.getInstance().getMuteService();

    public MuteLoadInformationHandler() {
        super(BukkitMain.getInstance().getSectorName(), MuteLoadInformationPacket.class);
    }

    @Override
    public void onDecode(MuteLoadInformationPacket packet) {
        this.muteService.setMuteList(packet.getMuteList());
    }
}

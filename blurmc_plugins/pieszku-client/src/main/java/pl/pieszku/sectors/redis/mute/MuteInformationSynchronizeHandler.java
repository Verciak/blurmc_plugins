package pl.pieszku.sectors.redis.mute;

import com.google.gson.Gson;
import org.pieszku.api.API;
import org.pieszku.api.objects.mute.Mute;
import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.mute.sync.MuteInformationSynchronizePacket;
import org.pieszku.api.service.MuteService;
import pl.pieszku.sectors.BukkitMain;

public class MuteInformationSynchronizeHandler extends RedisListener<MuteInformationSynchronizePacket> {


    private final MuteService muteService = API.getInstance().getMuteService();

    public MuteInformationSynchronizeHandler() {
        super(BukkitMain.getInstance().getSectorName(), MuteInformationSynchronizePacket.class);
    }

    @Override
    public void onDecode(MuteInformationSynchronizePacket packet) {
        switch (packet.getUpdateType()){
            case CREATE:{
                Mute mute = new Gson().fromJson(packet.getSerializedMute(), Mute.class);
                this.muteService.getMuteList().add(mute);
                break;
            }
            case UPDATE:{
                this.muteService.findMuteByNickName(packet.getNickName()).ifPresent(muteFind -> {
                Mute mute = new Gson().fromJson(packet.getSerializedMute(), Mute.class);
                this.muteService.getMuteList().remove(muteFind);
                this.muteService.getMuteList().add(mute);
                });
                break;
            }
            case REMOVE:{
                this.muteService.findMuteByNickName(packet.getNickName()).ifPresent(mute -> {
                    this.muteService.getMuteList().remove(mute);
                });
                break;
            }
        }
    }
}

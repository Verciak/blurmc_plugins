package org.pieszku.server.handler.mute;

import com.google.gson.Gson;
import org.pieszku.api.API;
import org.pieszku.api.objects.mute.Mute;
import org.pieszku.api.objects.repository.MuteRepository;
import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.mute.sync.MuteInformationSynchronizePacket;
import org.pieszku.api.sector.SectorService;
import org.pieszku.api.service.MuteService;
import org.pieszku.server.ServerMain;

public class MuteInformationSynchronizeHandler extends RedisListener<MuteInformationSynchronizePacket> {


    private final MuteService muteService = API.getInstance().getMuteService();
    private final MuteRepository muteRepository = API.getInstance().getMuteRepository();
    private final SectorService sectorService = ServerMain.getInstance().getSectorService();

    public MuteInformationSynchronizeHandler() {
        super("MASTER", MuteInformationSynchronizePacket.class);
    }

    @Override
    public void onDecode(MuteInformationSynchronizePacket packet) {
        switch (packet.getUpdateType()){
            case CREATE:{
                Mute mute = new Gson().fromJson(packet.getSerializedMute(), Mute.class);
                new MuteInformationSynchronizePacket(mute.getNickName(), packet.getUpdateType(), new Gson().toJson(mute)).sendToAllSectors(this.sectorService);
                this.muteRepository.update(mute, mute.getNickName(), packet.getUpdateType());
                this.muteService.getMuteList().add(mute);
                break;
            }
            case UPDATE:{
                this.muteService.findMuteByNickName(packet.getNickName()).ifPresent(muteFind -> {
                Mute mute = new Gson().fromJson(packet.getSerializedMute(), Mute.class);
                new MuteInformationSynchronizePacket(mute.getNickName(), packet.getUpdateType(), new Gson().toJson(mute)).sendToAllSectors(this.sectorService);
                this.muteRepository.update(mute, mute.getNickName(), packet.getUpdateType());
                this.muteService.getMuteList().remove(muteFind);
                this.muteService.getMuteList().add(mute);
                });
                break;
            }
            case REMOVE:{
                this.muteService.findMuteByNickName(packet.getNickName()).ifPresent(mute -> {
                    this.muteService.getMuteList().remove(mute);
                    new MuteInformationSynchronizePacket(mute.getNickName(), packet.getUpdateType(), new Gson().toJson(mute)).sendToAllSectors(this.sectorService);
                    this.muteRepository.update(mute, mute.getNickName(), packet.getUpdateType());
                });
                break;
            }
        }
    }
}

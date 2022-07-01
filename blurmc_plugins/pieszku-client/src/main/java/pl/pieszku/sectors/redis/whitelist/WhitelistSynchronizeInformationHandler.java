package pl.pieszku.sectors.redis.whitelist;

import com.google.gson.Gson;
import org.pieszku.api.API;
import org.pieszku.api.proxy.global.WhitelistServer;
import org.pieszku.api.proxy.global.WhitelistServerService;
import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.whitelist.sync.WhitelistSynchronizeInformationPacket;
import pl.pieszku.sectors.BukkitMain;

public class WhitelistSynchronizeInformationHandler extends RedisListener<WhitelistSynchronizeInformationPacket> {


    private final WhitelistServerService whitelistServerService = API.getInstance().getWhitelistServerService();

    public WhitelistSynchronizeInformationHandler() {
        super(BukkitMain.getInstance().getSectorName(), WhitelistSynchronizeInformationPacket.class);
    }

    @Override
    public void onDecode(WhitelistSynchronizeInformationPacket packet) {
        switch (packet.getUpdateType()){
            case CREATE: {
                WhitelistServer whitelistServerSerialized = new Gson().fromJson(packet.getSerializedWhitelist(), WhitelistServer.class);
                this.whitelistServerService.getWhitelistServersList().add(whitelistServerSerialized);
                break;
            }
            case REMOVE:{
                this.whitelistServerService.findWhitelistByServerName(packet.getChannelName()).ifPresent(whitelistServer -> {
                    this.whitelistServerService.getWhitelistServersList().remove(whitelistServer);
                });
                break;
            }
            case UPDATE:{
                this.whitelistServerService.findWhitelistByServerName(packet.getChannelName()).ifPresent(whitelistServer -> {
                    WhitelistServer whitelistServerSerialized = new Gson().fromJson(packet.getSerializedWhitelist(), WhitelistServer.class);
                    this.whitelistServerService.getWhitelistServersList().remove(whitelistServer);
                    this.whitelistServerService.getWhitelistServersList().add(whitelistServerSerialized);
                });
                break;
            }
        }
    }
}
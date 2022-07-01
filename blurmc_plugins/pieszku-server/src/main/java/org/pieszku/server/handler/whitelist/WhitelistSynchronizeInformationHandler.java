package org.pieszku.server.handler.whitelist;

import com.google.gson.Gson;
import org.pieszku.api.API;
import org.pieszku.api.proxy.ProxyService;
import org.pieszku.api.proxy.global.WhitelistServer;
import org.pieszku.api.proxy.global.WhitelistServerRepository;
import org.pieszku.api.proxy.global.WhitelistServerService;
import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.whitelist.sync.WhitelistSynchronizeInformationPacket;
import org.pieszku.api.sector.SectorService;
import org.pieszku.server.ServerMain;

public class WhitelistSynchronizeInformationHandler extends RedisListener<WhitelistSynchronizeInformationPacket> {


    private final ProxyService proxyService = API.getInstance().getProxyService();
    private final SectorService sectorService = ServerMain.getInstance().getSectorService();
    private final WhitelistServerService whitelistServerService = API.getInstance().getWhitelistServerService();
    private final WhitelistServerRepository whitelistServerRepository = API.getInstance().getWhitelistServerRepository();

    public WhitelistSynchronizeInformationHandler() {
        super("MASTER", WhitelistSynchronizeInformationPacket.class);
    }

    @Override
    public void onDecode(WhitelistSynchronizeInformationPacket packet) {
        switch (packet.getUpdateType()){
            case CREATE: {
                WhitelistServer whitelistServerSerialized = new Gson().fromJson(packet.getSerializedWhitelist(), WhitelistServer.class);
                this.whitelistServerService.getWhitelistServersList().add(whitelistServerSerialized);
                this.whitelistServerRepository.update(whitelistServerSerialized, whitelistServerSerialized.getChannelName(), packet.getUpdateType());
                new WhitelistSynchronizeInformationPacket(packet.getChannelName(), packet.getUpdateType(), new Gson().toJson(whitelistServerSerialized)).sendToAllServers(this.sectorService, this.proxyService);
                break;
            }
            case REMOVE:{
                this.whitelistServerService.findWhitelistByServerName(packet.getChannelName()).ifPresent(whitelistServer -> {
                    this.whitelistServerService.getWhitelistServersList().remove(whitelistServer);
                    this.whitelistServerRepository.update(whitelistServer, whitelistServer.getChannelName(), packet.getUpdateType());
                    new WhitelistSynchronizeInformationPacket(packet.getChannelName(), packet.getUpdateType(), new Gson().toJson(whitelistServer)).sendToAllServers(this.sectorService, this.proxyService);
                });
                break;
            }
            case UPDATE:{
                this.whitelistServerService.findWhitelistByServerName(packet.getChannelName()).ifPresent(whitelistServer -> {
                    WhitelistServer whitelistServerSerialized = new Gson().fromJson(packet.getSerializedWhitelist(), WhitelistServer.class);
                    this.whitelistServerService.getWhitelistServersList().remove(whitelistServer);
                    this.whitelistServerService.getWhitelistServersList().add(whitelistServerSerialized);
                    this.whitelistServerRepository.update(whitelistServerSerialized, whitelistServerSerialized.getChannelName(), packet.getUpdateType());
                    new WhitelistSynchronizeInformationPacket(packet.getChannelName(), packet.getUpdateType(), new Gson().toJson(whitelistServerSerialized)).sendToAllServers(this.sectorService, this.proxyService);
                });
                break;
            }
        }
    }
}

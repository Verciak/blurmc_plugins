package pl.pieszku.proxy.redis.whitelist;

import org.pieszku.api.API;
import org.pieszku.api.proxy.global.WhitelistServerService;
import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.whitelist.load.WhitelistLoadInformationPacket;
import pl.pieszku.proxy.ProxyMain;

public class WhitelistLoadInformationHandler extends RedisListener<WhitelistLoadInformationPacket> {


    private final WhitelistServerService whitelistServerService = API.getInstance().getWhitelistServerService();

    public WhitelistLoadInformationHandler() {
        super(ProxyMain.getInstance().getProxyName(), WhitelistLoadInformationPacket.class);
    }

    @Override
    public void onDecode(WhitelistLoadInformationPacket packet) {
        this.whitelistServerService.setWhitelistServersList(packet.getWhitelistServerList());
        System.out.println("[MASTER-SERVER] Sent " + this.whitelistServerService.getWhitelistServersList().size() + " whitelist.");
    }
}

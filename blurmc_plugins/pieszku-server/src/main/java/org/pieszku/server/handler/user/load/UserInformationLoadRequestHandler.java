package org.pieszku.server.handler.user.load;

import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.user.load.UserInformationLoadPacket;
import org.pieszku.api.redis.packet.user.load.UserInformationLoadRequestPacket;
import org.pieszku.api.service.UserService;
import org.pieszku.server.ServerMain;

public class UserInformationLoadRequestHandler extends RedisListener<UserInformationLoadRequestPacket> {

    private final UserService userService = ServerMain.getInstance().getUserService();

    public UserInformationLoadRequestHandler() {
        super("MASTER", UserInformationLoadRequestPacket.class);
    }

    @Override
    public void onDecode(UserInformationLoadRequestPacket packet) {
        new UserInformationLoadPacket(this.userService.getUserMap()).sendToChannel(packet.getSectorName());
    }
}

package org.pieszku.server.handler.user.request;

import com.google.gson.Gson;
import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.user.UserTransferInformationPacket;
import org.pieszku.api.redis.packet.user.request.UserTransferInformationRequestPacket;
import org.pieszku.api.service.UserService;
import org.pieszku.server.ServerMain;

public class UserTransferInformationRequestHandler extends RedisListener<UserTransferInformationRequestPacket> {


    private final UserService userService = ServerMain.getInstance().getUserService();

    public UserTransferInformationRequestHandler() {
        super("MASTER", UserTransferInformationRequestPacket.class);
    }
    @Override
    public void onDecode(UserTransferInformationRequestPacket packet) {
        this.userService.findUserByNickName(packet.getNickName()).ifPresent(user -> {
            new UserTransferInformationPacket(packet.getNickName(), new Gson().toJson(user)).sendToChannel(packet.getSectorName());
        });
    }
}

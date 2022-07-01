package org.pieszku.server.handler.user;

import com.google.gson.Gson;
import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.user.UserPlayOutInformationPacket;
import org.pieszku.api.redis.packet.user.request.UserPlayOutRequestPacket;
import org.pieszku.api.service.UserService;
import org.pieszku.server.ServerMain;

public class UserPlayOutRequestHandler extends RedisListener<UserPlayOutRequestPacket> {

    private final UserService userService = ServerMain.getInstance().getUserService();

    public UserPlayOutRequestHandler() {
        super("MASTER", UserPlayOutRequestPacket.class);
    }

    @Override
    public void onDecode(UserPlayOutRequestPacket packet) {
        this.userService.findUserByNickName(packet.getNickName()).ifPresent(user -> {
            new UserPlayOutInformationPacket(user.getNickName(), new Gson().toJson(user)).sendToChannel(packet.getSectorSender());
        });
    }
}

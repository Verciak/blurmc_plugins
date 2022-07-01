package org.pieszku.server.handler.user.sync;

import com.google.gson.Gson;
import org.pieszku.api.API;
import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.type.UpdateType;
import org.pieszku.api.redis.packet.user.sync.UserInformationSynchronizePacket;
import org.pieszku.api.objects.user.User;
import org.pieszku.api.service.UserService;
import org.pieszku.server.ServerMain;

import java.util.Optional;

public class UserInformationSynchronizeHandler extends RedisListener<UserInformationSynchronizePacket> {

    private final UserService userService = ServerMain.getInstance().getUserService();

    public UserInformationSynchronizeHandler() {
        super("MASTER", UserInformationSynchronizePacket.class);
    }

    @Override
    public void onDecode(UserInformationSynchronizePacket packet) {
        if(packet.getUpdateType().equals(UpdateType.CREATE)){
            Optional<User> optionalUser = this.userService.findUserByNickName(packet.getNickName());
            if(optionalUser.isPresent()){
                System.out.println("[MASTER-SERVER] User exists in userMap: " + packet.getNickName());
                return;
            }
            User user = new Gson().fromJson(packet.getSerializedUser(), User.class);
            this.userService.getUserMap().put(packet.getNickName(), user);
            API.getInstance().getUserRepository().update(user, user.getNickName(), UpdateType.CREATE);
            return;
        }
        if(packet.getUpdateType().equals(UpdateType.UPDATE)){
            Optional<User> optionalUser = this.userService.findUserByNickName(packet.getNickName());
            if(!optionalUser.isPresent()){
                User user = new Gson().fromJson(packet.getSerializedUser(), User.class);
                this.userService.getUserMap().put(packet.getNickName(), user);
                System.out.println("[MASTER-SERVER] User is not exists in userMap: " + packet.getNickName());
                System.out.println("[MASTER-SERVER] User added: " + user.getNickName());
                API.getInstance().getUserRepository().update(user, user.getNickName(), UpdateType.CREATE);
                return;
            }
            User userExists = optionalUser.get();
            User user = new Gson().fromJson(packet.getSerializedUser(), User.class);
            this.userService.getUserMap().remove(userExists.getNickName(), userExists);
            this.userService.getUserMap().put(packet.getNickName(), user);
            API.getInstance().getUserRepository().update(user, user.getNickName(), UpdateType.UPDATE);
            return;
        }
        if(packet.getUpdateType().equals(UpdateType.REMOVE)) {
            this.userService.findUserByNickName(packet.getNickName()).ifPresent(user -> {
                this.userService.getUserMap().remove(user.getNickName(), user);
                System.out.println("[MASTER-SERVER] User: " + user.getNickName() + " success delete from userMap");
                API.getInstance().getUserRepository().update(user, user.getNickName(), UpdateType.REMOVE);
            });
        }
    }
}

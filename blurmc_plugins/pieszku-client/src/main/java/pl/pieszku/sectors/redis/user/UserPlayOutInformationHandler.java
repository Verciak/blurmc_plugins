package pl.pieszku.sectors.redis.user;

import com.google.gson.Gson;
import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.user.UserPlayOutInformationPacket;
import org.pieszku.api.objects.user.User;
import org.pieszku.api.service.UserService;
import pl.pieszku.sectors.BukkitMain;

public class UserPlayOutInformationHandler extends RedisListener<UserPlayOutInformationPacket> {

    private final UserService userService = BukkitMain.getInstance().getUserService();

    public UserPlayOutInformationHandler(){
        super(BukkitMain.getInstance().getSectorName(), UserPlayOutInformationPacket.class);
    }

    @Override
    public void onDecode(UserPlayOutInformationPacket packet) {
        this.userService.findUserByNickName(packet.getNickName()).ifPresent(user -> {
            this.userService.getUserMap().remove(user.getNickName(), user);
        });
        this.userService.getUserMap().put(packet.getNickName(), new Gson().fromJson(packet.getUserSerialized(), User.class));
    }
}

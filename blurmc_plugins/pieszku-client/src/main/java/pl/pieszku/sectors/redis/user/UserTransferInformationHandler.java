package pl.pieszku.sectors.redis.user;

import com.google.gson.Gson;
import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.user.UserTransferInformationPacket;
import org.pieszku.api.objects.user.User;
import org.pieszku.api.service.UserService;
import pl.pieszku.sectors.BukkitMain;

public class UserTransferInformationHandler extends RedisListener<UserTransferInformationPacket> {


    private final UserService userService = BukkitMain.getInstance().getUserService();

    public UserTransferInformationHandler() {
        super(BukkitMain.getInstance().getSectorName(), UserTransferInformationPacket.class);
    }
    @Override
    public void onDecode(UserTransferInformationPacket packet) {
        this.userService.findUserByNickName(packet.getNickName()).ifPresent(user -> {
            this.userService.getUserMap().remove(packet.getNickName(), user);
        });
        User user = new Gson().fromJson(packet.getSerializedUser(), User.class);
        this.userService.getUserMap().put(user.getNickName(), user);
    }
}

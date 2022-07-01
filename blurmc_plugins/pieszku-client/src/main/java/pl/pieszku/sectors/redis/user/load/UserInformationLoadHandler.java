package pl.pieszku.sectors.redis.user.load;

import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.user.load.UserInformationLoadPacket;
import org.pieszku.api.service.UserService;
import pl.pieszku.sectors.BukkitMain;

public class UserInformationLoadHandler extends RedisListener<UserInformationLoadPacket> {

    private final UserService userService = BukkitMain.getInstance().getUserService();

    public UserInformationLoadHandler() {
        super(BukkitMain.getInstance().getSectorName(), UserInformationLoadPacket.class);
    }

    @Override
    public void onDecode(UserInformationLoadPacket packet) {
        this.userService.setUserMap(packet.getUserMap());
        System.out.println("[MASTER-SERVER] Sent " + packet.getUserMap().size() + " user.");
    }
}

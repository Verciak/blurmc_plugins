package org.pieszku.api.redis.packet.user.load;

import org.pieszku.api.redis.packet.Packet;
import org.pieszku.api.objects.user.User;

import java.util.concurrent.ConcurrentHashMap;

public class UserInformationLoadPacket extends Packet {

    private final ConcurrentHashMap<String, User> userMap;

    public UserInformationLoadPacket(ConcurrentHashMap<String, User> userMap){
        this.userMap = userMap;
    }

    public ConcurrentHashMap<String, User> getUserMap() {
        return userMap;
    }

    @Override
    public String toString() {
        return "UserInformationLoadPacket{" +
                "userMap=" + userMap +
                '}';
    }
}

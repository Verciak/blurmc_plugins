package pl.pieszku.sectors.redis.client;

import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.client.ChatSynchronizePacket;
import pl.pieszku.sectors.BukkitMain;

public class ChatSynchronizeHandler extends RedisListener<ChatSynchronizePacket> {

    public ChatSynchronizeHandler() {
        super(BukkitMain.getInstance().getSectorName(), ChatSynchronizePacket.class);
    }

    @Override
    public void onDecode(ChatSynchronizePacket packet) {
        BukkitMain.getInstance().setChatStatus(packet.isChatStatus());
    }
}

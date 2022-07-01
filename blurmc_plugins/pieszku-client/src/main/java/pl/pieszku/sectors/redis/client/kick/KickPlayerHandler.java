package pl.pieszku.sectors.redis.client.kick;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.client.KickPlayerPacket;
import pl.pieszku.sectors.redis.client.kick.runnable.KickPlayerRunnableHandler;

public class KickPlayerHandler extends RedisListener<KickPlayerPacket> {

    public KickPlayerHandler() {
        super("SECTORS", KickPlayerPacket.class);
    }

    @Override
    public void onDecode(KickPlayerPacket packet) {
        Player player = Bukkit.getPlayerExact(packet.getNickName());
        if(player == null)return;
        new KickPlayerRunnableHandler(player, packet.getReason()).start();
    }
}

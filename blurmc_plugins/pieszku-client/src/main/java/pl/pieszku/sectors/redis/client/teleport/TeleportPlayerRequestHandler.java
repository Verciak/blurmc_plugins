package pl.pieszku.sectors.redis.client.teleport;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.client.teleport.TeleportPlayerInformationPacket;
import org.pieszku.api.redis.packet.client.teleport.TeleportPlayerRequestPacket;

public class TeleportPlayerRequestHandler extends RedisListener<TeleportPlayerRequestPacket> {

    public TeleportPlayerRequestHandler() {
        super("SECTORS", TeleportPlayerRequestPacket.class);
    }

    @Override
    public void onDecode(TeleportPlayerRequestPacket packet) {
        Player player = Bukkit.getPlayerExact(packet.getNickNameTeleport());
        if(player == null)return;
        TeleportPlayerInformationPacket teleportPlayerInformationPacket = new TeleportPlayerInformationPacket();
        teleportPlayerInformationPacket.setTeleportDelay(packet.getTimeDelay());
        teleportPlayerInformationPacket.setNickNameTargetTo(packet.getNickNameTargetTo());
        teleportPlayerInformationPacket.setWorld(player.getWorld().getName());
        teleportPlayerInformationPacket.setX(player.getLocation().getBlockX());
        teleportPlayerInformationPacket.setY(player.getLocation().getBlockY());
        teleportPlayerInformationPacket.setZ(player.getLocation().getBlockZ());
        teleportPlayerInformationPacket.sendToChannel("SECTORS");
    }
}

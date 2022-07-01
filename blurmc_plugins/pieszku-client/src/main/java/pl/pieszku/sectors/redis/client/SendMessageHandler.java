package pl.pieszku.sectors.redis.client;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.client.SendMessagePacket;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.utilities.ChatUtilities;

public class SendMessageHandler extends RedisListener<SendMessagePacket> {


    public SendMessageHandler() {
        super(BukkitMain.getInstance().getSectorName(), SendMessagePacket.class);
    }

    @Override
    public void onDecode(SendMessagePacket packet) {
        switch (packet.getReceiverType()){
            case ALL:{
                switch (packet.getMessageType()){
                    case CHAT:{
                        Bukkit.broadcastMessage(ChatUtilities.colored(packet.getMessage()));
                        break;
                    }
                    case TITLE:{
                        Bukkit.getOnlinePlayers().forEach(player -> {
                            player.sendTitle(ChatUtilities.colored(packet.getMessageTitle()), ChatUtilities.colored(packet.getMessage()));
                        });
                        break;
                    }
                    case ACTIONBAR:{
                        Bukkit.getOnlinePlayers().forEach(player -> {
                            ChatUtilities.sendActionBar(player, packet.getMessage());
                        });
                        break;
                    }
                }
                break;
            }
            case PLAYER:{
                Player player = Bukkit.getPlayerExact(packet.getNickNameReceiver());
                if(player == null)return;
                switch (packet.getMessageType()){
                    case CHAT:{
                        player.sendMessage(ChatUtilities.colored(packet.getMessage()));
                        break;
                    }
                    case TITLE: {
                        player.sendTitle(ChatUtilities.colored(packet.getMessageTitle()), ChatUtilities.colored(packet.getMessage()));
                        break;
                    }
                    case ACTIONBAR: {
                        ChatUtilities.sendActionBar(player, packet.getMessage());
                        break;
                    }
                }
                break;
            }
        }
    }
}

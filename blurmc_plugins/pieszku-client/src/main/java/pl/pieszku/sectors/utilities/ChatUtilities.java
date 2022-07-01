package pl.pieszku.sectors.utilities;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class ChatUtilities {

    public static String colored(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static List<String> colored(List<String> text) {
        return text.stream().map(ChatUtilities::colored).collect(Collectors.toList());
    }

    public static void sendActionBar(Player player, String text) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(colored(text)));
    }
}

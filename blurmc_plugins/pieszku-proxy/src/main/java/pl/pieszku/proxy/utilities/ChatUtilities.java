package pl.pieszku.proxy.utilities;

import net.md_5.bungee.api.ChatColor;

public class ChatUtilities {

    public static String colored(String text){
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}

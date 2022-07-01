package pl.pieszku.sectors.handler;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignEditHandler implements Listener {

    @EventHandler
    public void onSignEdit(SignChangeEvent event){
        Player player = event.getPlayer();
        player.sendMessage(event.getLine(0));
    }
}

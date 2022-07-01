package pl.pieszku.sectors.handler.gameplay;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class BlockFromGameplayHandler implements Listener {

    @EventHandler
    public void onBlockFrom(BlockFromToEvent event){
        event.setCancelled(true);
    }
}

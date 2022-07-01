package pl.pieszku.sectors.gui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class MinecraftGUIHandler implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(event.getInventory().getHolder() instanceof MinecraftGUI){
            ((MinecraftGUI) event.getInventory().getHolder()).handleClick(event);
        }
    }
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onClose(InventoryCloseEvent event){
        if(event.getInventory().getHolder() instanceof MinecraftGUI){
            ((MinecraftGUI) event.getInventory().getHolder()).handleClose(event);
        }
    }
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onOpen(InventoryOpenEvent event){
        if(event.getInventory().getHolder() instanceof MinecraftGUI){
            ((MinecraftGUI) event.getInventory().getHolder()).handleOpen(event);
        }
    }
}

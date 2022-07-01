package pl.pieszku.sectors.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public interface MinecraftGUI extends InventoryHolder {


    default void handleClick(InventoryClickEvent event){

    }
    default void handleOpen(InventoryOpenEvent event){

    }
    default void handleClose(InventoryCloseEvent event){

    }
    @NotNull
    @Override
    Inventory getInventory();

    default void open(Player player){
        player.openInventory(this.getInventory());
    }
}

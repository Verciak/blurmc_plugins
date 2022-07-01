package pl.pieszku.sectors.helper;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class InventoryHelper implements Listener {


    public static final List<InventoryHelper> inventoryHelpers = new ArrayList<>();
    private Inventory inventory;
    private Consumer<InventoryClickEvent> inventoryClickEventConsumer;
    private Consumer<InventoryCloseEvent> inventoryCloseEventConsumer;

    public InventoryHelper(int slot, String title){
        this.inventory = Bukkit.createInventory(null, slot, title);
        inventoryHelpers.add(this);
    }
    public InventoryHelper(InventoryType inventoryType, String title){
        this.inventory = Bukkit.createInventory(null, inventoryType, title);
        inventoryHelpers.add(this);
    }
    public InventoryHelper(){

    }
    public InventoryHelper(Inventory inventory){
        this.inventory = inventory;
        inventoryHelpers.add(this);
    }
    public void click(Consumer<InventoryClickEvent> inventoryClickEventConsumer){
        this.inventoryClickEventConsumer = inventoryClickEventConsumer;
    }

    public void close(Consumer<InventoryCloseEvent> inventoryCloseEventConsumer) {
        this.inventoryCloseEventConsumer = inventoryCloseEventConsumer;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public static InventoryHelper findInventoryHelper(Inventory inventory){
        return inventoryHelpers
                .stream()
                .filter(inventoryHelper -> inventoryHelper.inventory.equals(inventory))
                .findFirst()
                .orElse(null);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(InventoryClickEvent event){
        if(event.isCancelled())return;
        InventoryHelper inventoryHelper = findInventoryHelper(event.getInventory());
        if(inventoryHelper != null){
            if(inventoryHelper.inventoryClickEventConsumer != null) {
                if(event.getInventory().getType() == InventoryType.PLAYER)return;
                inventoryHelper.inventoryClickEventConsumer.accept(event);
            }
        }
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void onClose(InventoryCloseEvent event){
        InventoryHelper inventoryHelper = findInventoryHelper(event.getInventory());
        if(inventoryHelper != null){
            if(inventoryHelper.inventoryCloseEventConsumer != null) {
                inventoryHelper.inventoryCloseEventConsumer.accept(event);
            }
        }
    }
}

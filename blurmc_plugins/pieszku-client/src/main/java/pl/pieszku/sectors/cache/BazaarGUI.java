package pl.pieszku.sectors.cache;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import pl.pieszku.sectors.gui.MinecraftGUI;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.util.function.Consumer;

public class BazaarGUI implements MinecraftGUI {


    private final int page;
    private final String inventoryName;
    private final Inventory inventory;
    private Consumer<InventoryClickEvent> eventConsumer;

    public BazaarGUI(int page){
        this.page = page;
        this.inventoryName = ChatUtilities.colored("&b&lBazaar&8: &7(&f" + page + "&7)");
        this.inventory = Bukkit.createInventory(this, 54, this.inventoryName);
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public int getPage() {
        return page;
    }
    public void handleClickConsumer(Consumer<InventoryClickEvent> eventConsumer){
        this.eventConsumer = eventConsumer;
    }

    @Override
    public void handleClick(InventoryClickEvent event) {
        this.eventConsumer.accept(event);
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }
}

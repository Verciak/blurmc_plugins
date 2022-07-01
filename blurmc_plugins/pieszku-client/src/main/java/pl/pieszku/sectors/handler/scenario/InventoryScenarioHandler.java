package pl.pieszku.sectors.handler.scenario;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.EnchantingInventory;
import org.pieszku.api.service.UserService;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.helper.ItemHelper;
import pl.pieszku.sectors.service.MasterConnectionHeartbeatService;

public class InventoryScenarioHandler implements Listener {

    private final UserService userService = BukkitMain.getInstance().getUserService();
    private final MasterConnectionHeartbeatService masterConnectionHeartbeatService = BukkitMain.getInstance().getMasterConnectionHeartbeatService();

    @EventHandler(priority = EventPriority.HIGH)
    public void onClick(InventoryClickEvent event) {
        if (!masterConnectionHeartbeatService.isConnected()) {
            event.setCancelled(true);
            return;
        }

        Player player = (Player) event.getWhoClicked();
        this.userService.findUserByNickName(player.getName()).ifPresent(user -> {
            if (!user.isInteractSector()) {
                event.setCancelled(true);
            }
        });
        if(event.getInventory() instanceof EnchantingInventory){
            if(event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.LAPIS_LAZULI){
                event.setCancelled(true);
                player.updateInventory();
            }
        }
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void onClose(InventoryCloseEvent event){

        if(event.getInventory() instanceof EnchantingInventory){
            Player player = (Player) event.getPlayer();
            player.getInventory().remove(Material.LAPIS_LAZULI);
            player.updateInventory();
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onDragClick(InventoryDragEvent event) {
        if (!masterConnectionHeartbeatService.isConnected()) {
            event.setCancelled(true);
            return;
        }

        Player player = (Player) event.getWhoClicked();
        this.userService.findUserByNickName(player.getName()).ifPresent(user -> {
            if (!user.isInteractSector()) {
                event.setCancelled(true);
            }
        });
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onOpen(InventoryOpenEvent event) {
        if (!masterConnectionHeartbeatService.isConnected()) {
            event.setCancelled(true);
            return;
        }

        Player player = (Player) event.getPlayer();
        this.userService.findUserByNickName(player.getName()).ifPresent(user -> {
            if (!user.isInteractSector()) {
                event.setCancelled(true);
            }
        });
        if(event.getInventory() instanceof EnchantingInventory){
            event.getInventory().setItem(1, new ItemHelper(Material.LAPIS_LAZULI, 64).toItemStack());
        }
    }
}

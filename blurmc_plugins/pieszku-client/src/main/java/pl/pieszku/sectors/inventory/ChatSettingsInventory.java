package pl.pieszku.sectors.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.pieszku.api.objects.user.UserSettingMessage;
import org.pieszku.api.service.UserService;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.helper.InventoryHelper;
import pl.pieszku.sectors.helper.ItemHelper;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.util.Arrays;

public class ChatSettingsInventory {

    private final UserService userService = BukkitMain.getInstance().getUserService();

    public void show(Player player){
        InventoryHelper inventoryHelper = new InventoryHelper(27, ChatUtilities.colored("&8&l:: &bZarządzaj ustawieniami &8&l::"));
        Inventory inventory = inventoryHelper.getInventory();

        Integer[] glassBlueSlots = new Integer[]{0, 2, 4, 7, 17, 18, 19, 21, 23, 25};
        Integer[] glassDarkBlueSlots = new Integer[]{1, 3, 5, 6, 8, 9, 20, 22, 24, 26};

        Arrays.stream(glassBlueSlots).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack()));
        Arrays.stream(glassDarkBlueSlots).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack()));


        this.userService.findUserByNickName(player.getName()).ifPresent(user -> {

            user.getUserSettingMessageList().forEach(userSettingMessage -> {

                ItemHelper itemSetting = new ItemHelper(Material.BOOK)
                        .setName("&8&m---&b&m---&e&m---&8&m---&a&m---&8&m--&f&m---")
                        .addLore("&f" + userSettingMessage.getMessagePolishName())
                        .addLore("    " + (userSettingMessage.isStatus() ? "&a&lAKTYWNY" : "&c&lWYLĄCZONE"))
                        .addLore("&8&m---&b&m---&e&m---&8&m---&a&m---&8&m--&f&m---");
                inventory.setItem(userSettingMessage.getSlot(), itemSetting.toItemStack());
            });
            user.getUserSettingMessageList().forEach(userSettingMessage -> {

                ItemHelper itemSetting = new ItemHelper((userSettingMessage.isStatus() ? Material.LIME_DYE : Material.RED_DYE))
                        .setName((userSettingMessage.isStatus() ? "&a&lAKTYWNY" : "&c&lWYLĄCZONE"));
                inventory.setItem(userSettingMessage.getSlot() + 9, itemSetting.toItemStack());
            });


            inventoryHelper.click(event -> {
                event.setCancelled(true);

                UserSettingMessage userSettingMessage = user.findUserSettingMessageBySlot(event.getSlot());
                if(userSettingMessage == null)return;

                userSettingMessage.setStatus(user, !userSettingMessage.isStatus());
                this.show(player);

            });

        });


        player.openInventory(inventory);

    }
}

package pl.pieszku.sectors.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.pieszku.api.objects.user.User;
import org.pieszku.api.objects.user.UserEnderchest;
import org.pieszku.api.service.UserService;
import org.pieszku.api.type.GroupType;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.helper.InventoryHelper;
import pl.pieszku.sectors.helper.ItemHelper;
import pl.pieszku.sectors.serializer.ItemSerializer;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.util.Arrays;

public class EnderchestInventory {

    private final UserService userService = BukkitMain.getInstance().getUserService();

    public void show(Player player){
        this.userService.findUserByNickName(player.getName()).ifPresent(user -> {

            InventoryHelper inventoryHelper = new InventoryHelper(27, ChatUtilities.colored("&8* &3Lista enderchestów &8*"));
            Inventory inventory = inventoryHelper.getInventory();

            Integer[] glassBlueSlots = new Integer[]{0, 2, 4, 7, 17, 18, 19, 21, 23, 25};
            Integer[] glassDarkBlueSlots = new Integer[]{1, 3, 5, 6, 8, 9, 16, 20, 22, 24, 26};

            Arrays.stream(glassBlueSlots).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack()));
            Arrays.stream(glassDarkBlueSlots).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack()));


            user.getUserEnderchestList().forEach(userEnderchest -> {

                ItemHelper homeItem = new ItemHelper(Material.PLAYER_HEAD, 1, (short) 3)
                        .setOwnerUrl("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTg4MTQ5ZTU2Y2RlMDJlZWU2MjA5ODI4ODRiODkzNjM0NzU2YmU5MTQxZjJlMjc3N2I1MWNiMGE2NmMzMWRjZSJ9fX0=")
                        .setName("&d&lEnderchest: &5&l" + userEnderchest.getName())
                        .addLore("")
                        .addLore("&fDostęp od rangi: " + (userEnderchest.getGroupType().equals(GroupType.PLAYER) ? "&aGRACZ" : userEnderchest.getGroupType().getPrefix()))
                        .addLore((!GroupType.hasPermission(user, userEnderchest.getGroupType()) ? "&cNie posiadasz dostępu" : "&aPosiadasz dostęp"))
                        .addLore("")
                        .addLore("&3Lewy &8- &fAby otworzyć enderchest")
                        .addLore("");

                inventory.setItem(userEnderchest.getInventorySlot(), homeItem.toItemStack());
            });
            player.openInventory(inventory);

            inventoryHelper.click(event -> {
                event.setCancelled(true);

                UserEnderchest userEnderchest = user.findUserEnderchestBySlot(event.getSlot());
                if(userEnderchest == null)return;

                if(!GroupType.hasPermission(user, userEnderchest.getGroupType())){
                    player.closeInventory();
                    player.sendTitle(ChatUtilities.colored("&5&lENDERCHEST"), ChatUtilities.colored("&fTen enderchest wymaga rangi: " + userEnderchest.getGroupType().getPrefix()));
                    return;
                }

                this.showEnderchest(player, user, userEnderchest);

            });

        });
    }
    public void showEnderchest(Player player, User user, UserEnderchest userEnderchest){
        InventoryHelper inventoryHelper = new InventoryHelper(54, ChatUtilities.colored("&dEnderchest: &5" + userEnderchest.getName()));
        Inventory inventory = inventoryHelper.getInventory();

        if(userEnderchest.getInventorySerialized().equals("null")){
            userEnderchest.setInventorySerialized(user, ItemSerializer.decodeItems(new ItemStack[]{}));
        }
        inventory.setContents(ItemSerializer.encodeItem(userEnderchest.getInventorySerialized()));

        inventoryHelper.close(event -> {

            userEnderchest.setInventorySerialized(user, ItemSerializer.decodeItems(inventory.getContents()));

        });

        player.openInventory(inventory);
    }
}

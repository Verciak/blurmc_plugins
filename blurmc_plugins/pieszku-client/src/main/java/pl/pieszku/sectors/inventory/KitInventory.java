package pl.pieszku.sectors.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.pieszku.api.objects.kit.Kit;
import org.pieszku.api.service.KitService;
import org.pieszku.api.service.UserService;
import org.pieszku.api.type.GroupType;
import org.pieszku.api.type.TimeType;
import org.pieszku.api.utilities.DataUtilities;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.helper.InventoryHelper;
import pl.pieszku.sectors.helper.ItemHelper;
import pl.pieszku.sectors.serializer.ItemSerializer;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.util.Arrays;

public class KitInventory {

    private final KitService kitService = BukkitMain.getInstance().getKitService();
    private final UserService userService = BukkitMain.getInstance().getUserService();

    public void show(Player player){
        InventoryHelper inventoryHelper = new InventoryHelper(54, ChatUtilities.colored("&e&lZESTAWY SERWEROWE"));
        Inventory inventory = inventoryHelper.getInventory();


            Integer[] slotsBlackGlass = new Integer[]{0, 4, 8, 9, 17, 49, 45, 44, 53, 36};
            Integer[] slotsDarkBlueGlass = new Integer[]{3, 5,48,50};
            Integer[] slotsBlueGlass = new Integer[]{1, 2,6, 7, 47, 46, 51, 52};
            Integer[] slotsWhiteGlass = new Integer[]{18, 26, 27, 35};

        Arrays.stream(slotsBlackGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.BLACK_STAINED_GLASS_PANE, 1).setName(" ").toItemStack()));
        Arrays.stream(slotsDarkBlueGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.BLUE_STAINED_GLASS_PANE, 1 ).setName(" ").toItemStack()));
        Arrays.stream(slotsBlueGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1).setName(" ").toItemStack()));
        Arrays.stream(slotsWhiteGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.WHITE_STAINED_GLASS_PANE, 1).setName(" ").toItemStack()));


        this.kitService.getKits().forEach(kit -> {

            ItemHelper kitItem = new ItemHelper(Material.valueOf(kit.getInventoryMaterial().toUpperCase()))
                    .setName(kit.getInventoryName())
                    .addLore("")
                    .addLore("&fLewy &8- &fAby przejść dalej.");
            inventory.setItem(kit.getInventorySlot(), kitItem.toItemStack());
        });

        inventoryHelper.click(event -> {
            event.setCancelled(true);
            int inventorySlot = event.getSlot();
            this.kitService.findKitBySlot(inventorySlot).ifPresent(kit -> {
                this.show(player, kit);
            });
        });
        player.openInventory(inventory);
    }
    public void show(Player player, Kit kit) {
        InventoryHelper inventoryHelper = new InventoryHelper(54, ChatUtilities.colored(kit.getInventoryName()));
        Inventory inventory = inventoryHelper.getInventory();

        inventory.setContents(ItemSerializer.encodeItem(kit.getGlassItemSerialized()));
        for (ItemStack itemStack : ItemSerializer.encodeItem(kit.getItemSerialized())) {
            if (itemStack == null || itemStack.getType() == Material.AIR) continue;
            inventory.addItem(itemStack);
        }

        ItemHelper pickupItem = new ItemHelper(Material.GREEN_DYE)
                .setName("&aOdbierz zestaw")
                .addLore("")
                .addLore("&8>> &fDostep od rangi: " + kit.getPermissionAccess().getPrefix())
                .addLore("&8>> &fPosiadasz dostep: " + (!GroupType.hasPermission(this.userService.getOrCreate(player.getName()), kit.getPermissionAccess()) ? "&cNIE" : "&aTAK"))
                .addLore("&8>> &fTen zestaw mozesz odbierac co: &d" + DataUtilities.getTimeToString(System.currentTimeMillis() + TimeType.MINUTE.getTime(kit.getPickupDelay())))
                .addLore("")
                .addLore("     &b&lJAK ODEBRAC ZESTAW?")
                .addLore("&3Lewy &8- &fAby odebrac ten zestaw.")
                .addLore("");

        inventory.setItem(43, pickupItem.toItemStack());

        inventoryHelper.click(event -> {
            event.setCancelled(true);

            this.userService.findUserByNickName(player.getName()).ifPresent(user -> {
                if(event.getSlot() == 43){
                    if(!user.hasKitPickup(kit.getKitName())){
                        player.closeInventory();
                        player.sendTitle(ChatUtilities.colored("&b&lKIT"),
                                ChatUtilities.colored("&b&l♦ &fZestaw&8(&3" + kit.getKitName().toUpperCase() +
                                        "&8) &fmożesz odebrać za&8(&b" + DataUtilities.getTimeToString(user.getTimeDelayKit(kit.getKitName())) + "&8) &b&l♦"));
                        return;
                    }
                    player.closeInventory();
                    user.addKit(kit.getKitName(), System.currentTimeMillis() + TimeType.MINUTE.getTime(kit.getPickupDelay()));
                    player.sendTitle(ChatUtilities.colored("&b&lKIT"),
                            ChatUtilities.colored("&b&l♦ &fPomyślnie odebrałeś zestaw&8(&b" + kit.getKitName().toUpperCase() +
                                    " &7| &b" + DataUtilities.getTimeToString(user.getTimeDelayKit(kit.getKitName())) +"&8) &b&l♦"));

                    for (ItemStack itemStack : ItemSerializer.encodeItem(kit.getItemSerialized())) {
                        if (itemStack == null || itemStack.getType() == Material.AIR) continue;
                        player.getInventory().addItem(itemStack);
                    }

                }
            });

        });

        player.openInventory(inventory);
    }
}

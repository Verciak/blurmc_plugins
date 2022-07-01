package pl.pieszku.sectors.inventory;

import net.minecraft.server.v1_16_R3.EnumItemSlot;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.pieszku.api.service.UserService;
import org.pieszku.api.type.ArmorType;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.helper.InventoryHelper;
import pl.pieszku.sectors.helper.ItemHelper;
import pl.pieszku.sectors.packet.PacketEquipment;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.util.Arrays;

public class DiscoArmorInventories {


    private final ChatUtilities chatUtilities = new ChatUtilities();
    private final UserService userService = BukkitMain.getInstance().getUserService();

    public void open(Player player) {
        InventoryHelper inventoryHelper = new InventoryHelper(27, ChatUtilities.colored("&b&l© &3&lDiscoZbroja &b&l©"));
        Inventory inventory = inventoryHelper.getInventory();

        Integer[] glassPaneMagentaSlots = new Integer[]{1, 3, 5, 7, 9, 17, 18, 20, 22, 24};
        Integer[] glassPanePurpleSlots = new Integer[]{0, 2, 4, 6, 8, 19, 21, 23, 25, 26};
        Integer[] glassPaneBlackSlots = new Integer[]{10, 11, 15, 16};

        Arrays.stream(glassPaneMagentaSlots).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack()));
        Arrays.stream(glassPanePurpleSlots).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack()));
        Arrays.stream(glassPaneBlackSlots).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.BLACK_STAINED_GLASS_PANE).setName(" ").toItemStack()));


        ItemHelper discoArmorRandomItem = new ItemHelper(Material.GOLDEN_CHESTPLATE)
                .setName("&8* &6Random &8*")
                .addLore("")
                .addLore("&8>> &7Aby &awlaczyc &fDisco-Random &7kliknij");
        inventory.setItem(12, discoArmorRandomItem.toItemStack());

        ItemHelper discoArmorUltraItem = new ItemHelper(Material.DIAMOND_CHESTPLATE)
                .setName("&8* &9U&el&5t&fr&ea &8*")
                .addLore("")
                .addLore("&8>> &7Aby &awlaczyc &5Disco-Ultra &7kliknij");
        inventory.setItem(14, discoArmorUltraItem.toItemStack());

        ItemHelper offDiscoItem = new ItemHelper(Material.RED_DYE)
                .visibleFlag().addEnchant(Enchantment.ARROW_DAMAGE, 10)
                .setName("&cWylacz disco")
                .addLore("")
                .addLore("&8>> &7Aby &cwylaczyc &ddisco &fKliknij");

        inventory.setItem(13, offDiscoItem.toItemStack());

        player.openInventory(inventory);


        inventoryHelper.click(event -> {
            event.setCancelled(true);


            if (event.getInventory().getType() == InventoryType.PLAYER) return;

            this.userService.findUserByNickName(player.getName()).ifPresent(user -> {


                if (event.getSlot() == 13) {
                    user.setArmorType(ArmorType.CLEAR);
                    user.setLastArmorType(ArmorType.CLEAR);

                    for(Player playerOnline : Bukkit.getOnlinePlayers()) {
                        PacketEquipment.sendEquipment(playerOnline, player.getEntityId(), EnumItemSlot.FEET, new ItemStack(Material.AIR));
                        PacketEquipment.sendEquipment(playerOnline, player.getEntityId(), EnumItemSlot.LEGS, new ItemStack(Material.AIR));
                        PacketEquipment.sendEquipment(playerOnline, player.getEntityId(), EnumItemSlot.CHEST, new ItemStack(Material.AIR));
                        PacketEquipment.sendEquipment(playerOnline, player.getEntityId(), EnumItemSlot.HEAD, new ItemStack(Material.AIR));
                    }


                    player.closeInventory();
                    player.sendTitle(ChatUtilities.colored("&3&lDiscoZbroja"),
                           ChatUtilities.colored("&fPomyslnie &cwylaczono &eDiscoZbroje"));
                }
                if (event.getSlot() == 12) {
                    player.closeInventory();
                    user.setArmorType(ArmorType.RANDOM);
                    player.sendTitle(ChatUtilities.colored("&d&lDiscoZbroja"),
                           ChatUtilities.colored("&fPomyslnie &auruchomiono &fRandom"));
                }
                if (event.getSlot() == 14) {
                    player.closeInventory();
                    user.setArmorType(ArmorType.ULTRA);
                    player.sendTitle(ChatUtilities.colored("&3&lDiscoZbroja"),
                           ChatUtilities.colored("&fPomyslnie &auruchomiono &9U&el&5t&fr&ea"));
                }
            });
        });

    }
}

package pl.pieszku.sectors.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.pieszku.sectors.helper.InventoryHelper;
import pl.pieszku.sectors.helper.ItemHelper;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.util.Arrays;

public class EffectInventory {



    public void show(Player player) {
        InventoryHelper inventoryHelper = new InventoryHelper(27, ChatUtilities.colored("&6&lEFEKTY"));
        Inventory inventory = inventoryHelper.getInventory();

        Integer[] blackGlassSlots = new Integer[]{0, 8, 10, 11, 15,16, 18, 26};
        Integer[] blueGlassSlots = new Integer[]{2, 3, 4, 5, 6, 20, 21, 22, 23, 24};
        Integer[] lightBlueGlassSlots = new Integer[]{1, 7, 9, 17, 19, 25};

        Arrays.stream(blackGlassSlots).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.BLACK_STAINED_GLASS_PANE).setName(" ").toItemStack()));
        Arrays.stream(blueGlassSlots).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack()));
        Arrays.stream(lightBlueGlassSlots).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack()));


        ItemHelper minerEffectItem = new ItemHelper(Material.GOLDEN_PICKAXE)
                .setName("&6Efekty kopacza")
                .addLore("")
                .addLore("   &e&lOPIS:")
                .addLore("&6HASTE II &8- &f5 min")
                .addLore("&6SPEED II &8- &f3 min")
                .addLore("")
                .addLore("   &e&lCENA:")
                .addLore("&632 bloki emeraldów")
                .addLore("")
                .addLore("&6&lLEWY &8- &fAby dokonać zakupu");

        ItemHelper speedEffectItem = new ItemHelper(Material.FEATHER)
                .setName("&6Efekt biegacza")
                .addLore("")
                .addLore("   &e&lOPIS:")
                .addLore("&6SPEED II &8- &f3 min")
                .addLore("")
                .addLore("   &e&lCENA:")
                .addLore("&616 bloki emeraldów")
                .addLore("")
                .addLore("&6&lLEWY &8- &fAby dokonać zakupu");

        ItemHelper jumpBootsEffectItem = new ItemHelper(Material.GOLDEN_BOOTS)
                .setName("&6Efekt skakania")
                .addLore("")
                .addLore("   &e&lOPIS:")
                .addLore("&6JUMP BOOTS II &8- &f3 min")
                .addLore("")
                .addLore("   &e&lCENA:")
                .addLore("&632 bloki emeraldów")
                .addLore("")
                .addLore("&6&lLEWY &8- &fAby dokonać zakupu");

        inventory.setItem(12, minerEffectItem.toItemStack());
        inventory.setItem(13, speedEffectItem.toItemStack());
        inventory.setItem(14, jumpBootsEffectItem.toItemStack());

        player.openInventory(inventory);

        inventoryHelper.click(event -> {
            event.setCancelled(true);

            switch (event.getSlot()) {
                case 12: {

                    if (!player.getInventory().containsAtLeast(new ItemStack(Material.EMERALD_BLOCK), 32)) {
                        player.closeInventory();
                        player.sendTitle(ChatUtilities.colored("&6&lEFEKTY"), ChatUtilities.colored("&fNie stać cię na ten efekt"));
                        return;
                    }
                    player.getInventory().removeItem(new ItemStack(Material.EMERALD_BLOCK, 32));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 5500, 1));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 3500, 1));
                    break;
                }
                case 13: {

                    if (!player.getInventory().containsAtLeast(new ItemStack(Material.EMERALD_BLOCK), 16)) {
                        player.closeInventory();
                        player.sendTitle(ChatUtilities.colored("&6&lEFEKTY"), ChatUtilities.colored("&fNie stać cię na ten efekt"));
                        return;
                    }
                    player.getInventory().removeItem(new ItemStack(Material.EMERALD_BLOCK, 16));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 3500, 1));
                    break;
                }
                case 14: {

                    if (!player.getInventory().containsAtLeast(new ItemStack(Material.EMERALD_BLOCK), 16)) {
                        player.closeInventory();
                        player.sendTitle(ChatUtilities.colored("&6&lEFEKTY"), ChatUtilities.colored("&fNie stać cię na ten efekt"));
                        return;
                    }
                    player.getInventory().removeItem(new ItemStack(Material.EMERALD_BLOCK, 16));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 3500, 1));
                    break;
                }
            }

        });

    }
}

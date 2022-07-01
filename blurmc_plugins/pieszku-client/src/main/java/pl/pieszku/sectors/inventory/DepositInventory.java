package pl.pieszku.sectors.inventory;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.pieszku.api.objects.user.User;
import org.pieszku.api.service.UserService;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.helper.InventoryHelper;
import pl.pieszku.sectors.helper.ItemHelper;
import pl.pieszku.sectors.utilities.ChatUtilities;
import pl.pieszku.sectors.utilities.ItemUtilities;

import java.util.Arrays;

public class DepositInventory {

    private final UserService userService = BukkitMain.getInstance().getUserService();

    public void show(Player player) {
        InventoryHelper inventoryHelper = new InventoryHelper(54, ChatUtilities.colored("&b&lDEPOZYT"));
        Inventory inventory = inventoryHelper.getInventory();


        Integer[] slotsBlackGlass = new Integer[]{10, 16, 28, 34, 48, 51};
        Integer[] slotsDarkBlueGlass = new Integer[]{3, 5, 11, 13, 15, 19, 21, 23, 25, 29, 31, 33, 49};
        Integer[] slotsBlueGlass = new Integer[]{2, 6, 18, 26, 27, 35, 37, 43, 47, 50};
        Integer[] slotsWhiteGlass = new Integer[]{0, 1, 7, 8, 9, 17, 41, 39, 36, 44, 45, 46, 52, 53};

        Arrays.stream(slotsBlackGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.BLACK_STAINED_GLASS_PANE, 1).setName(" ").toItemStack()));
        Arrays.stream(slotsDarkBlueGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.BLUE_STAINED_GLASS_PANE, 1 ).setName(" ").toItemStack()));
        Arrays.stream(slotsBlueGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1).setName(" ").toItemStack()));
        Arrays.stream(slotsWhiteGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.WHITE_STAINED_GLASS_PANE, 1).setName(" ").toItemStack()));


        this.userService.findUserByNickName(player.getName()).ifPresent(user -> {

            ItemHelper sortingItem = new ItemHelper(Material.BOOK)
                    .setName(" ")
                    .addLore("              &e&lSORTOWANIE")
                    .addLore("      &7Tutaj mozesz ustawic sortowanie")
                    .addLore("&fSortowanie &6twoich &fprzedmiotow po wyplaceniu")
                    .addLore("&fJesli bedziesz chcial ustawic &esortowanie &fprzedmiotow")
                    .addLore("&fnalezy przejsc dalej zobacz ponizej jak to zrobic?")
                    .addLore("")
                    .addLore("           &b&lJAK USTAWIC SOROTWANIE?")
                    .addLore("       &3&lLEWY &8&l- &fAby przejsc dalej")
                    .addLore("&3&lLEWY + SHIFT &8&l- &fAby ustawic domyslne ustawienia")
                    .addLore("");
            inventory.setItem(4, sortingItem.toItemStack());


            ItemHelper depositItem = new ItemHelper(Material.ENCHANTED_GOLDEN_APPLE)
                    .setName(" ")
                    .addLore("         &e&lZAKLETE ZLOTE JABLKO")
                    .addLore("       &7Tutaj mozesz wyplacic do limitu")
                    .addLore("    &7Wczesniej zostaly tobie zabrane tutaj tkzw &6KOXY")
                    .addLore("  &7natomiast tutaj mozesz wyplacac ten przedmiot do limitu &eserwerowego")
                    .addLore("")
                    .addLore("          &6&lLIMITY SERWEROWE")
                    .addLore("      &7Limit serwerowy&8(&e1 sztuki&8)")
                    .addLore("   &7Posiadasz lacznie juz&8(&e" + user.getDepositCount("goldenAppleEnchantment") + "sztuk&8)")
                    .addLore("")
                    .addLore("          &6&lJAK TO ZROBIC?")
                    .addLore("      &e&lLEWY &8&l- &fAby wyplacic do limitu")
                    .addLore(" &e&lSHIFT + LEWY &8&l- &fAby wyplacic wszystko");
            inventory.setItem(12, depositItem.toItemStack());


            depositItem = new ItemHelper(Material.GOLDEN_APPLE)
                    .setName(" ")
                    .addLore("         &e&lZLOTE JABLKO")
                    .addLore("       &7Tutaj mozesz wyplacic do limitu")
                    .addLore("    &7Wczesniej zostaly tobie zabrane tutaj tkzw &eREFY")
                    .addLore("  &7natomiast tutaj mozesz wyplacac ten przedmiot do limitu &eserwerowego")
                    .addLore("")
                    .addLore("          &6&lLIMITY SERWEROWE")
                    .addLore("      &7Limit serwerowy&8(&e12 sztuki&8)")
                    .addLore("   &7Posiadasz lacznie juz&8(&e" + user.getDepositCount("goldenApple") + "sztuk&8)")
                    .addLore("")
                    .addLore("          &6&lJAK TO ZROBIC?")
                    .addLore("      &e&lLEWY &8&l- &fAby wyplacic do limitu")
                    .addLore(" &e&lSHIFT + LEWY &8&l- &fAby wyplacic wszystko");
            ;
            inventory.setItem(14, depositItem.toItemStack());


            depositItem = new ItemHelper(Material.SNOWBALL)
                    .setName(" ")
                    .addLore("            &f&lSNIEZKI")
                    .addLore("       &7Tutaj mozesz wyplacic do limitu")
                    .addLore("     &7Wczesniej zostaly tobie zabrane tutaj")
                    .addLore(" &7natomiast tutaj mozesz wyplacac ten przedmiot do limitu &fserwerowego")
                    .addLore("")
                    .addLore("          &3&lLIMITY SERWEROWE")
                    .addLore("      &7Limit serwerowy&8(&b16 sztuki&8)")
                    .addLore("   &7Posiadasz lacznie juz&8(&b" + user.getDepositCount("snowball") + "sztuk&8)")
                    .addLore("")
                    .addLore("          &3&lJAK TO ZROBIC?")
                    .addLore("      &b&lLEWY &8&l- &fAby wyplacic do limitu")
                    .addLore(" &b&lSHIFT + LEWY &8&l- &fAby wyplacic wszystko")
            ;
            inventory.setItem(20, depositItem.toItemStack());

            depositItem = new ItemHelper(Material.ENDER_PEARL)
                    .setName(" ")
                    .addLore("         &d&lPERLY ENDERMANA")
                    .addLore("       &7Tutaj mozesz wyplacic do limitu")
                    .addLore("     &7Wczesniej zostaly tobie zabrane tutaj tzkw &dPERELKI")
                    .addLore(" &7natomiast tutaj mozesz wyplacac ten przedmiot do limitu &fserwerowego")
                    .addLore("")
                    .addLore("          &5&lLIMITY SERWEROWE")
                    .addLore("      &7Limit serwerowy&8(&d3 sztuki&8)")
                    .addLore("   &7Posiadasz lacznie juz&8(&d" + user.getDepositCount("enderPearl") + "sztuk&8)")
                    .addLore("")
                    .addLore("          &5&lJAK TO ZROBIC?")
                    .addLore("      &d&lLEWY &8&l- &fAby wyplacic do limitu")
                    .addLore(" &d&lSHIFT + LEWY &8&l- &fAby wyplacic wszystko")
            ;
            inventory.setItem(22, depositItem.toItemStack());

            depositItem = new ItemHelper(Material.FISHING_ROD)
                    .setName(" ")
                    .addLore("           &f&lWEDKI")
                    .addLore("      &7Tutaj mozesz wyplacic do limitu")
                    .addLore("   &7Wczesniej zostaly tobie zabrane tutaj")
                    .addLore(" &7natomiast tutaj mozesz wyplacac ten przedmiot do limitu &fserwerowego")
                    .addLore("")
                    .addLore("          &f&lLIMITY SERWEROWE")
                    .addLore("      &7Limit serwerowy&8(&b1 sztuki&8)")
                    .addLore("   &7Posiadasz lacznie juz&8(&b" + user.getDepositCount("fishing") + "sztuk&8)")
                    .addLore("")
                    .addLore("          &f&lJAK TO ZROBIC?")
                    .addLore("    &b&lLEWY &8&l- &fAby wyplacic do limitu")
            ;
            inventory.setItem(24, depositItem.toItemStack());

            depositItem = new ItemHelper(Material.TNT)
                    .visibleFlag().addEnchant(Enchantment.ARROW_DAMAGE, 10)
                    .setName(" ")
                    .addLore("         &c&lRZUCANE TNT")
                    .addLore("       &7Tutaj mozesz wyplacic do limitu")
                    .addLore("   &7Wczesniej zostaly tobie zabrane tutaj tzkw &cRZUCAKI")
                    .addLore(" &7natomiast tutaj mozesz wyplacac ten przedmiot do limitu &fserwerowego")
                    .addLore("")
                    .addLore("          &4&lLIMITY SERWEROWE")
                    .addLore("      &7Limit serwerowy&8(&c2 sztuki&8)")
                    .addLore("   &7Posiadasz lacznie juz&8(&c" + user.getDepositCount("tnt") + "sztuk&8)")
                    .addLore("")
                    .addLore("          &4&lJAK TO ZROBIC?")
                    .addLore("      &c&lLEWY &8&l- &fAby wyplacic do limitu")
                    .addLore(" &c&lSHIFT + LEWY &8&l- &fAby wyplacic wszystko")
            ;
            inventory.setItem(30, depositItem.toItemStack());


            depositItem = new ItemHelper(Material.GOLDEN_BOOTS)
                    .visibleFlag().addEnchant(Enchantment.ARROW_DAMAGE, 10)
                    .setName(" ")
                    .addLore("          &9&lANTY-NOGI")
                    .addLore("       &7Tutaj mozesz wyplacic do limitu")
                    .addLore("   &7Wczesniej zostaly tobie zabrane tutaj tzkw &9AntyNozki")
                    .addLore(" &7natomiast tutaj mozesz wyplacac ten przedmiot do limitu &fserwerowego")
                    .addLore("")
                    .addLore("          &9&lLIMITY SERWEROWE")
                    .addLore("      &7Limit serwerowy&8(&33 sztuki&8)")
                    .addLore("   &7Posiadasz lacznie juz&8(&3" + user.getDepositCount("antiBoots") + "sztuk&8)")
                    .addLore("")
                    .addLore("          &9&lJAK TO ZROBIC?")
                    .addLore("      &3&lLEWY &8&l- &fAby wyplacic do limitu")
                    .addLore(" &3&lSHIFT + LEWY &8&l- &fAby wyplacic wszystko")
            ;
            inventory.setItem(32, depositItem.toItemStack());


            ItemHelper depositAllItem = new ItemHelper(Material.HOPPER)
                    .visibleFlag().addEnchant(Enchantment.ARROW_DAMAGE, 10)
                    .setName(" ")
                    .addLore("      &a&lWYPLAC DO LIMITU")
                    .addLore("&7Dzieki tej opcji mozesz wyplacic do limitu")
                    .addLore("&7po kliknieciu tutaj wszystkie &alimity &7serwerowe")
                    .addLore("&7zostana odpowiednio &awysorotwane &7oraz dodane do twojego ekwipunku")
                    .addLore("")
                    .addLore("      &a&lJAK WYPLACIC DO LIMITU?")
                    .addLore(" &2&lLEWY &8&l- &fAby wyplacic do limitu")
                    .addLore("");
            inventory.setItem(40, depositAllItem.toItemStack());

            ItemHelper sortingEnableItem = new ItemHelper(Material.GREEN_DYE)
                    .setName(" ")
                    .addLore("     &a&lWLACZ SORTOWANIE DEPOZYTU")
                    .addLore("  &7Twoje &austawienia &7zostana odpowiednio")
                    .addLore("&7wysortowane nastepnie &asystem &7ustawi ci te przedmioty")
                    .addLore("")
                    .addLore("     &a&lJAK TO ZROBIC?")
                    .addLore("&2&lLEWY &8&l- &fAby ustawic sortowanie")
                    .addLore("");

            ItemHelper sortingDisableItem = new ItemHelper(Material.RED_DYE)
                    .setName(" ")
                    .addLore("     &4&lWYLACZ SORTOWANIE DEPOZYTU")
                    .addLore("  &7Twoje &custawienia &7zostana dopasowane")
                    .addLore("&7wedlug &adomyslnego &7ustawienia sortowania serwera")
                    .addLore("")
                    .addLore("     &4&lJAK TO ZROBIC?")
                    .addLore("&c&lLEWY &8&l- &fAby wylaczyc sortowanie")
                    .addLore("");

            inventory.setItem(38, sortingEnableItem.toItemStack());
            inventory.setItem(42, sortingDisableItem.toItemStack());


            inventoryHelper.click(event -> {
                event.setCancelled(true);

                switch (event.getSlot()) {
                    case 12: {
                        depositSort(player, user, new ItemHelper(Material.ENCHANTED_GOLDEN_APPLE), 1, "goldenAppleEnchantment");
                        this.show(player);
                        break;
                    }
                    case 14:{
                        depositSort(player, user, new ItemHelper(Material.GOLDEN_APPLE, (short) 0), 12, "goldenApple");
                        this.show(player);
                        break;
                    }
                    case 20:{
                        depositSort(player, user, new ItemHelper(Material.SNOWBALL), 16, "snowball");
                        this.show(player);
                        break;
                    }
                    case 22:{
                        depositSort(player, user, new ItemHelper(Material.ENDER_PEARL), 3, "enderPearl");
                        this.show(player);
                        break;
                    }
                    case 24:{
                        depositSort(player, user, new ItemHelper(Material.FISHING_ROD), 1, "fishing");
                        this.show(player);
                        break;
                    }
                    case 40:{
                        depositSort(player, user, new ItemHelper(Material.ENCHANTED_GOLDEN_APPLE), 1, "goldenAppleEnchantment");
                        depositSort(player, user, new ItemHelper(Material.GOLDEN_APPLE), 12, "goldenApple");
                        depositSort(player, user, new ItemHelper(Material.SNOWBALL), 16, "snowball");
                        depositSort(player, user, new ItemHelper(Material.ENDER_PEARL), 3, "enderPearl");
                        depositSort(player, user, new ItemHelper(Material.FISHING_ROD), 1, "fishing");
                        this.show(player);
                        break;
                    }
                }

            });
        });
        player.openInventory(inventory);
    }
    public void depositSort(Player player, User user, ItemHelper itemHelper, int limit, String patchMap) {
        int amount = ItemUtilities.getCountItemInInventory(player, itemHelper);
        if(user.getDepositCount(patchMap) <= 0)return;
        if(amount >= limit)return;
        amount = limit - amount;
        ItemUtilities.addItem(player, itemHelper.setAmount(amount));
        user.removeDepositItem(patchMap, amount);
    }
}

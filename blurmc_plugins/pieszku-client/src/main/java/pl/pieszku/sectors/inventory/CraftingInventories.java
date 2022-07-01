package pl.pieszku.sectors.inventory;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.pieszku.sectors.helper.InventoryHelper;
import pl.pieszku.sectors.helper.ItemHelper;
import pl.pieszku.sectors.utilities.ChatUtilities;
import pl.pieszku.sectors.utilities.ItemUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CraftingInventories {


    public void openTnt(Player player){
        InventoryHelper inventoryHelper = new InventoryHelper(54, ChatUtilities.colored("&3&lCRAFTINGI" + "&8(&b6&7/&77&8)"));
        Inventory inventory = inventoryHelper.getInventory();
        this.setInventoryContent(inventory, inventoryHelper);

        ItemHelper tnt = new ItemHelper(Material.TNT, 64);
        ItemHelper kox = new ItemHelper(Material.GOLDEN_APPLE);

        ItemHelper tntItem = new ItemHelper(Material.TNT, 1)
                .visibleFlag().addEnchant(Enchantment.ARROW_DAMAGE, 10)
                .setName("&4&lRZUCANE TNT")
                .setLore(Stream.of("",
                        "&7Opis: &fKliknij prawym",
                        "&fa wyrzucisz odpalone tnt",
                        "&adziala &ftylko na terenie gildii").collect(Collectors.toList()));

        inventory.setItem(4, tntItem.toItemStack());


        ItemHelper autoCrafting = new ItemHelper(Material.CRAFTING_TABLE)
                .visibleFlag().addEnchant(Enchantment.ARROW_DAMAGE, 10)
                .setName("&3AutoCrafting")
                .addLore("")
                .addLore("&8Na serwerze poslugujemy sie tylko ta opcja")
                .addLore("&8>> &fZdobadz pokazane przedmioty nastepnie")
                .addLore("&8>> &fwroc tutaj i &7kliknij &fa wykraftujemy ci ta recepture")
                .addLore("");

        inventory.setItem(19, autoCrafting.toItemStack());
        inventory.setItem(25, autoCrafting.toItemStack());
        inventory.setItem(12, tnt.toItemStack());
        inventory.setItem(13, tnt.toItemStack());
        inventory.setItem(14, tnt.toItemStack());
        inventory.setItem(21, tnt.toItemStack());
        inventory.setItem(22, kox.toItemStack());
        inventory.setItem(23, tnt.toItemStack());
        inventory.setItem(30, tnt.toItemStack());
        inventory.setItem(31, tnt.toItemStack());
        inventory.setItem(32, tnt.toItemStack());

        player.openInventory(inventory);
    }

    public void openBoyFarmer(Player player){
        InventoryHelper inventoryHelper = new InventoryHelper(54, ChatUtilities.colored("&3&lCRAFTINGI" + "&8(&b2&7/&37&8)"));
        Inventory inventory = inventoryHelper.getInventory();
        this.setInventoryContent(inventory, inventoryHelper);

        ItemHelper obsidian = new ItemHelper(Material.OBSIDIAN);
        ItemHelper kox = new ItemHelper(Material.GOLDEN_APPLE);

        ItemHelper boyFarmerItem = new ItemHelper(Material.END_PORTAL_FRAME, 4)
                .setName("&b&lBoyFarmer")
                .setLore(Stream.of("",
                        "&7Opis: &fPostaw ten przedmiot na ziemi",
                        "&fa zacznie sie generowac obsydian",
                        "&fpamietaj podloga musi byc pusta",
                        "&adziala &ftylko na terenie gildii").collect(Collectors.toList()));

        inventory.setItem(4, boyFarmerItem.toItemStack());


        ItemHelper autoCrafting = new ItemHelper(Material.CRAFTING_TABLE)
                .visibleFlag().addEnchant(Enchantment.ARROW_DAMAGE, 10)
                .setName("&3AutoCrafting")
                .addLore("")
                .addLore("&8Na serwerze poslugujemy sie tylko ta opcja")
                .addLore("&8>> &fZdobadz pokazane przedmioty nastepnie")
                .addLore("&8>> &fwroc tutaj i &7kliknij &fa wykraftujemy ci ta recepture")
                .addLore("");

        inventory.setItem(19, autoCrafting.toItemStack());
        inventory.setItem(25, autoCrafting.toItemStack());
        inventory.setItem(12, obsidian.toItemStack());
        inventory.setItem(13, obsidian.toItemStack());
        inventory.setItem(14, obsidian.toItemStack());
        inventory.setItem(21, obsidian.toItemStack());
        inventory.setItem(22, kox.toItemStack());
        inventory.setItem(23, obsidian.toItemStack());
        inventory.setItem(30, obsidian.toItemStack());
        inventory.setItem(31, obsidian.toItemStack());
        inventory.setItem(32, obsidian.toItemStack());

        player.openInventory(inventory);

    }
    public void openEnderchest(Player player){
        InventoryHelper inventoryHelper = new InventoryHelper(54, ChatUtilities.colored("&3&lCRAFTINGI" + "&8(&b3&7/&37&8)"));
        Inventory inventory = inventoryHelper.getInventory();
        this.setInventoryContent(inventory, inventoryHelper);
        ItemHelper obsidian = new ItemHelper(Material.OBSIDIAN);
        ItemHelper pearl = new ItemHelper(Material.ENDER_PEARL);

        ItemHelper enderChestItem = new ItemHelper(Material.ENDER_CHEST, 1);

        inventory.setItem(4, enderChestItem.toItemStack());

        ItemHelper autoCrafting = new ItemHelper(Material.CRAFTING_TABLE)
                .visibleFlag().addEnchant(Enchantment.ARROW_DAMAGE, 10)
                .setName("&3AutoCrafting")
                .addLore("")
                .addLore("&8Na serwerze poslugujemy sie tylko ta opcja")
                .addLore("&8>> &fZdobadz pokazane przedmioty nastepnie")
                .addLore("&8>> &fwroc tutaj i &7kliknij &fa wykraftujemy ci ta recepture")
                .addLore("");

        inventory.setItem(19, autoCrafting.toItemStack());
        inventory.setItem(25, autoCrafting.toItemStack());
        inventory.setItem(12, obsidian.toItemStack());
        inventory.setItem(13, obsidian.toItemStack());
        inventory.setItem(14, obsidian.toItemStack());
        inventory.setItem(21, obsidian.toItemStack());
        inventory.setItem(22, pearl.toItemStack());
        inventory.setItem(23, obsidian.toItemStack());
        inventory.setItem(30, obsidian.toItemStack());
        inventory.setItem(31, obsidian.toItemStack());
        inventory.setItem(32, obsidian.toItemStack());
        player.openInventory(inventory);

    }
    public void openWorker(Player player){
        InventoryHelper inventoryHelper = new InventoryHelper(54, ChatUtilities.colored("&3&lCRAFTINGI" + "&8(&b4&7/&37&8)"));
        Inventory inventory = inventoryHelper.getInventory();
        this.setInventoryContent(inventory, inventoryHelper);

        ItemHelper cobblestone = new ItemHelper(Material.COBBLESTONE);
        ItemHelper bucket = new ItemHelper(Material.PISTON);
        ItemHelper kox = new ItemHelper(Material.GOLDEN_APPLE);

        ItemHelper workerGuildItem = new ItemHelper(Material.STONE, 4)
                .setName("&f&lKopaczFosy")
                .setLore(Stream.of("",
                        "&7Opis: &fPostaw ten przedmiot na ziemi",
                        "&fa podloga pod wybranym miejscem zacznie znikac",
                        "&adziala &ftylko na terenie gildii").collect(Collectors.toList()));

        inventory.setItem(4, workerGuildItem.toItemStack());


        ItemHelper autoCrafting = new ItemHelper(Material.CRAFTING_TABLE)
                .visibleFlag().addEnchant(Enchantment.ARROW_DAMAGE, 10)
                .setName("&3AutoCrafting")
                .addLore("")
                .addLore("&8Na serwerze poslugujemy sie tylko ta opcja")
                .addLore("&8>> &fZdobadz pokazane przedmioty nastepnie")
                .addLore("&8>> &fwroc tutaj i &7kliknij &fa wykraftujemy ci ta recepture")
                .addLore("");

        inventory.setItem(19, autoCrafting.toItemStack());
        inventory.setItem(25, autoCrafting.toItemStack());
        inventory.setItem(12, cobblestone.toItemStack());
        inventory.setItem(13, bucket.toItemStack());
        inventory.setItem(14, cobblestone.toItemStack());
        inventory.setItem(21, cobblestone.toItemStack());
        inventory.setItem(22, kox.toItemStack());
        inventory.setItem(23, cobblestone.toItemStack());
        inventory.setItem(30, cobblestone.toItemStack());
        inventory.setItem(31, cobblestone.toItemStack());
        inventory.setItem(32, cobblestone.toItemStack());

        player.openInventory(inventory);

    }
    public void openSandFarmer(Player player){
        InventoryHelper inventoryHelper = new InventoryHelper(54, ChatUtilities.colored("&3&lCRAFTINGI" + "&8(&b5&7/&37&8)"));

        Inventory inventory = inventoryHelper.getInventory();
        this.setInventoryContent(inventory, inventoryHelper);

        ItemHelper workerGuildItem = new ItemHelper(Material.SANDSTONE, 4)
                .setName("&e&lSandFarmer")
                .setLore(Stream.of("",
                        "&7Opis: &fPostaw ten przedmiot na ziemi",
                        "&fa zacznie sie generowac piasek",
                        "&fpamietaj podloga musi byc pusta",
                        "&adziala &ftylko na terenie gildii").collect(Collectors.toList()));

        inventory.setItem(4, workerGuildItem.toItemStack());

        ItemHelper sand = new ItemHelper(Material.SAND);
        ItemHelper kox = new ItemHelper(Material.GOLDEN_APPLE);



        ItemHelper autoCrafting = new ItemHelper(Material.CRAFTING_TABLE)
                .visibleFlag().addEnchant(Enchantment.ARROW_DAMAGE, 10)
                .setName("&3AutoCrafting")
                .addLore("")
                .addLore("&8Na serwerze poslugujemy sie tylko ta opcja")
                .addLore("&8>> &fZdobadz pokazane przedmioty nastepnie")
                .addLore("&8>> &fwroc tutaj i &7kliknij &fa wykraftujemy ci ta recepture")
                .addLore("");

        inventory.setItem(19, autoCrafting.toItemStack());
        inventory.setItem(25, autoCrafting.toItemStack());
        inventory.setItem(12, sand.toItemStack());
        inventory.setItem(13, sand.toItemStack());
        inventory.setItem(14, sand.toItemStack());
        inventory.setItem(21, sand.toItemStack());
        inventory.setItem(22, kox.toItemStack());
        inventory.setItem(23, sand.toItemStack());
        inventory.setItem(30, sand.toItemStack());
        inventory.setItem(31, sand.toItemStack());
        inventory.setItem(32, sand.toItemStack());

        player.openInventory(inventory);
    }

    public void setInventoryContent(Inventory inventory, InventoryHelper inventoryHelper) {
        Integer[] slotsBlack = new Integer[]{0, 1, 7, 8, 29, 33, 37, 39, 40, 41, 43, 45, 46, 52, 53};
        Integer[] slotsMagma = new Integer[]{2, 3, 5, 6, 10, 16, 18, 26, 27, 28, 35, 36};
        Integer[] slotsPurple = new Integer[]{9, 11, 15, 17, 20, 24, 36, 44, 34};

        for (Integer integer : slotsBlack) {
            inventory.setItem(integer, new ItemHelper(Material.BLACK_STAINED_GLASS_PANE).setName(" ").toItemStack());
        }
        for (Integer integer : slotsMagma) {
            inventory.setItem(integer, new ItemHelper(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack());
        }
        for (Integer integer : slotsPurple) {
            inventory.setItem(integer, new ItemHelper(Material.BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack());

        }

        inventory.setItem(42, new ItemHelper(Material.WHITE_STAINED_GLASS_PANE)
                .setName(" ").toItemStack());

        inventory.setItem(38, new ItemHelper(Material.WHITE_STAINED_GLASS_PANE)
                .setName(" ").toItemStack());


        ItemHelper stoneWorkItem = new ItemHelper(Material.END_STONE, 4)
                .setName("&e&lStowniarka")
                .setLore(Stream.of("&7Opis: &fPostaw ten przedmiot na ziemi",
                        "&fa zacznie sie generowac stone na dwie kratki do gory",
                        "&fpamietaj ze 2 kratki nad stowniarka musza byc puste",
                        "&aregeneruje &fsie co 2 sek").collect(Collectors.toList()));

        inventory.setItem(47, stoneWorkItem.toItemStack());

        ItemHelper enderChestItem = new ItemHelper(Material.ENDER_CHEST, 1);
        inventory.setItem(49, enderChestItem.toItemStack());

        ItemHelper boyFarmerItem = new ItemHelper(Material.END_PORTAL_FRAME, 4)
                .setName("&b&lBoyFarmer")
                .setLore(Stream.of("",
                        "&7Opis: &fPostaw ten przedmiot na ziemi",
                        "&fa zacznie sie generowac obsydian",
                        "&fpamietaj podloga musi byc pusta",
                        "&adziala &ftylko na terenie gildii").collect(Collectors.toList()));

        inventory.setItem(48, boyFarmerItem.toItemStack());


        ItemHelper workerGuildFosItem = new ItemHelper(Material.STONE, 4)
                .setName("&f&lKopaczFosy")
                .setLore(Stream.of("",
                        "&7Opis: &fPostaw ten przedmiot na ziemi",
                        "&fa podloga pod wybranym miejscem zacznie znikac",
                        "&adziala &ftylko na terenie gildii").collect(Collectors.toList()));

        inventory.setItem(50, workerGuildFosItem.toItemStack());

        ItemHelper workerGuildItem = new ItemHelper(Material.SANDSTONE, 4)
                .setName("&e&lSandFarmer")
                .setLore(Stream.of("",
                        "&7Opis: &fPostaw ten przedmiot na ziemi",
                        "&fa zacznie sie generowac piasek",
                        "&fpamietaj podloga musi byc pusta",
                        "&adziala &ftylko na terenie gildii").collect(Collectors.toList()));

        inventory.setItem(51, workerGuildItem.toItemStack());


        ItemHelper tntItem = new ItemHelper(Material.TNT, 1)
                .visibleFlag().addEnchant(Enchantment.ARROW_DAMAGE, 10)
                .setName("&4&lRZUCANE TNT")
                .setLore(Stream.of("",
                        "&7Opis: &fKliknij prawym",
                        "&fa wyrzucisz odpalone tnt",
                        "&adziala &ftylko na terenie gildii").collect(Collectors.toList()));

        inventory.setItem(46, tntItem.toItemStack());


        inventoryHelper.click(event -> {

            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();


            if(event.getSlot() == 46){
                this.openTnt(player);
                return;
            }

            if (event.getSlot() == 47) {
                this.showStonework(player);
                return;
            }
            if (event.getSlot() == 48) {
                this.openBoyFarmer(player);
                return;
            }
            if (event.getSlot() == 49) {
                this.openEnderchest(player);
                return;
            }
            if (event.getSlot() == 50) {
                this.openWorker(player);
                return;
            }
            if (event.getSlot() == 51) {
                this.openSandFarmer(player);
                return;
            }


            if (event.getSlot() == 19 || event.getSlot() == 25) {
                ItemStack craftingOneItem = inventory.getItem(12);
                ItemStack craftingTwoItem = inventory.getItem(13);
                ItemStack craftingThreeItem = inventory.getItem(14);
                ItemStack craftingFourItem = inventory.getItem(21);
                ItemStack craftingFiveItem = inventory.getItem(22);
                ItemStack craftingSixItem = inventory.getItem(23);
                ItemStack craftingSevenItem = inventory.getItem(30);
                ItemStack craftingEightItem = inventory.getItem(31);
                ItemStack craftingNineItem = inventory.getItem(32);

                List<ItemStack> itemStackList = new ArrayList<>();
                itemStackList.add(craftingOneItem);
                itemStackList.add(craftingTwoItem);
                itemStackList.add(craftingThreeItem);
                itemStackList.add(craftingFourItem);
                itemStackList.add(craftingFiveItem);
                itemStackList.add(craftingSixItem);
                itemStackList.add(craftingSevenItem);
                itemStackList.add(craftingEightItem);
                itemStackList.add(craftingNineItem);

                if(!ItemUtilities.hasItems(player, itemStackList)){
                    player.closeInventory();
                    player.sendMessage(ChatUtilities.colored("&5&lCRAFTINGI&8: &fBlad nie posiadasz potrzebnych przedmiotow!"));
                    return;
                }
                player.closeInventory();
                player.getInventory().addItem(inventory.getItem(4));
                itemStackList.forEach(itemStack -> {
                    player.getInventory().removeItem(itemStack);
                });
            }
        });
    }

    public void showStonework(Player player) {
        InventoryHelper inventoryHelper = new InventoryHelper(54, ChatUtilities.colored("&3&lCRAFTINGI" + "&8(&b1&7/&37&8)"));
        Inventory inventory = inventoryHelper.getInventory();

        this.setInventoryContent(inventory, inventoryHelper);

        ItemHelper stoneWorkItem = new ItemHelper(Material.END_STONE, 4)
                .setName("&e&lStowniarka")
                .setLore(Stream.of("&7Opis: &fPostaw ten przedmiot na ziemi",
                        "&fa zacznie sie generowac stone na dwie kratki do gory",
                        "&fpamietaj ze 2 kratki nad stowniarka musza byc puste",
                        "&aregeneruje &fsie co 2 sek").collect(Collectors.toList()));

        inventory.setItem(4, stoneWorkItem.toItemStack());

        ItemHelper stone = new ItemHelper(Material.STONE);
        ItemHelper redstone = new ItemHelper(Material.REDSTONE);



        ItemHelper autoCrafting = new ItemHelper(Material.CRAFTING_TABLE)
                .visibleFlag().addEnchant(Enchantment.ARROW_DAMAGE, 10)
                .setName("&3AutoCrafting")
                .addLore("")
                .addLore("&8Na serwerze poslugujemy sie tylko ta opcja")
                .addLore("&8>> &fZdobadz pokazane przedmioty nastepnie")
                .addLore("&8>> &fwroc tutaj i &7kliknij &fa wykraftujemy ci ta recepture")
                .addLore("");

        inventory.setItem(19, autoCrafting.toItemStack());
        inventory.setItem(25, autoCrafting.toItemStack());
        inventory.setItem(12, stone.toItemStack());
        inventory.setItem(13, stone.toItemStack());
        inventory.setItem(14, stone.toItemStack());
        inventory.setItem(21, stone.toItemStack());
        inventory.setItem(22, redstone.toItemStack());
        inventory.setItem(23, stone.toItemStack());
        inventory.setItem(30, stone.toItemStack());
        inventory.setItem(31, stone.toItemStack());
        inventory.setItem(32, stone.toItemStack());


        player.openInventory(inventoryHelper.getInventory());
    }
}

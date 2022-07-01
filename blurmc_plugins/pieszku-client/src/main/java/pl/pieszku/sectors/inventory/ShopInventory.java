package pl.pieszku.sectors.inventory;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.pieszku.api.data.ConfigurationData;
import org.pieszku.api.data.shop.Shop;
import org.pieszku.api.service.UserService;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.helper.InventoryHelper;
import pl.pieszku.sectors.helper.ItemHelper;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ShopInventory {

    private final ConfigurationData configurationData = BukkitMain.getInstance().getConfigurationData();
    private final UserService userService = BukkitMain.getInstance().getUserService();

    public void show(Player player){
        InventoryHelper inventoryHelper = new InventoryHelper(27, ChatUtilities.colored("&a&lSKLEP SERWEROWY"));
        Inventory inventory = inventoryHelper.getInventory();


        ItemHelper blueGlassItem = new ItemHelper(Material.BLUE_STAINED_GLASS_PANE).setName(" ");
        ItemHelper blackGlassItem = new ItemHelper(Material.BLACK_STAINED_GLASS_PANE).setName(" ");
        ItemHelper whiteGlassItem = new ItemHelper(Material.WHITE_STAINED_GLASS_PANE).setName(" ");
        ItemHelper whiteBlueGlassItem = new ItemHelper(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ");


        ItemHelper buyShopItem = new ItemHelper(Material.LIME_DYE)
                .setName("&a&lKUPNO")
                .addLore("")
                .addLore("&a&lLEWY &8- &fAby przejsc dalej");

        inventory.setItem(10, buyShopItem.toItemStack());

        ItemHelper buyEmeraldShopItem = new ItemHelper(Material.EMERALD_BLOCK)
                .setName("&a&lKUPNO ZA EMERALDY")
                .addLore("")
                .addLore("&a&lLEWY &8- &fAby przejsc dalej");

        inventory.setItem(12,buyEmeraldShopItem.toItemStack());

        this.userService.findUserByNickName(player.getName()).ifPresent(user -> {
            ItemHelper infoYourCoinsItem = new ItemHelper(Material.CLOCK)
                    .setName("&7Posiadasz: &e" + user.getDepositCount("coins") + " &fmonet");

            inventory.setItem(13, infoYourCoinsItem.toItemStack());
        });

        ItemHelper villageShopItem = new ItemHelper(Material.VILLAGER_SPAWN_EGG)
                .setName("&6&lPrzenosny villager")
                .addLore("")
                .addLore("&e&lLEWY &8- &fAby przejsc dalej");

        inventory.setItem(14, villageShopItem.toItemStack());

        ItemHelper sellShopItem = new ItemHelper(Material.RED_DYE)
                .setName("&c&lSPRZEDAZ")
                .addLore("")
                .addLore("&4&lLEWY &8- &fAby przejsc dalej");

        inventory.setItem(16, sellShopItem.toItemStack());

        inventory.setItem(0, blueGlassItem.toItemStack());
        inventory.setItem(3, blueGlassItem.toItemStack());
        inventory.setItem(5, blueGlassItem.toItemStack());
        inventory.setItem(8, blueGlassItem.toItemStack());
        inventory.setItem(18, blueGlassItem.toItemStack());
        inventory.setItem(21, blueGlassItem.toItemStack());
        inventory.setItem(23, blueGlassItem.toItemStack());
        inventory.setItem(26, blueGlassItem.toItemStack());

        inventory.setItem(4, whiteGlassItem.toItemStack());
        inventory.setItem(9, whiteGlassItem.toItemStack());
        inventory.setItem(11, whiteGlassItem.toItemStack());
        inventory.setItem(15, whiteGlassItem.toItemStack());
        inventory.setItem(17, whiteGlassItem.toItemStack());
        inventory.setItem(22, whiteGlassItem.toItemStack());

        inventory.setItem(1, whiteBlueGlassItem.toItemStack());
        inventory.setItem(7, whiteBlueGlassItem.toItemStack());
        inventory.setItem(19, whiteBlueGlassItem.toItemStack());
        inventory.setItem(25, whiteBlueGlassItem.toItemStack());

        inventory.setItem(2, blackGlassItem.toItemStack());
        inventory.setItem(6, blackGlassItem.toItemStack());
        inventory.setItem(20, blackGlassItem.toItemStack());
        inventory.setItem(24, blackGlassItem.toItemStack());

        player.openInventory(inventory);

        inventoryHelper.click(event -> {
            event.setCancelled(true);


            switch (event.getSlot()){
                case 10:{
                    this.showShopBuy(player);
                    break;
                }
                case 14:{
                    this.showShopVillager(player);
                    break;
                }
            }

        });
    }

    public void showShopVillager(Player player) {
        InventoryHelper inventoryHelper = new InventoryHelper(54, ChatUtilities.colored("&8&l* &f&lPRZENOSNY VILLAGER &8&l*"));
        Inventory inventory = inventoryHelper.getInventory();

        Integer[] slotsBlackGlass = new Integer[]{3, 5, 18, 26, 27, 35, 48, 50};
        Integer[] slotsDarkBlueGlass = new Integer[]{2, 6, 10, 12, 14, 16, 37, 39, 41, 43, 47, 51};
        Integer[] slotsBlueGlass = new Integer[]{11, 15, 19, 4, 25, 28, 34, 38, 42};
        Integer[] slotsWhiteGlass = new Integer[]{0, 1, 7, 8, 9, 17, 40, 36, 33, 32, 30, 29, 44, 45, 46, 52, 53};

        Arrays.stream(slotsBlackGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.BLACK_STAINED_GLASS_PANE, 1).setName(" ").toItemStack()));
        Arrays.stream(slotsDarkBlueGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.BLUE_STAINED_GLASS_PANE, 1).setName(" ").toItemStack()));
        Arrays.stream(slotsBlueGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1).setName(" ").toItemStack()));
        Arrays.stream(slotsWhiteGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.WHITE_STAINED_GLASS_PANE, 1).setName(" ").toItemStack()));


        ItemHelper tntItem = new ItemHelper(Material.TNT_MINECART)
                .setName("&4&lITEMKI DO PRZEBITY")
                .addLore("")
                .addLore("&c&lLEWY &8- &fAby przejsc dalej");

        inventory.setItem(13, tntItem.toItemStack());

        ItemHelper designItem = new ItemHelper(Material.BLUE_TERRACOTTA)
                .setName("&a&lITEMKI DO DESIGNU")
                .addLore("")
                .addLore("&2&lLEWY &8- &fAby przejsc dalej");

        inventory.setItem(20, designItem.toItemStack());


        ItemHelper enchantmentItem = new ItemHelper(Material.ENCHANTING_TABLE)
                .setName("&d&lENCHANTY")
                .addLore("")
                .addLore("&5&lLEWY &8- &fAby przejsc dalej");

        inventory.setItem(21, enchantmentItem.toItemStack());

        ItemHelper generateItem = new ItemHelper(Material.END_STONE)
                .setName("&a&lGENERATORY")
                .addLore("")
                .addLore("&2&lLEWY &8- &fAby przejsc dalej");

        inventory.setItem(22, generateItem.toItemStack());


        ItemHelper fightItem = new ItemHelper(Material.DIAMOND_SWORD)
                .setName("&b&lITEMKI DO KLEPY")
                .addLore("")
                .addLore("&3&lLEWY &8- &fAby przejsc dalej");

        inventory.setItem(23, fightItem.toItemStack());

        ItemHelper blocksItem = new ItemHelper(Material.OBSIDIAN)
                .setName("&f&lROZNE BLOKI")
                .addLore("")
                .addLore("&7&lLEWY &8- &fAby przejsc dalej");

        inventory.setItem(24, blocksItem.toItemStack());


        ItemHelper pickaxeItem = new ItemHelper(Material.DIAMOND_PICKAXE)
                .setName("&a&lNARZEDZIA")
                .addLore("")
                .addLore("&2&lLEWY &8- &fAby przejsc dalej");

        inventory.setItem(31, pickaxeItem.toItemStack());

        ItemHelper backItem = new ItemHelper(Material.DARK_OAK_FENCE_GATE)
                .setName("&cPowrot");
        inventory.setItem(49, backItem.toItemStack());

        inventoryHelper.click(event -> {
            event.setCancelled(true);


            switch (event.getSlot()){
                case 49:{
                    this.show(player);
                    break;
                }
                case 31:{
                    this.showVillager(player, this.configurationData.getShopVillagerJson().pickaxeShop, "&a&lNARZEDZIA");
                    break;
                }
                case 24:{
                    this.showVillager(player, this.configurationData.getShopVillagerJson().blocksShop, "&f&lROZNE BLOKI");
                    break;
                }
                case 23:{
                    this.showVillager(player, this.configurationData.getShopVillagerJson().fightShop, "&e&lITEMKI DO KLEPY");
                    break;
                }
                case 22:{
                    this.showVillager(player, this.configurationData.getShopVillagerJson().generatorShop, "&6&lGENERATORY");
                    break;
                }
                case 21:{
                    this.showVillager(player, this.configurationData.getShopVillagerJson().enchantmentShop, "&d&lENCHANTY");
                    break;
                }
                case 20:{
                    this.showVillager(player, this.configurationData.getShopVillagerJson().designShop, "&a&lITEMKI DO DESIGNU");
                    break;
                }
                case 13:{
                    this.showVillager(player, this.configurationData.getShopVillagerJson().tntShop, "&4&lITEMKI DO PRZEBITY");
                    break;
                }
            }

        });

        player.openInventory(inventory);
    }
    public void showVillager(Player player, Shop[] shopArray, String inventoryName){
        InventoryHelper inventoryHelper = new InventoryHelper(54, ChatUtilities.colored(inventoryName));
        Inventory inventory = inventoryHelper.getInventory();


        Integer[] slotsBlackGlass = new Integer[]{18, 26, 27, 35};
        Integer[] slotsDarkBlueGlass = new Integer[]{4, 48, 47, 50, 51};
        Integer[] slotsBlueGlass = new Integer[]{0, 2, 3, 5, 6, 8, 10, 16, 37, 43, 45, 53};
        Integer[] slotsWhiteGlass = new Integer[]{1, 7, 9, 17, 36, 44, 46, 52};

        Arrays.stream(slotsBlackGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.BLACK_STAINED_GLASS_PANE, 1).setName(" ").toItemStack()));
        Arrays.stream(slotsDarkBlueGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.BLUE_STAINED_GLASS_PANE, 1 ).setName(" ").toItemStack()));
        Arrays.stream(slotsBlueGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1).setName(" ").toItemStack()));
        Arrays.stream(slotsWhiteGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.WHITE_STAINED_GLASS_PANE, 1).setName(" ").toItemStack()));

        ItemHelper backItem = new ItemHelper(Material.DARK_OAK_FENCE_GATE).setName("&cPowrot");
        inventory.setItem(49, backItem.toItemStack());

        this.userService.findUserByNickName(player.getName()).ifPresent(user -> {
            for(Shop shop : shopArray) {
                ItemHelper shopItem = new ItemHelper(Material.valueOf(shop.getMaterial().toUpperCase()), shop.getMaterialAmount())
                        .setName(shop.getInventoryName());

                if(!Arrays.stream(shop.getInventoryLore()).collect(Collectors.toList()).contains("null")){
                    shopItem.setLore(Arrays.stream(shop.getInventoryLore()).map(s -> {
                        s = s.replace("{COINS}", String.valueOf(shop.getCount()));
                        s = s.replace("{AMOUNT}", String.valueOf(shop.getMaterialAmount()));
                        s = s.replace("{SHOP_INFO}", (user.getDepositCount("coins") >= shop.getCount() ? "&aKliknij aby zakupic ten przedmiot" : "&cNie stac cie na ten przedmiot"));
                        return s;
                    }).collect(Collectors.toList()));
                }
                for(String enchants : shop.getMaterialEnchants()){
                    if(enchants.contains("null"))continue;
                    String[] split = enchants.split(":");
                    String enchantmentName = split[0];
                    int power = Integer.parseInt(split[1]);
                    shopItem.addEnchant(Enchantment.getByName(enchantmentName), power);
                }
                inventory.setItem(shop.getInventorySlot(), shopItem.toItemStack());

            }
            player.openInventory(inventory);

            inventoryHelper.click(event -> {
                event.setCancelled(true);

                switch (event.getSlot()) {
                    case 49: {
                        this.showShopVillager(player);
                    }
                }

                Arrays.stream(shopArray).filter(shopFind -> shopFind.getInventorySlot() == event.getSlot()).findFirst().ifPresent(shop -> {
                    if(!player.getInventory().containsAtLeast(new ItemStack(Material.EMERALD_BLOCK), shop.getCount())){
                        player.closeInventory();
                        player.sendTitle(ChatUtilities.colored("&a&lSKLEP"), ChatUtilities.colored("&fNie posiadasz tyle blokow emeraldowych!"));
                        return;
                    }
                    ItemHelper shopItem = new ItemHelper(Material.valueOf(shop.getMaterial().toUpperCase()), shop.getMaterialAmount());

                    if(!shop.getMaterialName().equals("null")) {
                        shopItem.setName(shop.getMaterialName());
                    }

                    if(!Arrays.stream(shop.getMaterialLore()).collect(Collectors.toList()).contains("null")){
                        shopItem.setLore(shop.getMaterialLore());
                    }
                    for(String enchants : shop.getMaterialEnchants()){
                        if(enchants.contains("null"))continue;
                        String[] split = enchants.split(":");
                        String enchantmentName = split[0];
                        int power = Integer.parseInt(split[1]);
                        shopItem.addEnchant(Enchantment.getByName(enchantmentName), power);
                    }
                    player.getInventory().removeItem(new ItemStack(Material.EMERALD_BLOCK, shop.getCount()));
                    player.getInventory().addItem(shopItem.toItemStack());
                });
            });
        });
    }

    public void showShopBuy(Player player){
        InventoryHelper inventoryHelper = new InventoryHelper(54, ChatUtilities.colored("&a&lKUPNO ZA MONETY"));
        Inventory inventory = inventoryHelper.getInventory();


        Integer[] slotsBlackGlass = new Integer[]{18, 26, 27, 35};
        Integer[] slotsDarkBlueGlass = new Integer[]{4, 48, 47, 50, 51};
        Integer[] slotsBlueGlass = new Integer[]{0, 2, 3, 5, 6, 8, 10, 16, 37, 43, 45, 53};
        Integer[] slotsWhiteGlass = new Integer[]{1, 7, 9, 17, 36, 44, 46, 52};

        Arrays.stream(slotsBlackGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.BLACK_STAINED_GLASS_PANE, 1).setName(" ").toItemStack()));
        Arrays.stream(slotsDarkBlueGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.BLUE_STAINED_GLASS_PANE, 1 ).setName(" ").toItemStack()));
        Arrays.stream(slotsBlueGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1).setName(" ").toItemStack()));
        Arrays.stream(slotsWhiteGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.WHITE_STAINED_GLASS_PANE, 1).setName(" ").toItemStack()));

        ItemHelper backItem = new ItemHelper(Material.DARK_OAK_FENCE_GATE).setName("&cPowrot");
        inventory.setItem(49, backItem.toItemStack());

        this.userService.findUserByNickName(player.getName()).ifPresent(user -> {
        for(Shop shop : this.configurationData.getShopBuyJson().getShops()) {
            ItemHelper shopItem = new ItemHelper(Material.valueOf(shop.getMaterial().toUpperCase()))
                    .setName(shop.getInventoryName());

            if(!Arrays.stream(shop.getInventoryLore()).collect(Collectors.toList()).contains("null")){
                shopItem.setLore(Arrays.stream(shop.getInventoryLore()).map(s -> {
                    s = s.replace("{COINS}", String.valueOf(shop.getCount()));
                    s = s.replace("{AMOUNT}", String.valueOf(shop.getMaterialAmount()));
                    s = s.replace("{SHOP_INFO}", (user.getDepositCount("coins") >= shop.getCount() ? "&aKliknij aby zakupic ten przedmiot" : "&cNie stac cie na ten przedmiot"));
                    return s;
                }).collect(Collectors.toList()));
            }
            for(String enchants : shop.getMaterialEnchants()){
                if(enchants.contains("null"))continue;
                String[] split = enchants.split(":");
                String enchantmentName = split[0];
                int power = Integer.parseInt(split[1]);
                shopItem.addEnchant(Enchantment.getByName(enchantmentName), power);
            }
            inventory.setItem(shop.getInventorySlot(), shopItem.toItemStack());

        }
        player.openInventory(inventory);

        inventoryHelper.click(event -> {
            event.setCancelled(true);

            switch (event.getSlot()) {
                case 49: {
                    this.show(player);
                }
            }
            this.configurationData.shopBuyJson.findShopByInventorySlot(event.getSlot()).ifPresent(shop -> {


                if(user.getDepositCount("coins") < shop.getCount()){
                    player.closeInventory();
                    player.sendTitle(ChatUtilities.colored("&a&lSKLEP"), ChatUtilities.colored("&fNiestety nie stać cię na ten przedmiot."));
                    return;
                }
                ItemHelper shopItem = new ItemHelper(Material.valueOf(shop.getMaterial().toUpperCase()), shop.getMaterialAmount());

                if(!shop.getMaterialName().equals("null")) {
                    shopItem.setName(shop.getMaterialName());
                }

                if(!Arrays.stream(shop.getMaterialLore()).collect(Collectors.toList()).contains("null")){
                    shopItem.setLore(shop.getMaterialLore());
                }
                for(String enchants : shop.getMaterialEnchants()){
                    if(enchants.contains("null"))continue;
                    String[] split = enchants.split(":");
                    String enchantmentName = split[0];
                    int power = Integer.parseInt(split[1]);
                    shopItem.addEnchant(Enchantment.getByName(enchantmentName), power);
                }
                user.removeDepositItem("coins", shop.getCount());
                player.getInventory().addItem(shopItem.toItemStack());
            });
        });
        });
    }
}

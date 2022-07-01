package pl.pieszku.sectors.inventory;


import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.pieszku.api.data.ConfigurationData;
import org.pieszku.api.data.drop.Drop;
import org.pieszku.api.objects.user.User;
import org.pieszku.api.service.UserService;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.helper.InventoryHelper;
import pl.pieszku.sectors.helper.ItemHelper;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DropInventory {

    private ConfigurationData configurationData;
    private final UserService userService = BukkitMain.getInstance().getUserService();

    public void init(){
        this.configurationData = BukkitMain.getInstance().getConfigurationData();
    }


    public void show(Player player) {
        InventoryHelper inventoryHelper = new InventoryHelper(27, ChatUtilities.colored("&f&l♦ &b&lDROPY &f&l♦"));
        Inventory inventory = inventoryHelper.getInventory();
        Integer[] slotsBlackGlass = new Integer[]{3, 4, 5, 11, 15, 21, 23};
        Integer[] slotsDarkBlueGlass = new Integer[]{1, 7, 9, 17, 19, 25};
        Integer[] slotsBlueGlass = new Integer[]{2, 6, 10, 16, 20, 24};
        Integer[] slotsWhiteGlass = new Integer[]{0, 8, 18, 26};

        Arrays.stream(slotsBlackGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.BLACK_STAINED_GLASS_PANE, 1).setName(" ").toItemStack()));
        Arrays.stream(slotsDarkBlueGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.BLUE_STAINED_GLASS_PANE, 1).setName(" ").toItemStack()));
        Arrays.stream(slotsBlueGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1).setName(" ").toItemStack()));
        Arrays.stream(slotsWhiteGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.WHITE_STAINED_GLASS_PANE, 1).setName(" ").toItemStack()));


        ItemHelper stoneDropItem = new ItemHelper(Material.STONE)
                .setName("&f&l♦ &7&lDROP Z KAMIENIA &f&l♦")
                .addLore("")
                .addLore("&7&lLEWY &8&l- &fAby przejsc dalej");


        inventory.setItem(12, stoneDropItem.toItemStack());

        ItemHelper chestDropItem = new ItemHelper(Material.ENDER_CHEST)
                .visibleFlag().addEnchant(Enchantment.ARROW_DAMAGE, 10)
                .setName("&f&l♦ &d&lDROP Z MAGICZNEJ SKRZYNKI &f&l♦")
                .addLore("")
                .addLore("&5&lLEWY &8&l- &fAby przejsc dalej");

        inventory.setItem(13, chestDropItem.toItemStack());

        ItemHelper cobblexDropItem = new ItemHelper(Material.MOSSY_COBBLESTONE)
                .visibleFlag().addEnchant(Enchantment.ARROW_DAMAGE, 10)
                .setName("&f&l♦ &7&lDROP Z COBBLEXA &f&l♦")
                .addLore("")
                .addLore("&7&lLEWY &8&l- &fAby przejsc dalej");

        inventory.setItem(14, cobblexDropItem.toItemStack());

        ItemHelper topsItem = new ItemHelper(Material.NETHERITE_PICKAXE)
                .setName("&f&l♦ &b&lTOPKA WYKOPANEGO KAMIENIA &f&l♦")
                .addLore("");

        List<User> users = new ArrayList<>(this.userService.getUserMap().values());
        synchronized (Collections.unmodifiableList(users)){
            users.sort((o1, o2) -> {
                Integer stoneOne = o1.getDepositCount("stone");
                Integer stoneTwo = o2.getDepositCount("stone");
                return stoneTwo.compareTo(stoneOne);
            });
        }

        IntStream.rangeClosed(1, 15).forEach(i -> {
            if(users.size() >= i){
                User userCompare = users.get(i - 1);
                topsItem.addLore("&f" + i + ".&b" + userCompare.getNickName() + " &3" + userCompare.getDepositCount("stone") + "szt &ewykopanego kamienia");
            }else{
                topsItem.addLore("&f" + i + ".&bBrak danych.");
            }
        });
        inventory.setItem(22, topsItem.toItemStack());

        inventoryHelper.click(event -> {
            event.setCancelled(true);
            switch (event.getSlot()){
                case 12:{
                    this.showDrop(player);
                    break;
                }
                case 13:{
                    this.showCase(player);
                    break;
                }
            }
        });

        player.openInventory(inventory);

    }

    private void showCase(Player player) {
        InventoryHelper inventoryHelper = new InventoryHelper(54, ChatUtilities.colored("&f&l♦ &d&lDROP Z MAGICZNEJ SKRZYNI &f&l♦"));
        Inventory inventory = inventoryHelper.getInventory();


        Integer[] slotsBlackGlass = new Integer[]{0, 4, 8, 9, 17, 49, 45, 44, 53, 36};
        Integer[] slotsDarkBlueGlass = new Integer[]{3, 5,48,50};
        Integer[] slotsBlueGlass = new Integer[]{1, 2,6, 7, 47, 46, 51, 52};
        Integer[] slotsWhiteGlass = new Integer[]{18, 26, 27, 35};

        Arrays.stream(slotsBlackGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.BLACK_STAINED_GLASS_PANE, 1).setName(" ").toItemStack()));
        Arrays.stream(slotsDarkBlueGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.BLUE_STAINED_GLASS_PANE, 1 ).setName(" ").toItemStack()));
        Arrays.stream(slotsBlueGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1).setName(" ").toItemStack()));
        Arrays.stream(slotsWhiteGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.WHITE_STAINED_GLASS_PANE, 1).setName(" ").toItemStack()));


        ItemHelper barrierItem = new ItemHelper(Material.BARRIER)
                .setName("&c&lPOWROT");
        inventory.setItem(49, barrierItem.toItemStack());

        for (Drop drop : this.configurationData.getCaseDropJson().getDrops()) {

            ItemHelper dropItem = new ItemHelper(Material.valueOf(drop.getMaterial().toUpperCase()),  drop.getMaterialId())
                    .setName(drop.getInventoryName())
                    .setLore(Arrays.stream(drop.getInventoryLore())
                            .map(s -> {
                                s = s.replace("{ID}", String.valueOf(drop.getId()));
                                s = s.replace("{CHANCE}", String.valueOf(drop.getChance()));
                                return s;
                            }).collect(Collectors.toList()));


            for(String enchant : drop.getMaterialEnchants()){
                if(enchant.contains("null"))continue;
                String[] split = enchant.split(":");
                String enchantmentName = split[0];
                int power = Integer.parseInt(split[1]);
                dropItem.addEnchant(Enchantment.getByName(enchantmentName.toUpperCase()), power);
            }
            inventory.setItem(drop.getInventorySlot(), dropItem.toItemStack());
        }

        inventoryHelper.click(event -> {
            event.setCancelled(true);

            if(event.getSlot() == 49){
                this.show(player);
            }
        });
        player.openInventory(inventory);
    }

    public void showDrop(Player player) {
        InventoryHelper inventoryHelper = new InventoryHelper(54, ChatUtilities.colored("&f&l♦ &7&lDROP Z KAMIENIA &f&l♦"));
        Inventory inventory = inventoryHelper.getInventory();


        Integer[] slotsBlackGlass = new Integer[]{2, 6, 10, 16, 18, 26, 37, 42, 38, 40};
        Integer[] slotsDarkBlueGlass = new Integer[]{41, 39, 34, 28};
        Integer[] slotsBlueGlass = new Integer[]{3, 5, 11, 15, 19, 25, 27, 35};
        Integer[] slotsWhiteGlass = new Integer[]{0, 1, 7, 8, 9, 17, 44, 45, 46, 36, 52, 53, 29, 33};

        Arrays.stream(slotsBlackGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.BLACK_STAINED_GLASS_PANE, 1).setName(" ").toItemStack()));
        Arrays.stream(slotsDarkBlueGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.BLUE_STAINED_GLASS_PANE, 1 ).setName(" ").toItemStack()));
        Arrays.stream(slotsBlueGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1).setName(" ").toItemStack()));
        Arrays.stream(slotsWhiteGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.WHITE_STAINED_GLASS_PANE, 1).setName(" ").toItemStack()));


        ItemHelper barrierItem = new ItemHelper(Material.BARRIER)
                .setName("&c&lPOWROT");
        inventory.setItem(49, barrierItem.toItemStack());

        ItemHelper allOnItem = new ItemHelper(Material.GREEN_DYE).setName("&a&lWLACZ WSZYSTKIE DROPY");
        ItemHelper allOffItem = new ItemHelper(Material.RED_DYE).setName("&c&lWYLACZ WSZYSTKIE DROPY");

        inventory.setItem(47, allOnItem.toItemStack());
        inventory.setItem(48, allOffItem.toItemStack());


        ItemHelper cobblexItem = new ItemHelper(Material.MOSSY_COBBLESTONE)
                .visibleFlag().addEnchant(Enchantment.ARROW_DAMAGE, 10)
                .setName(" ")
                .addLore("    &3&lDROP Z COBBLEX")
                .addLore("&b&lLEWY &8&l- &fAby przejsc dalej");

        inventory.setItem(37, cobblexItem.toItemStack());
        ItemHelper breakStoneItem = new ItemHelper(Material.GOLDEN_PICKAXE)
                .setName(" ")
                .addLore("    &6&lTWOJ WYKOPANY KAMIEN");

        inventory.setItem(50, breakStoneItem.toItemStack());

        ItemHelper infoItem = new ItemHelper(Material.BOOK)
                .setName(" ")
                .addLore("    &d&lTURBODROP")
                .addLore("");

        inventory.setItem(51, infoItem.toItemStack());

        this.userService.findUserByNickName(player.getName()).ifPresent(user -> {


            for (Drop drop : this.configurationData.getStoneDropJson().getDrops()) {
                ItemHelper dropItem = new ItemHelper(Material.valueOf(drop.getMaterial().toUpperCase()), 1, drop.getMaterialId())
                        .setName(drop.getInventoryName())
                        .setLore(Arrays.stream(drop.getInventoryLore())
                                .map(s -> {
                                    s = s.replace("{STATUS}", (user.hasDisable(drop.getId()) ? "&c&lNIE&8&l(&f&lNIE DROPI&8&l)" : "&a&lTAK&8&l(&f&lDROPI&8&l)"));
                                    s = s.replace("{STATUS_MESSAGE}", (user.hasDisableMessage(drop.getId()) ? "&c&lNIE&8&l(&f&lNIE WYSYLA&8&l)" : "&a&lTAK&8&l(&f&lWYSYLA&8&l)"));
                                    s = s.replace("{CHANCE}", String.valueOf(drop.getChance()));
                                    return s;
                                }).collect(Collectors.toList()));

                if(!user.hasDisable(drop.getId())){
                    dropItem.visibleFlag().addEnchant(Enchantment.ARROW_DAMAGE, 10);
                }

                inventory.setItem(drop.getInventorySlot(), dropItem.toItemStack());
            }

            inventoryHelper.click(event -> {
                event.setCancelled(true);



                switch (event.getSlot()){
                    case 47:{
                        user.enableALLDrop();
                        this.showDrop(player);
                        break;
                    }
                    case 48:{
                        user.disableALLDrop(this.configurationData.getStoneDropJson().drops);
                        this.showDrop(player);
                        break;
                    }
                    case 49:{
                        this.show(player);
                        break;
                    }
                }

                Drop drop = this.configurationData.getStoneDropJson().findDropByInventorySlot(event.getSlot());
                if(drop == null) return;

                switch (event.getClick()){
                    case LEFT:{
                        if(user.hasDisable(drop.getId())) {
                            user.enableDrop(drop.getId());
                            this.showDrop(player);
                            return;
                        }
                        user.disableDrop(drop.getId());
                        this.showDrop(player);
                        break;
                    }
                    case RIGHT:{
                        if(user.hasDisableMessage(drop.getId())) {
                            user.enableDropMessage(drop.getId());
                            this.showDrop(player);
                            return;
                        }
                        user.disableDropMessage(drop.getId());
                        this.showDrop(player);
                        break;
                    }
                }

            });
        });

        player.openInventory(inventory);
    }
}

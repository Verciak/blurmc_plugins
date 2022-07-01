package pl.pieszku.sectors.inventory.guild;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.pieszku.api.data.guild.GuildPermissionJson;
import org.pieszku.api.data.guild.PermissionGuildData;
import org.pieszku.api.objects.guild.GuildPermission;
import org.pieszku.api.objects.guild.GuildPermissionType;
import org.pieszku.api.objects.guild.impl.Guild;
import org.pieszku.api.objects.user.User;
import org.pieszku.api.service.GuildService;
import org.pieszku.api.service.UserService;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.helper.InventoryHelper;
import pl.pieszku.sectors.helper.ItemHelper;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GuildPermissionInventory {


    private final GuildService guildService = BukkitMain.getInstance().getGuildService();
    private final GuildPermissionJson guildPermissionJson = BukkitMain.getInstance().getConfigurationData().getGuildPermissionJson();
    private final UserService userService = BukkitMain.getInstance().getUserService();

    public void show(Player player) {
        InventoryHelper inventoryHelper = new InventoryHelper(54, ChatUtilities.colored("&3&lSchematy uprawnień"));
        Inventory inventory = inventoryHelper.getInventory();


        Integer[] glassBlueSlots = new Integer[]{0, 2, 6, 8, 18, 26, 36, 44, 45, 47, 51, 53};
        Integer[] glassDarkBlueSlots = new Integer[]{1, 3, 5, 7, 9, 17, 27, 35, 46, 48, 50, 52};

        Arrays.stream(glassBlueSlots).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack()));
        Arrays.stream(glassDarkBlueSlots).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack()));

        inventory.setItem(49, new ItemHelper(Material.DARK_OAK_FENCE_GATE).setName("&cWyjdz").toItemStack());


        ItemHelper informationItem = new ItemHelper(Material.PLAYER_HEAD, 1, (short) 3)
                .setOwnerUrl("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDNhZmY1NzcyNGY1ZWQwNGRiNDUwZGI4NzRjNmMwYzZlZjRhMTBlMTQwNmI3N2ZiYWZmZGNiOTgxOTFiZjIwNCJ9fX0=")
                .setName("&bSchematy uprawnien")
                .setLore(Arrays.asList(
                        "",
                        "    &8Troszke uprawnien nie zaszkodzi  ",
                        "",
                        "&8>> &fWitaj &b" + player.getName(),
                        "&8>> &fDzieki tej fukncji mozesz",
                        "&8>> &fzarzadzac &bschematami uprawnien",
                        "&8>> &fzabierac&8/&fdodawac czlonka&8/&fuprawnienie",
                        "",
                        "&8>> &fNajezdzajac na dany przedmiot ponizej",
                        "&8>> &fwyswietli ci sie permisja wybierz ktora",
                        "&8>> &fz nich bedziesz chcial skonfigurowac",
                        ""));

        inventory.setItem(4, informationItem.toItemStack());

        List<String> lore = Arrays.asList(
                "",
                "    &8Troszke uprawnien nie zaszkodzi  ",
                "",
                "&bLewy &8- &fAby przejsc dalej",
                "");

        ItemHelper itemOwner = new ItemHelper(Material.PLAYER_HEAD, 1, (short) 3)
                .setOwnerUrl("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzU5ZThlMDQ2ZDM1NjllNDE3NzRmOWM3NTQ0OGY5YzJkODdmM2FlYTY3OTA0MGM0YWViY2ZmOGRjMzQ1NjJlYiJ9fX0=")
                .setName("&fUprawnienia: &3Zalozyciela")
                .setLore(lore);

        ItemHelper itemLeader = new ItemHelper(Material.PLAYER_HEAD, 1, (short) 3)
                .setOwnerUrl("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzE2YjA1YThlNTBmYWEzYWU5NzA4ZGU2ZjgzZDJmNWJiYzlhZjgyOTQ2ZDU0NTBkYTI2NmY3ZDVlODU1MzA3NSJ9fX0=")
                .setName("&fUprawnienia: &bZastepcy")
                .setLore(lore);

        ItemHelper itemMaster = new ItemHelper(Material.PLAYER_HEAD, 1, (short) 3)
                .setOwnerUrl("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2I4M2Q4YzZiZmUyM2ViMTRjODQ1YmQxNjU1OGFiMzRlZmZlZWYzY2Q5NDAwMjhkN2VkYWU1YjliZmYyMDFhZSJ9fX0=")
                .setName("&fUprawnienia: &aMistrza")
                .setLore(lore);

        ItemHelper itemDefault = new ItemHelper(Material.PLAYER_HEAD, 1, (short) 3)
                .setOwnerUrl("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTY2MWY4YTc3MTUwM2M3Y2U4OGMwM2ZkZTRiMDg0OTY5YzIwYjk0Njk1OTc1N2U3MmFjOGIzMzNjMDRhMGQ4ZSJ9fX0=")
                .setName("&fUprawnienia: &aDomyslne")
                .setLore(lore);

        inventory.setItem(13, itemOwner.toItemStack());
        inventory.setItem(29, itemLeader.toItemStack());
        inventory.setItem(31, itemMaster.toItemStack());
        inventory.setItem(33, itemDefault.toItemStack());


        inventoryHelper.click(event -> {
            event.setCancelled(true);

            switch (event.getSlot()){
                case 13:{
                    this.openPermission(player, "&fUprawnienia: &3Zalozyciela", "Założyciel", GuildPermissionType.OWNER);
                    break;
                }
            }
        });


        player.openInventory(inventory);
    }

    public void openPermission(Player player, String invName, String permissionPolishName, GuildPermissionType permissionGuildType) {
        InventoryHelper inventoryHelper = new InventoryHelper(54, ChatUtilities.colored(invName));
        Inventory inventory = inventoryHelper.getInventory();



        Integer[] glassBlueSlots = new Integer[]{0, 2, 6, 8, 18, 26, 36, 44, 45, 47, 51, 53};
        Integer[] glassDarkBlueSlots = new Integer[]{1, 3, 5, 7, 9, 17, 27, 35, 46, 48, 50, 52};

        Arrays.stream(glassBlueSlots).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack()));
        Arrays.stream(glassDarkBlueSlots).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack()));



        this.guildService.findGuildByMember(player.getName()).ifPresent(guild -> {

            if (!guild.hasOwner(player.getName())) return;


            List<String> lore = Arrays.asList(
                    "",
                    "    &8Nadawanie uprawnien  ",
                    "",
                    "&bLewy &8- &fAby przejsc dalej",
                    "");

            ItemHelper itemDefault = new ItemHelper(Material.PLAYER_HEAD, 1, (short) 3)
                    .setOwnerUrl("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTY2MWY4YTc3MTUwM2M3Y2U4OGMwM2ZkZTRiMDg0OTY5YzIwYjk0Njk1OTc1N2U3MmFjOGIzMzNjMDRhMGQ4ZSJ9fX0=")
                    .setName("&fUprawnienia: &b" + permissionPolishName)
                    .setLore(lore);

            inventory.setItem(4, itemDefault.toItemStack());
            inventory.setItem(49, itemDefault.toItemStack());

            GuildPermission guildPermission = guild.getPermissionGuildByType(permissionGuildType);
            if (guildPermission == null) return;
            for (PermissionGuildData permissionConfig : this.guildPermissionJson.getPermissionData()) {
                ItemHelper permissionItem = new ItemHelper(Material.valueOf(permissionConfig.getMaterial().toUpperCase()), permissionConfig.getMaterialAmount())
                        .setName(permissionConfig.getInventoryName())
                        .setLore(Arrays.stream(permissionConfig.getInventoryLore()).map(regex -> {
                            regex = regex.replace("{STATUS}", (!guildPermission.hasPermissionDisable(permissionConfig.getId()) ? "&aTAK" : "&cNIE"));
                            regex = regex.replace("{ID}", String.valueOf(permissionConfig.getId()));
                            return regex;
                        }).collect(Collectors.toList()));

                if (!guildPermission.hasPermissionDisable(permissionConfig.getId())) {
                    permissionItem.visibleFlag().addEnchant(Enchantment.ARROW_DAMAGE, 10);
                }

                inventory.setItem(permissionConfig.getInventorySlot(), permissionItem.toItemStack());
            }


            player.openInventory(inventory);

            inventoryHelper.click(event -> {
                event.setCancelled(true);

                if (event.getSlot() == 4 || event.getSlot() == 49) {
                    this.openAddPermission(player, guild, guildPermission);
                    return;
                }

                PermissionGuildData permissionGuild = this.guildPermissionJson.findPermissionByInventorySlot(event.getSlot());
                if (permissionGuild == null) return;

                if (!guildPermission.hasPermissionDisable(permissionGuild.getId())) {
                    guildPermission.disablePermission(guild, permissionGuild.getId());
                    this.openPermission(player, invName, permissionPolishName, permissionGuildType);
                    return;
                }
                guildPermission.enablePermission(guild, permissionGuild.getId());
                this.openPermission(player, invName, permissionPolishName, permissionGuildType);
            });
        });
    }
    public void openAddPermission(Player player, Guild guild, GuildPermission permissionGuild){
        InventoryHelper inventoryHelper = new InventoryHelper(54, ChatUtilities.colored("&b&lNadawanie permisji"));
        Inventory inventory = inventoryHelper.getInventory();



        Integer[] glassBlueSlots = new Integer[]{0, 2, 6, 8, 18, 26, 36, 44, 45, 47, 51, 53, 4};
        Integer[] glassDarkBlueSlots = new Integer[]{1, 3, 5, 7, 9, 17, 27, 35, 46, 48, 50, 52};

        Arrays.stream(glassBlueSlots).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack()));
        Arrays.stream(glassDarkBlueSlots).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack()));



        inventory.setItem(49, new ItemHelper(Material.DARK_OAK_FENCE_GATE).setName("&cWyjdz").toItemStack());

        for(String member : guild.getMembers()){
            User user = this.userService.getOrCreate(member);
            ItemHelper memberItem = new ItemHelper(Material.PLAYER_HEAD, 1, (short) 3)
                    .setOwner(member)
                    .setName(user.getNickName())
                    .addLore("&8>> &fPunkty: &b" + user.getPoints())
                    .addLore("&8>> &fZabojstwa: &a" + user.getKills())
                    .addLore("&8>> &fZgony: &c" + user.getDeaths())
                    .addLore("")
                    .addLore("&bLewy &8- &fAby nadac permisje: &b" + permissionGuild.getPermissionType().name());
            inventory.addItem(memberItem.toItemStack());
        }

        inventoryHelper.click(event -> {

            event.setCancelled(true);

            if(event.getSlot() == 49){
                player.closeInventory();
            }

            ItemStack itemStack = event.getCurrentItem();
            if(itemStack == null)return;
            ItemMeta itemMeta = itemStack.getItemMeta();
            if(itemMeta == null)return;
            if(!itemStack.getType().equals(Material.PLAYER_HEAD))return;
            if(itemMeta.getDisplayName() == null)return;
            String nickName = itemMeta.getDisplayName();

            Arrays.stream(guild.getGuildPermissionList().toArray(new GuildPermission[0])).forEach(permissionGuilds -> permissionGuilds.removeMember(guild, nickName));
            permissionGuild.addMember(guild, nickName);
            player.closeInventory();
            guild.sendMessage("&3&lGILDIA &8>> &fNadano czlonkowi: &b" + nickName + " &fpermisje: &b" + permissionGuild.getPermissionType().name(), BukkitMain.getInstance().getSectorService());
        });
        player.openInventory(inventory);
    }
}

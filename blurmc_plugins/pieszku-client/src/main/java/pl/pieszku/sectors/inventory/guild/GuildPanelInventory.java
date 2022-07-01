package pl.pieszku.sectors.inventory.guild;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.pieszku.api.objects.guild.impl.Guild;
import org.pieszku.api.service.GuildService;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.helper.InventoryHelper;
import pl.pieszku.sectors.helper.ItemHelper;
import pl.pieszku.sectors.impl.HeadType;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.util.Arrays;

public class GuildPanelInventory {


    private final GuildService guildService = BukkitMain.getInstance().getGuildService();
    private final GuildPermissionInventory guildPermissionInventory = new GuildPermissionInventory();

    public void show(Player player, Guild guild){
        InventoryHelper inventoryHelper = new InventoryHelper(54, ChatUtilities.colored("&b&lGILDIA &8&l->> &f&lPANEL"));
        Inventory inventory = inventoryHelper.getInventory();


        Integer[] glassBlueSlots = new Integer[]{0, 2, 6, 8, 18, 26, 36, 44, 45, 47, 51, 53, 49};
        Integer[] glassDarkBlueSlots = new Integer[]{1,3 ,5 , 7, 9, 17, 27, 35, 46, 48, 50, 52};

        Arrays.stream(glassBlueSlots).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack()));
        Arrays.stream(glassDarkBlueSlots).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack()));


        ItemHelper guildInfoItem = new ItemHelper(Material.PLAYER_HEAD, 1, (short) 3)
                .setName(" ").setOwnerUrl(HeadType.HEAD_TWO.getUrl());
        guildInfoItem.addLore("   &3&lZARZAD");
        guildInfoItem.addLore("&fZalozyciel: &b" + guild.getOwner());
        guildInfoItem.addLore("&fZastepca: &b" + (guild.getLeader().equalsIgnoreCase("null") ? "brak" : guild.getLeader()));
        guildInfoItem.addLore("&fMistrz: &b" + (guild.getMaster().equalsIgnoreCase("null") ? "brak" : guild.getMaster()));
        guildInfoItem.addLore("");
        guildInfoItem.addLore("  &3&lSTATYSTYKI");
        guildInfoItem.addLore("&fPunkty: &a" + guild.getPoints());
        guildInfoItem.addLore("&fZabojstwa: &a" + guild.getKills());
        guildInfoItem.addLore("&fZgony: &c" + guild.getDeaths());
        guildInfoItem.addLore("");
        guildInfoItem.addLore("  &3&lINFORMACJE");
        guildInfoItem.addLore("&fLokacja: &aX&8:&2" + guild.getLocationSerializer().getX() + "&f, &aZ&8:&2" + guild.getLocationSerializer().getZ());
        guildInfoItem.addLore("&fRozmiar gildii&8(&a" + guild.getLocationSerializer().getSize() + "&7x&a" + guild.getLocationSerializer().getSize() + "&8)");
        guildInfoItem.addLore("&fOstatnia synchronizacja: &a" + guild.getLatestSynchronizeData().toLocaleString() + "&8(&fInterakcja&8)");
        guildInfoItem.addLore("&fData zalozenia: &a" + guild.getCreateDate().toLocaleString());
        guildInfoItem.addLore("&fCzlonkowie&8(&a" + guild.getMembers().toString().replace("[", "").replace("]", "")
                + "&f, &b" + guild.getMembers().size() + " &flacznie&8)");
        guildInfoItem.addLore("&fSojusze&8(" + ((guild.getAllys().size() == 0) ? "&cbrak&8)" : "&a" + guild.getAllys().toString().replace("[", "").replace("]", "")
                + "&f, &b" + guild.getAllys().size() + " &flacznie&8)"));

        inventory.setItem(4, guildInfoItem.toItemStack());


        ItemHelper permissionGuildItem = new ItemHelper(Material.PLAYER_HEAD, 1, (short) 3)
                .setName("&6Schematy uprawnien")
                .addLore("")
                .addLore(" &ePrzygotwane schematy:")
                .addLore("&aZALOZCIEL")
                .addLore("&cZASTEPCA")
                .addLore("&aMISTRZ")
                .addLore("&cCZLONEK")
                .addLore("")
                .addLore(" &eJak skonfigurowac?")
                .addLore("&6&lLEWY &8&l- &fAby przejsc dalej")
                .addLore("")
                .setOwnerUrl(HeadType.HEAD_CRAFTING.getUrl());

        inventory.setItem(10, permissionGuildItem.toItemStack());


        inventoryHelper.click(event -> {
            event.setCancelled(true);

            switch (event.getSlot()){
                case 10:{
                    this.guildPermissionInventory.show(player);
                }
            }

        });

        player.openInventory(inventory);

    }

}

package pl.pieszku.sectors.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.pieszku.api.service.UserService;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.helper.InventoryHelper;
import pl.pieszku.sectors.helper.ItemHelper;
import pl.pieszku.sectors.impl.HeadType;
import pl.pieszku.sectors.inventory.guild.GuildHelpInventory;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.util.Arrays;

public class HelpInventories {

    private final GuildHelpInventory guildHelpInventory = new GuildHelpInventory();
    private final UserService userService = BukkitMain.getInstance().getUserService();
    private final DropInventory dropInventory = new DropInventory();
    private final BazaarInventory bazaarInventory = new BazaarInventory();
    private final DepositInventory depositInventory = new DepositInventory();

    public void show(Player player){
        InventoryHelper inventoryHelper = new InventoryHelper(54, ChatUtilities.colored("&9&lONE&f&lHARD &8&l:: &f&lPOMOC"));
        Inventory inventory = inventoryHelper.getInventory();


        Integer[] slotsBlackGlass = new Integer[]{1, 7, 9, 17, 19, 25, 36, 44};
        Integer[] slotsDarkBlueGlass = new Integer[]{37, 43, 48, 47, 50, 51};
        Integer[] slotsBlueGlass = new Integer[]{3, 5, 10, 11, 12, 14, 15, 16, 35, 33, 29, 27, 45, 53};
        Integer[] slotsWhiteGlass = new Integer[]{0, 2, 4, 6, 8, 18, 26, 28, 34, 46, 52};

        Arrays.stream(slotsBlackGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.BLACK_STAINED_GLASS_PANE, 1).setName(" ").toItemStack()));
        Arrays.stream(slotsDarkBlueGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.BLUE_STAINED_GLASS_PANE, 1 ).setName(" ").toItemStack()));
        Arrays.stream(slotsBlueGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1).setName(" ").toItemStack()));
        Arrays.stream(slotsWhiteGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.WHITE_STAINED_GLASS_PANE, 1).setName(" ").toItemStack()));


        ItemHelper quitItem = new ItemHelper(Material.BARRIER)
                .setName("&c&lWyjdż");
        inventory.setItem(49, quitItem.toItemStack());

        ItemHelper infoItem = new ItemHelper(Material.BOOKSHELF)
                .setName(" ")
                .addLore("      &e&lPotrzebujesz pomocy?")
                .addLore("")
                .addLore("&f/msg <nick> <wiadomosc> &8- &fWiadomość do danego gracza")
                .addLore("&f/tpa <nick> &8- &7Teleportacja do danego gracza")
                .addLore("&f/gildia &8- &7Informacje na temat gildii")
                .addLore("&f/schowek &8= &7Twój własny depozyt")
                .addLore("&f/kit &8- &7Zestawy serwerowe")
                .addLore("&f/gracz <nick> &8- &7Informacje na temat danego gracza")
                .addLore("&f/vip &8- &7Informacje na temat rangi &6&lVIP")
                .addLore("&f/svip &8- &7Informacje na temat rangi &5&lSVIP")
                .addLore("&f/sponsor &8- &7Informacje na temat rangi &9&lSPONSOR")
                .addLore("&f/youtuber &8- &7Informacje na temat rangi &f&lY&c&lT")
                .addLore("&f/premium &8- &7Informacje na temat rangi &b&lPREMIUM")
                .addLore("&f/helpop <wiadomość> &fZgłoś się do admininstracji")
                .addLore("")
                .addLore("          &3&lMEDIA")
                .addLore("  &fStrona: &bhttps://www.blurmc.pl")
                .addLore("  &fDiscord: &bdc.blurmc.pl")
                .addLore("  &fFanpage: &bfb.blurmc.pl")
                .addLore("");

        inventory.setItem(13, infoItem.toItemStack());


        ItemHelper premiumInfoItem = new ItemHelper(Material.DIAMOND_CHESTPLATE)
                .setName("&7Informacje na temat rangi &b&lPREMIUM")
                .addLore("")
                .addLore("      &3&lPRZYWILEJE:")
                .addLore("&7Dostęp do &bwszystkich &f6 &7domów")
                .addLore("&7Dostęp do &bwszystkich &f6 &7enderchestów")
                .addLore("&e-50% &7przedmiotów na gildię")
                .addLore("&7Dostęp do &9&lDISCO-ZBROI")
                .addLore("&7Dostęp do wszystkich zestawów pod: &b/kit")
                .addLore("&7Dostęp do komendy: &b/enderchest")
                .addLore("&7Dostęp do komendy: &b/wb")
                .addLore("&7Możliwość wystawienia &f25 przedmiotów &7pod: &b/bazar")
                .addLore("&7Powiększony drop o: &b3.25% &7więcej pod: &b/drop")
                .addLore("&7Codzienne nagrody w postaći np: &e&lTurbo&6&lDrop")
                .addLore("")
                .addLore("   &7Więcej informacji na naszej stronie")
                .addLore("              &b&lwww.blurmc.pl")
                .addLore("");
        inventory.setItem(20, premiumInfoItem.toItemStack());

        ItemHelper sponsorInfoItem = new ItemHelper(Material.GOLDEN_CHESTPLATE)
                .setName("&7Informacje na temat rangi &9&lSPONSOR")
                .addLore("")
                .addLore("      &b&lPRZYWILEJE:")
                .addLore("&7Dostęp do &f5 &7domów")
                .addLore("&7Dostęp do &f5 &7enderchestów")
                .addLore("&e-50% &7przedmiotów na gildię")
                .addLore("&7Dostęp do &b&lDISCO-ZBROI")
                .addLore("&7Dostęp do wszystkich zestawów poza &b&lPREMIUM &7pod: &b/kit")
                .addLore("&7Dostęp do komendy: &b/enderchest")
                .addLore("&7Dostęp do komendy: &b/wb")
                .addLore("&7Możliwość wystawienia &f15 przedmiotów &7pod: &b/bazar")
                .addLore("&7Powiększony drop o: &b2.25% &7więcej pod: &b/drop")
                .addLore("&7Codzienne nagrody w postaći np: &e&lTurbo&6&lDrop")
                .addLore("")
                .addLore("   &7Więcej informacji na naszej stronie")
                .addLore("              &9&lwww.blurmc.pl")
                .addLore("");
        inventory.setItem(21, sponsorInfoItem.toItemStack());

        ItemHelper youtuberInfoItem = new ItemHelper(Material.IRON_CHESTPLATE)
                .setName("&7Informacje na temat rangi &f&lY&c&lT")
                .addLore("")
                .addLore("      &b&lPRZYWILEJE:")
                .addLore("&cTo samo co ranga &9&lSPONSOR")
                .addLore("&7Dostęp do &f5 &7domów")
                .addLore("&7Dostęp do &f5 &7enderchestów")
                .addLore("&e-50% &7przedmiotów na gildię")
                .addLore("&7Dostęp do &b&lDISCO-ZBROI")
                .addLore("&7Dostęp do wszystkich zestawów poza &b&lPREMIUM &7pod: &b/kit")
                .addLore("&7Dostęp do komendy: &b/enderchest")
                .addLore("&7Dostęp do komendy: &b/wb")
                .addLore("&7Możliwość wystawienia &f15 przedmiotów &7pod: &b/bazar")
                .addLore("&7Powiększony drop o: &b2.25% &7więcej pod: &b/drop")
                .addLore("&7Codzienne nagrody w postaći np: &e&lTurbo&6&lDrop")
                .addLore("")
                .addLore("      &b&lWYMAGANIA")
                .addLore("&fMusisz wstawić reklame naszego serwera")
                .addLore("&fMusisz posiadać minimum &7100 &csubskrypcji &fna kanale")
                .addLore("")
                .addLore("   &7Więcej informacji na naszej stronie")
                .addLore("              &c&ldc.blurmc.pl")
                .addLore("");
        inventory.setItem(22, youtuberInfoItem.toItemStack());

        ItemHelper svipInfoItem = new ItemHelper(Material.CHAINMAIL_CHESTPLATE)
                .setName("&7Informacje na temat rangi &5&lSVIP")
                .addLore("")
                .addLore("      &5&lPRZYWILEJE:")
                .addLore("&7Dostęp do &d3 &7domów")
                .addLore("&7Dostęp do &d3 &7enderchestów")
                .addLore("&e-50% &7przedmiotów na gildię")
                .addLore("&7Dostęp do &d&lDISCO-ZBROI")
                .addLore("&7Dostęp do zestawu &6&lVIP&8, &5&lSVIP &7pod: &d/kit")
                .addLore("&7Możliwość wystawienia &d10 przedmiotów &7pod: &d/bazar")
                .addLore("&7Dostęp do komendy: &d/enderchest")
                .addLore("&7Dostęp do komendy: &d/wb")
                .addLore("&7Powiększony drop o: &d1.25% &7więcej pod: &d/drop")
                .addLore("")
                .addLore("   &7Więcej informacji na naszej stronie")
                .addLore("              &d&lwww.blurmc.pl")
                .addLore("");
        inventory.setItem(23, svipInfoItem.toItemStack());

        ItemHelper vipInfoItem = new ItemHelper(Material.LEATHER_CHESTPLATE)
                .setName("&7Informacje na temat rangi &6&lVIP")
                .addLore("")
                .addLore("      &e&lPRZYWILEJE:")
                .addLore("&7Dostęp do &62 &7domów")
                .addLore("&7Dostęp do &62 &7enderchestów")
                .addLore("&e-50% &7przedmiotów na gildię")
                .addLore("&7Dostęp do &6&lDISCO-ZBROI")
                .addLore("&7Dostęp do zestawu &6&lVIP &7pod: &6/kit")
                .addLore("&7Dostęp do komendy: &6/enderchest")
                .addLore("&7Dostęp do komendy: &6/wb")
                .addLore("&7Możliwość wystawienia &65 przedmiotów &7pod: &6/bazar")
                .addLore("&7Powiększony drop o: &60.75% &7więcej pod: &6/drop")
                .addLore("")
                .addLore("   &7Więcej informacji na naszej stronie")
                .addLore("              &6&lwww.blurmc.pl")
                .addLore("");
        inventory.setItem(24, vipInfoItem.toItemStack());


        ItemHelper guildInfoItem = new ItemHelper(Material.PLAYER_HEAD, 1, (short) 3)
                .setOwnerUrl(HeadType.HEAD_FOUR.getUrl())
                .setName("&3&lGILDIE")
                .addLore("")
                .addLore("  &b&lJAK ZOBACZYĆ INFORMACJE?")
                .addLore("&3Lewy &8- &fAby przejść dalej");

        inventory.setItem(30, guildInfoItem.toItemStack());

        ItemHelper dropsInfoItem = new ItemHelper(Material.PLAYER_HEAD, 1, (short) 3)
                .setOwnerUrl("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmQwZjQwNjFiZmI3NjdhN2Y5MjJhNmNhNzE3NmY3YTliMjA3MDliZDA1MTI2OTZiZWIxNWVhNmZhOThjYTU1YyJ9fX0=")
                .setName("&6&lDROPY SERWEROWE")
                .addLore("")
                .addLore("  &e&lJAK ZOBACZYĆ DROPY?")
                .addLore("&6Lewy &8- &fAby przejść dalej");

        inventory.setItem(31, dropsInfoItem.toItemStack());

        ItemHelper craftingInfoItem = new ItemHelper(Material.PLAYER_HEAD, 1, (short) 3)
                .setOwnerUrl(HeadType.HEAD_CRAFTING.getUrl())
                .setName("&e&lCRAFTINGI SERWEROWE")
                .addLore("")
                .addLore("  &6&lJAK ZOBACZYĆ RECEPTURY?")
                .addLore("&eLewy &8- &fAby przejść dalej");

        inventory.setItem(32, craftingInfoItem.toItemStack());


        ItemHelper topsInfoItem = new ItemHelper(Material.PLAYER_HEAD, 1, (short) 3)
                .setOwner(player.getName())
                .setName("&5&lTOPKI NAJLEPSZYCH GRACZY")
                .addLore("")
                .addLore("  &d&lJAK ZOBACZYĆ RANKINGI?")
                .addLore("&5Lewy &8- &fAby przejść dalej");

        inventory.setItem(38, topsInfoItem.toItemStack());

        ItemHelper depositInfoItem = new ItemHelper(Material.PLAYER_HEAD, 1, (short) 3)
                .setOwnerUrl("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzc5MWVlOGQzMGNjYTNmZTUyNjNiNjg4ZGQ2NzQ4YmRlNTZlOTE1YTE1OTBlNTc3ZWRiNDIzNmMzN2EwYjI4MiJ9fX0=")
                .setName("&6&lTWÓJ DEPOZYT")
                .addLore("")
                .addLore("&e&lJAK ZOBACZYĆ SWÓJ SCHOWEK?")
                .addLore("&6Lewy &8- &fAby przejść dalej");

        inventory.setItem(39, depositInfoItem.toItemStack());



        ItemHelper enderchestInfoItem = new ItemHelper(Material.PLAYER_HEAD, 1, (short) 3)
                .setOwnerUrl("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTg4MTQ5ZTU2Y2RlMDJlZWU2MjA5ODI4ODRiODkzNjM0NzU2YmU5MTQxZjJlMjc3N2I1MWNiMGE2NmMzMWRjZSJ9fX0=")
                .setName("&5&lTWOJE ENDERCHESTY")
                .addLore("")
                .addLore("&d&lJAK ZOBACZYĆ ENDERCHESTY?")
                .addLore("&5Lewy &8- &fAby przejść dalej");

        inventory.setItem(40, enderchestInfoItem.toItemStack());

        ItemHelper bazaarInfoItem = new ItemHelper(Material.PLAYER_HEAD, 1, (short) 3)
                .setOwnerUrl(HeadType.HEAD_CHEST.getUrl())
                .setName("&a&lBAZAR SERWEROWY")
                .addLore("")
                .addLore("   &2&lJAK ZOBACZYĆ RYNEK?")
                .addLore("&aLewy &8- &fAby przejść dalej");

        inventory.setItem(41, bazaarInfoItem.toItemStack());

        ItemHelper shopInfoItem = new ItemHelper(Material.PLAYER_HEAD, 1, (short) 3)
                .setOwnerUrl("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGJhNTU2NzFmOTdmZjNiZmM1YmUzMzVhZTkyY2Q5NzQ5YWJkNjE5ZTdhZmMyYTY2NzM1OTdiODBiNzU1Yzc0MSJ9fX0=")
                .setName("&a&lSKLEP SERWEROWY")
                .addLore("")
                .addLore("   &2&lJAK ZOBACZYĆ SKLEP?")
                .addLore("&aLewy &8- &fAby przejść dalej");

        inventory.setItem(42, shopInfoItem.toItemStack());



        inventoryHelper.click(event -> {
            event.setCancelled(true);


            switch (event.getSlot()){
                case 49:{
                    player.closeInventory();
                    break;
                }
                case 42:{
                    ShopInventory shopInventory = new ShopInventory();
                    shopInventory.show(player);
                    break;
                }
                case 41:{
                    this.bazaarInventory.init(player, 1, 1);
                    break;
                }
                case 40:{
                    //enderchest open
                    break;
                }
                case 39:{
                    this.depositInventory.show(player);
                    break;
                }
                case 38:{
                    //tops open
                    break;
                }
                case 32:{
                    //craftings open
                    break;
                }
                case 31:{
                    this.dropInventory.init();
                    this.dropInventory.show(player);
                    break;
                }
                case 30:{
                    this.guildHelpInventory.show(player);
                    break;
                }
            }



        });


        player.openInventory(inventory);
    }
}

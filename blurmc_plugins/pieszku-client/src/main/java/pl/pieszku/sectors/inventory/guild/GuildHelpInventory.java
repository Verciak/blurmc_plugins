package pl.pieszku.sectors.inventory.guild;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.pieszku.api.objects.guild.impl.Guild;
import org.pieszku.api.service.GuildService;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.helper.InventoryHelper;
import pl.pieszku.sectors.helper.ItemHelper;
import pl.pieszku.sectors.impl.HeadType;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.util.Arrays;
import java.util.Optional;

public class GuildHelpInventory {

    private final GuildService guildService = BukkitMain.getInstance().getGuildService();

    public void show(Player player) {
        InventoryHelper inventoryHelper = new InventoryHelper(InventoryType.BREWING, ChatUtilities.colored("&3&lGILIDA &8&l:: &f&lPOMOC"));
        Inventory inventory = inventoryHelper.getInventory();


        ItemHelper guildCreateItem = new ItemHelper(Material.PLAYER_HEAD, 1, (short) 3)
                .setOwnerUrl(HeadType.HEAD_ANVIL.getUrl())
                .setName(" ")
                .addLore("      &3&lTWORZENIE GILDII")
                .addLore("  &8(&fPostepuj wedlug instrukcji&8)")
                .addLore("")
                .addLore("          &3&lINSTRUKCJA")
                .addLore("&7Aby utworzyc wlasna gildie musisz przejsc dalej")
                .addLore("&7nastepnie pokaze ci sie &bkowadlo &7w ktorym wpisujesz")
                .addLore("&7nazwe swojej &bgildii &7po wpisaniu kliknij w glowke")
                .addLore("&7nastepnie przejdziesz do etapu gdzie musisz")
                .addLore("&7wpisac pelna nazwe &bswojej &7gildii")
                .addLore("&7jesli bedziesz spelnial wszystkie wymagania np: &bitemy")
                .addLore("&7twoja &bgildia &7zostanie pomyslnie &bstworzona &7do dziela")
                .addLore("")
                .addLore("      &3&lJAK TO ZROBIC?")
                .addLore("&b&lLEWY &8&l- &fAby przejsc do tworzenia")
                .addLore("");

        inventory.setItem(1, guildCreateItem.toItemStack());


        Optional<Guild> optionalGuild = this.guildService.findGuildByMember(player.getName());

        ItemHelper guildOwnInfoItem = new ItemHelper(Material.PLAYER_HEAD, 1, (short) 3);
        guildOwnInfoItem.setOwnerUrl(HeadType.HEAD_FIRST.getUrl());
        guildOwnInfoItem.setName(" ");
        if(!optionalGuild.isPresent()){
            guildOwnInfoItem.addLore("&8(&fNie posiadasz gildii&8)");
        }
        optionalGuild.ifPresent(guild -> {
            guildOwnInfoItem.addLore("   &3&lZARZAD");
            guildOwnInfoItem.addLore("&fZalozyciel: &b" + guild.getOwner());
            guildOwnInfoItem.addLore("&fZastepca: &b" + (guild.getLeader().equalsIgnoreCase("null") ? "brak" : guild.getLeader()));
            guildOwnInfoItem.addLore("&fMistrz: &b" + (guild.getMaster().equalsIgnoreCase("null") ? "brak" : guild.getMaster()));
            guildOwnInfoItem.addLore("");
            guildOwnInfoItem.addLore("  &3&lSTATYSTYKI");
            guildOwnInfoItem.addLore("&fPunkty: &a" + guild.getPoints());
            guildOwnInfoItem.addLore("&fZabojstwa: &a" + guild.getKills());
            guildOwnInfoItem.addLore("&fZgony: &c" + guild.getDeaths());
            guildOwnInfoItem.addLore("");
            guildOwnInfoItem.addLore("  &3&lINFORMACJE");
            guildOwnInfoItem.addLore("&fLokacja: &aX&8:&2" + guild.getLocationSerializer().getX() + "&f, &aZ&8:&2" + guild.getLocationSerializer().getZ());
            guildOwnInfoItem.addLore("&fRozmiar gildii&8(&a" + guild.getLocationSerializer().getSize() + "&7x&a" + guild.getLocationSerializer().getSize() + "&8)");
            guildOwnInfoItem.addLore("&fOstatnia synchronizacja: &a" + guild.getLatestSynchronizeData().toLocaleString() + "&8(&fInterakcja&8)");
            guildOwnInfoItem.addLore("&fData zalozenia: &a" + guild.getCreateDate().toLocaleString());
            guildOwnInfoItem.addLore("&fCzlonkowie&8(&a" + guild.getMembers().toString().replace("[", "").replace("]", "")
                    + "&f, &b" + guild.getMembers().size() + " &flacznie&8)");
            guildOwnInfoItem.addLore("&fSojusze&8(" + ((guild.getAllys().size() == 0) ? "&cbrak&8)" : "&a" + guild.getAllys().toString().replace("[", "").replace("]", "")
                    + "&f, &b" + guild.getAllys().size() + " &flacznie&8)"));
        });

        inventory.setItem(0, guildOwnInfoItem.toItemStack());

        ItemHelper teleportItem = new ItemHelper(Material.ENDER_PEARL)
                .visibleFlag().addEnchant(Enchantment.ARROW_DAMAGE, 10)
                .setName(" ")
                .addLore("  &3&lTELEPORTACJA DO GILDII")
                .addLore("&bLEWY &8&l- &fAby sie przeteleportowac")
                .addLore("");

        inventory.setItem(2, teleportItem.toItemStack());



        ItemHelper guildInfoItem = new ItemHelper(Material.PLAYER_HEAD, 1, (short) 3)
                .setOwnerUrl(HeadType.HEAD_SIX.getUrl())
                .setName(" ")
                .setLore(Arrays.asList("&f«&3&l&m----[&7&l&m---&f&m&l[--&f &3&lKOMENDY &f&l&m--]&7&l&m---]&3&l&m----&8&f»",
                        "&b/g zaloz <nazwa> <pelnaNazwa> &8- &fZakladanie gildii",
                        "&b/g itemy &8- &fZobacz przedmioty na gildie",
                        "&b/g zapros <nick> &8- &fZapraszanie gracza do gildii",
                        "&b/g wyrzuc <nick> &8- &fUsuwanie gracza z gildii",
                        "&b/g dolacz &8- &fDolacz do gildii",
                        "&b/g opusc &8- &fOpusc wlasna gildie",
                        "&b/g info <tag> &8- &fInformacje o danej gildii",
                        "&b/g sojusz &8- &fZarzadzaj sojuszami swojej gildii",
                        "&b/g apvp &8- &fZarzadzanie pvp w sojuszu",
                        "&b/g usun &8- &fUsuwanie swojej gildii",
                        "&b/g skarbiec &8- &fSkarbiec gildyjny",
                        "&b/g ustawdom &8- &fUstaw lokalizacje teleportu",
                        "&b/g baza &8- &fTeleportacja do bazy",
                        "&b/g zalozyciel <nick> &8- &fNadaj nowego zalozyciela",
                        "&b/g zastepca <nick> &8- &fNadaj nowego zastepce",
                        "&b/g mistrz <nick> &8- &fNadaj nowego mistrza",
                        "&b/g panel &8- &fZarzadzaj ustawieniami gildyjnymi",
                        "&b/g pokazteren &8- &fPodejrzyj swoj teren gildii",
                        "&f«&3&l&m----[&7&l&m---&f&m&l[--&f&l&m&8&f»     «&l&m--]&7&l&m---]&3&l&m----&8&f»"));

        inventory.setItem(3, guildInfoItem.toItemStack());

        inventoryHelper.click(event -> {
            event.setCancelled(true);
        });

        player.openInventory(inventory);

    }

}

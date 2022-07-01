package pl.pieszku.sectors.inventory;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.pieszku.api.API;
import org.pieszku.api.cache.BazaarCache;
import org.pieszku.api.objects.bazaar.Bazaar;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.cache.BazaarGUI;
import pl.pieszku.sectors.cache.BazaarGUICache;
import pl.pieszku.sectors.helper.ItemHelper;
import pl.pieszku.sectors.impl.HeadType;
import pl.pieszku.sectors.serializer.ItemSerializer;
import pl.pieszku.sectors.utilities.SignEditorUtilities;

import java.util.Arrays;

public class BazaarInventory {

    private final BazaarCache bazaarCache = API.getInstance().getBazaarCache();
    private final BazaarGUICache bazaarGUICache = BukkitMain.getInstance().getBazaarGUICache();

    public void init(Player player, int page, int beforePage) {
        BazaarGUI guiPageBefore = this.bazaarGUICache.findBazaarGUIOrCreate(beforePage);
        BazaarGUI bazaarGUIPage = this.bazaarGUICache.findBazaarGUIOrCreate(page);
        this.initInventory(bazaarGUIPage.getInventory());
        this.init(guiPageBefore.getInventory(), page);
        player.openInventory(bazaarGUIPage.getInventory());

        bazaarGUIPage.handleClickConsumer(event -> {
            event.setCancelled(true);

            if (event.getSlot() == 51) {
                if (bazaarGUIPage.getInventory().firstEmpty() == -1) {
                    this.init(player, bazaarGUIPage.getPage() + 1, page);
                    System.out.println(page + 1 + ":" + page);
                    return;
                }
                player.sendMessage("Nie posiadamy nastepnych stron.");
                return;
            }
            if(event.getSlot() == 16) {
                new SignEditorUtilities().openSignEditorToPlayer(player, "", "^^^", "Podaj kwote", "***");
                System.out.println(bazaarCache.getBazaars().size());
                this.bazaarCache.create(player.getName(), 1, ItemSerializer.decodeItems(new ItemStack[]{player.getItemInHand()}));
            }
        });
    }

    public void init(Inventory inventory, int page) {
        for (Bazaar bazaar : this.bazaarCache.getBazaars()) {
            if (bazaar.hasBazaarClient()) continue;

            ItemStack[] itemStacks = ItemSerializer.encodeItem(bazaar.getSerializedItem());
            ItemStack itemStack = itemStacks[0];
            if (itemStack == null || itemStack.getType() == Material.AIR) continue;

            ItemHelper bazaarItem = new ItemHelper(itemStack)
                    .addLore("&7Id: " + bazaar.getId())
                    .addLore("")
                    .addLore("");

            if (inventory.firstEmpty() == -1) {
                BazaarGUI bazaarGUIPage = this.bazaarGUICache.findBazaarGUIOrCreate(page);
                bazaarGUIPage.getInventory().addItem(bazaarItem.toItemStack());
            } else {
                inventory.addItem(bazaarItem.toItemStack());
            }
        }
    }
    public void initInventory(Inventory inventory){
        Integer[] cyanGlassPaneSlots = new Integer[]{0, 1, 7, 8, 9, 17, 36, 44, 45, 46, 52, 53};
        Integer[] whiteGlassPaneSlots = new Integer[]{11, 13, 15, 18, 26, 27, 35, 48, 50};
        Integer[] lightGlassPaneSlots = new Integer[]{3, 5};
        Integer[] blackGlassPaneSlots = new Integer[]{2, 6};

        Arrays.stream(cyanGlassPaneSlots).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.CYAN_STAINED_GLASS_PANE).setName(" ").toItemStack()));
        Arrays.stream(whiteGlassPaneSlots).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.WHITE_STAINED_GLASS_PANE).setName(" ").toItemStack()));
        Arrays.stream(lightGlassPaneSlots).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack()));
        Arrays.stream(blackGlassPaneSlots).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.BLACK_STAINED_GLASS_PANE).setName(" ").toItemStack()));

        ItemHelper searchBazaarItem = new ItemHelper(Material.PLAYER_HEAD, 1, (short) 3)
                .setName(" ")
                .addLore(" &b&lWYSZUKAJ BAZAR DANEGO GRACZA")
                .addLore(" &3&lLEWY &8&l- &fABY WYSZUKAC BAZAR")
                .addLore("")
                .setOwnerUrl(HeadType.HEAD_SEARCH.getUrl());
        inventory.setItem(4, searchBazaarItem.toItemStack());

        ItemHelper bazaarOwnItems = new ItemHelper(Material.PLAYER_HEAD, 1, (short) 3)
                .setName(" ")
                .addLore("    &a&lODBIERZ NIE SPRZEDANE PRZEDMIOTY")
                .addLore("   &fTutaj mozesz odebrac przedmioty ktore")
                .addLore("&fzostaly przez ciebie wystawione lecz nie sprzedane")
                .addLore("    &a&lLEWY &8&l- &2&lABY PRZEJSC DALEJ")
                .addLore("")
                .setOwnerUrl(HeadType.HEAD_CHEST.getUrl());
        inventory.setItem(10, bazaarOwnItems.toItemStack());

        ItemHelper collectMoneyItem = new ItemHelper(Material.CLOCK)
                .setName(" ")
                .addLore("&a&lODBIERZ ZAROBIONE MONETY ZA PRZEDMIOTY")
                .addLore("       &f&lZarobiles: &a&l3245 monet")
                .addLore("   &e&lDo wyplaty masz&8&l: &6&l258 monet")
                .addLore("  &2&lLEWY &8&l- &a&lABY PRZEJSC DALEJ")
                .addLore("");

        inventory.setItem(12, collectMoneyItem.toItemStack());

        ItemHelper informationBazaarItem = new ItemHelper(Material.BOOK)
                .visibleFlag().addEnchant(Enchantment.ARROW_DAMAGE, 10)
                .setName(" ")
                .addLore("  &6&lJAK DZIALA BAZAR SERWEROWY?")
                .addLore(" &fPrzedmiot ktory wezmiesz do reki")
                .addLore(" &fbedzie &cprzedmiotem ktory zostanie wystawiony")
                .addLore(" &fkliknij w &bprzedmiot obok wpisz cene i gotowe")
                .addLore(" &ftwoj &eprzedmiot &fzostanie wystawiony na czas")
                .addLore(" &e24 godzin oraz za kwote ktora podales")
                .addLore(" ");
        inventory.setItem(14, informationBazaarItem.toItemStack());

        ItemHelper sellBazaarItem = new ItemHelper(Material.PLAYER_HEAD, 1, (short) 3)
                .setOwnerUrl(HeadType.HEAD_ANVIL.getUrl())
                .setName(" ")
                .addLore("   &6&lWYSTAW SWOJ PRZEDMIOT")
                .addLore(" &e&lLEWY &8&l- &f&lABY PRZEJSC DALEJ")
                .addLore("");
        inventory.setItem(16, sellBazaarItem.toItemStack());

        ItemHelper nextPage = new ItemHelper(Material.LIME_DYE)
                .setName("&a&l&n->>> &a&lNASTEPNA STRONA &l&n->>>");

        ItemHelper backPage = new ItemHelper(Material.RED_DYE)
                .setName("&c&l&n<<- &c&lPOPRZEDNIA STRONA &l&n<<-");

        inventory.setItem(49, new ItemHelper(Material.BARRIER).setName("&c&lWYJDZ").toItemStack());
        inventory.setItem(47, backPage.toItemStack());
        inventory.setItem(51, nextPage.toItemStack());



    }
}
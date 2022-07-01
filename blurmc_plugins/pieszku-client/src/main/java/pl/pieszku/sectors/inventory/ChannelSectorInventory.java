package pl.pieszku.sectors.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.pieszku.api.sector.Sector;
import org.pieszku.api.sector.SectorService;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.helper.InventoryHelper;
import pl.pieszku.sectors.helper.ItemHelper;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.util.Collections;
import java.util.List;

public class ChannelSectorInventory {

    private final SectorService sectorService = BukkitMain.getInstance().getSectorService();

    public Inventory show(Player player) {
        InventoryHelper inventoryHelper = new InventoryHelper(InventoryType.HOPPER, ChatUtilities.colored("&bLista dostępnych spawnów"));
        Inventory inventory = inventoryHelper.getInventory();

        List<Sector> sectors = this.sectorService.getSectorList();
        synchronized (Collections.unmodifiableList(sectors)){
            sectors.sort((o1, o2) -> {
                if(o1.isSpawn() && o2.isSpawn()) {
                    Integer compareOne = Integer.valueOf(o1.getName().split("spawn")[1]);
                    Integer compareTwo = Integer.valueOf(o2.getName().split("spawn")[1]);
                    return compareOne.compareTo(compareTwo);
                }
                return 0;
            });
        }

        inventory.setItem(0, new ItemHelper(Material.BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack());
        inventory.setItem(4, new ItemHelper(Material.BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack());

        for (Sector sector : sectors) {
            if (!sector.isSpawn()) continue;

            ItemHelper sectorItem = new ItemHelper(new ItemStack((sector.isOnline() ? Material.LIME_DYE : Material.RED_DYE)))
                    .setName("&8&m---&b&m---> &8 &3&l" + sector.getName() + " &b&m<---&8&m---")
                    .addLore("")
                    .addLore("&fTPS&8: &a*20")
                    .addLore("&fOnline&8: " + (sector.isOnline() ? "&aTAK" : "&cNIE"))
                    .addLore("&fGraczy&8: &b" + sector.getPlayers().size())
                    .addLore("")
                    .addLore("&3Lewy &8- &fAby się przełączyć")
                    .addLore((sector.getName().equals(BukkitMain.getInstance().getSectorName()) ? "&aZanjdujesz się tutaj." : "&aJesteś aktualnie na: " + BukkitMain.getInstance().getSectorName() ))
                    .addLore("")
                    .addLore("&8&m---&b&m--->  &8   &b&m  <---&8&m---");


           inventory.addItem(sectorItem.toItemStack());
        }

        inventoryHelper.click(event -> {
            event.setCancelled(true);
        });

        return inventory;
    }
}

package pl.pieszku.sectors.inventory.guild;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.configuration.ConfigAPI;
import pl.pieszku.sectors.helper.InventoryHelper;
import pl.pieszku.sectors.helper.ItemHelper;
import pl.pieszku.sectors.utilities.ChatUtilities;
import pl.pieszku.sectors.utilities.ItemUtilities;

import java.util.Arrays;
import java.util.List;

public class GuildItemsInventory {


    private final ConfigAPI configAPI = BukkitMain.getInstance().getConfigAPI();

    public void show(Player player){
        InventoryHelper inventoryHelper = new InventoryHelper(9, ChatUtilities.colored("&3Przedmioty na gildię"));
        Inventory inventory = inventoryHelper.getInventory();

        List<String> itemsGuild = this.configAPI.getOrCreate("config.guild.items", Arrays.asList("DIAMOND:32"));

        for(String items : itemsGuild){
            String[] split = items.split(":");
            String material = split[0];
            int amount = Integer.parseInt(split[1]);
            ItemHelper item = new ItemHelper(Material.valueOf(material), amount);
            int amountPlayerItem = ItemUtilities.getAmountOf(player, Material.valueOf(material), (short) 0);
            item.setName("&7Posiadasz&8(&b" + amountPlayerItem + "&f/&3" + amount + "&8)");

            inventory.addItem(item.toItemStack());

        }

        inventoryHelper.click(event -> {
            event.setCancelled(true);
        });

        player.openInventory(inventory);

    }
}

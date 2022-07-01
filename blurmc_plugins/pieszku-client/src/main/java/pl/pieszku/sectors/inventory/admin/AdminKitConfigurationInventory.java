package pl.pieszku.sectors.inventory.admin;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.pieszku.api.objects.kit.Kit;
import org.pieszku.api.redis.packet.kit.sync.KitSynchronizeInformationPacket;
import org.pieszku.api.redis.packet.type.UpdateType;
import org.pieszku.api.sector.SectorService;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.helper.InventoryHelper;
import pl.pieszku.sectors.serializer.ItemSerializer;
import pl.pieszku.sectors.utilities.ChatUtilities;

public class AdminKitConfigurationInventory {


    private final SectorService sectorService = BukkitMain.getInstance().getSectorService();

    public void showEditKitGlassItem(Player player, Kit kit){
        InventoryHelper inventoryHelper = new InventoryHelper(54, ChatUtilities.colored("&fKonfiguracja szkła zestawu: &b" + kit.getKitName()));
        inventoryHelper.getInventory().setContents(ItemSerializer.encodeItem(kit.getGlassItemSerialized()));
        inventoryHelper.close(event -> {
            kit.setGlassItemSerialized(ItemSerializer.decodeItems(inventoryHelper.getInventory().getContents()));
            this.synchronize(player, kit, UpdateType.UPDATE);
        });
        player.openInventory(inventoryHelper.getInventory());
    }
    public void showEditKitItems(Player player, Kit kit){
        InventoryHelper inventoryHelper = new InventoryHelper(54, ChatUtilities.colored("&fKonfiguracja przedmiotów zestawu: &b" + kit.getKitName()));

        inventoryHelper.getInventory().setContents(ItemSerializer.encodeItem(kit.getItemSerialized()));

        inventoryHelper.close(event -> {
            kit.setItemSerialized(ItemSerializer.decodeItems(inventoryHelper.getInventory().getContents()));
            this.synchronize(player, kit, UpdateType.UPDATE);
        });
        player.openInventory(inventoryHelper.getInventory());
    }
    public void synchronize(Player player, Kit kit, UpdateType updateType){
        Bukkit.getScheduler().runTaskLaterAsynchronously(BukkitMain.getInstance(), () -> {
            new KitSynchronizeInformationPacket(kit.getKitName(), updateType, new Gson().toJson(kit)).sendToAllSectorsAndApp(this.sectorService);
            player.sendMessage(ChatUtilities.colored("&3&lKIT &fPrzeprowadzono pomyślnie synchronizacje zestawu: &b" + kit.getKitName()));
        }, 10L);
    }
}

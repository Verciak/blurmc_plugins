package pl.pieszku.sectors.inventory.admin;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.pieszku.api.API;
import org.pieszku.api.objects.backup.Backup;
import org.pieszku.api.redis.packet.backup.BackupSynchronizeInformationPacket;
import org.pieszku.api.redis.packet.type.UpdateType;
import org.pieszku.api.service.BackupService;
import pl.pieszku.sectors.helper.InventoryHelper;
import pl.pieszku.sectors.helper.ItemHelper;
import pl.pieszku.sectors.serializer.ItemSerializer;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.util.Arrays;

public class BackupInventory {

    private final BackupService backupService = API.getInstance().getBackupService();

    public void show(Player player, String targetNickName){
        InventoryHelper inventoryHelper = new InventoryHelper(54, ChatUtilities.colored("&3&lBACKUPY"));
        Inventory inventory = inventoryHelper.getInventory();


        Integer[] slotsBlackGlass = new Integer[]{0, 4, 8, 9, 17, 49, 45, 44, 53, 36};
        Integer[] slotsDarkBlueGlass = new Integer[]{3, 5,48,50};
        Integer[] slotsBlueGlass = new Integer[]{1, 2,6, 7, 47, 46, 51, 52};
        Integer[] slotsWhiteGlass = new Integer[]{18, 26, 27, 35};

        Arrays.stream(slotsBlackGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.BLACK_STAINED_GLASS_PANE, 1).setName(" ").toItemStack()));
        Arrays.stream(slotsDarkBlueGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.BLUE_STAINED_GLASS_PANE, 1 ).setName(" ").toItemStack()));
        Arrays.stream(slotsBlueGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1).setName(" ").toItemStack()));
        Arrays.stream(slotsWhiteGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.WHITE_STAINED_GLASS_PANE, 1).setName(" ").toItemStack()));


        for(Backup backup : this.backupService.getBackups()){
            if(backup.getNickName().equalsIgnoreCase(targetNickName)){

                ItemHelper backupItem = new ItemHelper(Material.ITEM_FRAME)
                        .setName("Id:" + backup.getId())
                        .addLore("")
                        .addLore("&fZapis danych gracza: &b" + backup.getNickName())
                        .addLore("&fZabity przez: &b" + backup.getKillerNickName())
                        .addLore("&fPing podczas śmierci: &b" + backup.getPing())
                        .addLore("&fPunkty podczas śmierci: &a" + backup.getPoints())
                        .addLore("&fZabójstwa podczas śmierci: &a" + backup.getKills())
                        .addLore("&fŚmierci podczas zgonu: &c" + backup.getDeaths())
                        .addLore("")
                        .addLore("&fZapis został utworzony: &a" + backup.getData())
                        .addLore("")
                        .addLore("&3Lewy &8- &fAby oddać zapis")
                        .addLore("&3Prawy &8- &fAby podejrzeć przedmioty")
                        .addLore("&3SHIFT + Lewy &8- &fAby usunąć zapis");

                inventory.addItem(backupItem.toItemStack());
            }
        }


        ItemHelper barrierItem = new ItemHelper(Material.BARRIER).setName("&c&lWYJDŻ");
        inventory.setItem(49, barrierItem.toItemStack());

        player.openInventory(inventory);

        inventoryHelper.click(event -> {
            event.setCancelled(true);

            ItemStack itemStack = event.getCurrentItem();
            if(itemStack == null)return;
            ItemMeta itemMeta = itemStack.getItemMeta();
            if(itemMeta == null)return;
            if(!itemMeta.getDisplayName().contains("Id"))return;

            String[] split = itemMeta.getDisplayName().split(":");
            int id = Integer.parseInt(split[1]);

            this.backupService.findBackupById(id).ifPresent(backup -> {


                switch (event.getClick()){
                    case LEFT:{
                        Player playerTarget = Bukkit.getPlayerExact(targetNickName);
                        if(playerTarget == null){
                            playerTarget.closeInventory();
                            player.sendMessage(ChatUtilities.colored("&4Błąd: &cPodany gracz jest offline lub jest na innym sektorze!"));
                            return;
                        }
                        playerTarget.getInventory().setContents(ItemSerializer.encodeItem(backup.getSerializedInventory()));
                        playerTarget.getInventory().setArmorContents(ItemSerializer.encodeItem(backup.getSerializedArmorContent()));

                        player.closeInventory();
                        player.sendMessage(ChatUtilities.colored("&3&lBACKUP &8:: &fPomyślnie oddano zapis graczowi: &3" + playerTarget.getName()));
                        break;
                    }
                    case RIGHT:{
                        this.showItemsBackup(player, backup);
                        break;
                    }
                    case SHIFT_LEFT:{
                        player.closeInventory();
                        player.sendMessage(ChatUtilities.colored("&3&lBACKUP &8:: &fPomyślnie usunięto zapis o id: &b" + backup.getId() + "\n" +
                                "&fgracza: &b" +  backup.getNickName()));
                        new BackupSynchronizeInformationPacket(backup.getId(), new Gson().toJson(backup), UpdateType.REMOVE).sendToChannel("MASTER");
                        break;
                    }
                }
            });
        });
    }
    public void showItemsBackup(Player player, Backup backup){
        InventoryHelper inventoryHelper = new InventoryHelper(54, ChatUtilities.colored("&3&lBACKUP PODGLĄD"));
        Inventory inventory = inventoryHelper.getInventory();

        Integer[] slotsBlackGlass = new Integer[]{0, 4, 8, 9, 17, 49, 45, 44, 53, 36};
        Integer[] slotsDarkBlueGlass = new Integer[]{3, 5,48,50, 10, 16, 13};
        Integer[] slotsBlueGlass = new Integer[]{1, 2,6, 7, 47, 46, 51, 52};
        Integer[] slotsWhiteGlass = new Integer[]{18, 26, 27, 35};

        Arrays.stream(slotsBlackGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.BLACK_STAINED_GLASS_PANE, 1).setName(" ").toItemStack()));
        Arrays.stream(slotsDarkBlueGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.BLUE_STAINED_GLASS_PANE, 1 ).setName(" ").toItemStack()));
        Arrays.stream(slotsBlueGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1).setName(" ").toItemStack()));
        Arrays.stream(slotsWhiteGlass).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.WHITE_STAINED_GLASS_PANE, 1).setName(" ").toItemStack()));


        ItemStack[] itemStacks = ItemSerializer.encodeItem(backup.getSerializedArmorContent());
        if(itemStacks[0] != null && itemStacks[0].getType() != Material.AIR){
            inventory.setItem(11, itemStacks[0]);
        }
        if(itemStacks[1] != null && itemStacks[1].getType() != Material.AIR){
            inventory.setItem(12, itemStacks[1]);
        }
        if(itemStacks[2] != null && itemStacks[2].getType() != Material.AIR){
            inventory.setItem(14, itemStacks[2]);
        }
        if(itemStacks[3] != null && itemStacks[3].getType() != Material.AIR){
            inventory.setItem(15, itemStacks[3]);
        }
        for (ItemStack itemStack : ItemSerializer.encodeItem(backup.getSerializedInventory())) {
            if(itemStack != null && itemStack.getType() != Material.AIR){
                inventory.addItem(itemStack);
            }
        }

        ItemHelper barrierItem = new ItemHelper(Material.BARRIER).setName("&c&lPOWRÓT");
        inventory.setItem(49, barrierItem.toItemStack());

        inventoryHelper.click(event -> {
            event.setCancelled(true);

            if(event.getSlot() == 49){
                this.show(player, backup.getNickName());
            }
        });

        player.openInventory(inventory);
    }
}

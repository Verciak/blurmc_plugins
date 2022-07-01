package pl.pieszku.sectors.commands.admin;

import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.pieszku.api.objects.kit.Kit;
import org.pieszku.api.redis.packet.kit.sync.KitSynchronizeInformationPacket;
import org.pieszku.api.redis.packet.type.UpdateType;
import org.pieszku.api.sector.SectorService;
import org.pieszku.api.service.KitService;
import org.pieszku.api.type.GroupType;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.impl.Command;
import pl.pieszku.sectors.impl.CommandInfo;
import pl.pieszku.sectors.inventory.admin.AdminKitConfigurationInventory;
import pl.pieszku.sectors.serializer.ItemSerializer;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.util.Optional;

@CommandInfo(name = "akits", permission = GroupType.DEVELOPER)
public class AdminKitCommand extends Command {


    private final KitService kitService = BukkitMain.getInstance().getKitService();
    private final SectorService sectorService  = BukkitMain.getInstance().getSectorService();
    private final AdminKitConfigurationInventory adminKitConfigurationInventory = new AdminKitConfigurationInventory();

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        Player player = (Player) commandSender;

        if(args.length < 1){
            player.sendMessage(ChatUtilities.colored("&7Poprawne użycie: &b/akits create,edit <name>"));
            return;
        }
        switch (args[0]){
            case "create":{
                if(args.length < 5){
                    player.sendMessage(ChatUtilities.colored("&7Poprawne użycie: &b/akits create <name> <slot> <material> <inventoryName>"));
                    return;
                }

                String name = args[1];
                Optional<Kit> kitOptional = this.kitService.findKitByName(name);
                if(kitOptional.isPresent()){
                    player.sendMessage(ChatUtilities.colored("&4Błąd: &cZestaw: &4" + name + " &cjuż istnieje!!"));
                    return;
                }
                int inventorySlot = Integer.parseInt(args[2]);
                String material = args[3];
                String inventoryName = StringUtils.join(args, " ", 4, args.length);

                Kit kit = new Kit(name, inventoryName, inventorySlot, material);
                kit.setGlassItemSerialized(ItemSerializer.decodeItems(new ItemStack[]{}));
                kit.setItemSerialized(ItemSerializer.decodeItems(new ItemStack[]{}));

                player.sendTitle(ChatUtilities.colored("&3&lKIT"),
                        ChatUtilities.colored("&b&l♦ &fPrzejdż do konfiguracji&8(&b/akits edit " + name + "&8) &b&l♦"));
                player.sendMessage(ChatUtilities.colored("&3&lKIT &fPrzeprowadzam synchronizacje na inne sektory..."));
                this.synchronize(player, kit, UpdateType.CREATE);
                break;
            }
            case "edit":{
                if(args.length < 3){
                    player.sendMessage(ChatUtilities.colored("&7Poprawne użycie: &b/akits edit name <time,material,permission,glass,items>"));
                    return;
                }

                String name = args[1];
                Optional<Kit> kitOptional = this.kitService.findKitByName(name);
                if(!kitOptional.isPresent()){
                    player.sendMessage(ChatUtilities.colored("&4Błąd: &cZestaw: &4" + name + " &cnie istnieje!!"));
                    return;
                }
                switch (args[2]){
                    case "permission":{
                        if(args.length < 4){
                            player.sendMessage(ChatUtilities.colored("&7Poprawne użycie: &b/akits edit " + name + " permission <group>"));
                            return;
                        }
                        String groupName = args[3];
                        if(!GroupType.groupExists(groupName)){
                            player.sendMessage(ChatUtilities.colored("&4Błąd: &cPodana grupa nie istnieje!"));
                            return;
                        }
                        kitOptional.ifPresent(kit -> {
                            kit.setPermissionAccess(GroupType.valueOf(groupName));
                            player.sendTitle(ChatUtilities.colored("&3&lKIT"),
                                    ChatUtilities.colored("&b&l♦ &fTrwa przeprowadzanie konfiguracji dla&8(&b" + name + "&8) &b&l♦"));
                            player.sendMessage(ChatUtilities.colored("&3&lKIT &fPrzeprowadzam synchronizacje na inne sektory..."));
                            this.synchronize(player, kit, UpdateType.UPDATE);
                        });
                        break;
                    }
                    case "material":{
                        if(args.length < 4){
                            player.sendMessage(ChatUtilities.colored("&7Poprawne użycie: &b/akits edit " + name + " material <materialName>"));
                            return;
                        }
                        String inventoryMaterial = args[3];
                        kitOptional.ifPresent(kit -> {
                            kit.setInventoryMaterial(inventoryMaterial);
                            player.sendTitle(ChatUtilities.colored("&3&lKIT"),
                                    ChatUtilities.colored("&b&l♦ &fTrwa przeprowadzanie konfiguracji dla&8(&b" + name + "&8) &b&l♦"));
                            player.sendMessage(ChatUtilities.colored("&3&lKIT &fPrzeprowadzam synchronizacje na inne sektory..."));
                            this.synchronize(player, kit, UpdateType.UPDATE);
                        });
                        break;
                    }
                    case "time":{
                        if(args.length < 4){
                            player.sendMessage(ChatUtilities.colored("&7Poprawne użycie: &b/akits edit " + name + " time <minutes>"));
                            return;
                        }
                        int minutes = 0;
                        try {
                           minutes = Integer.parseInt(args[3]);
                        } catch (Exception e) {
                            player.sendMessage(ChatUtilities.colored("&4Błąd: &cMusisz podać liczbę np: &7(&f1&8, &f5&8, &f10&8, &f60&7)"));
                            return;
                        }
                        int finalMinutes = minutes;
                        kitOptional.ifPresent(kit -> {
                            kit.setPickupDelay(finalMinutes);
                            player.sendTitle(ChatUtilities.colored("&3&lKIT"),
                                    ChatUtilities.colored("&b&l♦ &fTrwa przeprowadzanie konfiguracji dla&8(&b" + name + "&8) &b&l♦"));
                            player.sendMessage(ChatUtilities.colored("&3&lKIT &fPrzeprowadzam synchronizacje na inne sektory..."));
                            this.synchronize(player, kit, UpdateType.UPDATE);
                        });
                        break;
                    }
                    case "glass": {
                        kitOptional.ifPresent(kit -> {
                            this.adminKitConfigurationInventory.showEditKitGlassItem(player, kit);
                        });
                        break;
                    }
                    case "items":{
                        kitOptional.ifPresent(kit -> {
                            this.adminKitConfigurationInventory.showEditKitItems(player, kit);
                        });
                        break;
                    }
                }
                break;
            }
        }
    }
    public void synchronize(Player player, Kit kit, UpdateType updateType){
        Bukkit.getScheduler().runTaskLaterAsynchronously(BukkitMain.getInstance(), () -> {
            new KitSynchronizeInformationPacket(kit.getKitName(), updateType, new Gson().toJson(kit)).sendToAllSectorsAndApp(this.sectorService);
            player.sendMessage(ChatUtilities.colored("&3&lKIT &fPrzeprowadzono pomyślnie synchronizacje zestawu: &b" + kit.getKitName()));
        }, 10L);
    }
}

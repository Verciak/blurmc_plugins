package pl.pieszku.sectors.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.pieszku.api.objects.user.UserHome;
import org.pieszku.api.serializer.LocationSerializer;
import org.pieszku.api.service.UserService;
import org.pieszku.api.type.GroupType;
import org.pieszku.api.type.TimeType;
import org.pieszku.api.utilities.DataUtilities;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.cache.BukkitCache;
import pl.pieszku.sectors.helper.InventoryHelper;
import pl.pieszku.sectors.helper.ItemHelper;
import pl.pieszku.sectors.impl.UserActionBarType;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.util.Arrays;

public class HomeInventory {

    private final UserService userService = BukkitMain.getInstance().getUserService();
    private final BukkitCache bukkitCache = BukkitMain.getInstance().getBukkitCache();

    public void show(Player player) {
        this.userService.findUserByNickName(player.getName()).ifPresent(user -> {

            InventoryHelper inventoryHelper = new InventoryHelper(27, ChatUtilities.colored("&8* &3Lista domow &8*"));
            Inventory inventory = inventoryHelper.getInventory();
            
            Integer[] glassBlueSlots = new Integer[]{0, 2, 4, 7, 17, 18, 19, 21, 23, 25};
            Integer[] glassDarkBlueSlots = new Integer[]{1, 3, 5, 6, 8, 9, 16, 20, 22, 24, 26};

            Arrays.stream(glassBlueSlots).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack()));
            Arrays.stream(glassDarkBlueSlots).forEach(slot -> inventory.setItem(slot, new ItemHelper(Material.BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack()));


            user.getHomeList().forEach(userHome -> {

                LocationSerializer location = userHome.getLocationSerializer();
                ItemHelper homeItem = new ItemHelper(Material.PLAYER_HEAD, 1, (short) 3)
                        .setOwnerUrl("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjU1NTM4MTIwOWJmOTg5ZThjYWFjM2FlOGQ2YjdlMTkzMzczZjE3MTgwODhmNWRiZjEyMmY3MWY1ZWFmOTBmMCJ9fX0=")
                        .setName("&3&lDOM: &b&l" + userHome.getName())
                        .addLore("")
                        .addLore((location.getX() == 0 && location.getY() == 80 && location.getZ() == 0) ? "&cMusisz ustawić swój dom" : "&aTwój dom jest ustawiony")
                        .addLore("")
                        .addLore("&fX&8:&b " + location.getX())
                        .addLore("&fY&8:&b " + location.getY())
                        .addLore("&fZ&8:&b " + location.getZ())
                        .addLore("")
                        .addLore("&fDostęp od rangi: " + (userHome.getGroupType().equals(GroupType.PLAYER) ? "&aGRACZ" : userHome.getGroupType().getPrefix()))
                        .addLore((!GroupType.hasPermission(user, userHome.getGroupType()) ? "&cNie posiadasz dostępu" : "&aPosiadasz dostęp"))
                        .addLore("")
                        .addLore("&3Lewy &8- &fAby się przeteleportować")
                        .addLore("&3Prawy &8- &fAby ustawić dom")
                        .addLore("");

                inventory.setItem(userHome.getInventorySlot(), homeItem.toItemStack());
            });
            player.openInventory(inventory);

            inventoryHelper.click(event -> {
                event.setCancelled(true);

                UserHome userHome = user.findHomeByInventorySlot(event.getSlot());
                if(userHome == null)return;


                if(!GroupType.hasPermission(user, userHome.getGroupType())){
                    player.closeInventory();
                    player.sendTitle(ChatUtilities.colored("&5&lENDERCHEST"), ChatUtilities.colored("&fTen enderchest wymaga rangi: " + userHome.getGroupType().getPrefix()));
                    return;
                }

                if(event.getClick().equals(ClickType.LEFT)){
                    if(!userHome.hasSet()){
                        player.closeInventory();
                        player.sendTitle(ChatUtilities.colored("&8[&3DOM&8]"),
                                ChatUtilities.colored("&f&l• &bMusisz najpierw ustawić swój dom &f&l•"));
                        return;
                    }
                    this.teleport(player, userHome.getLocationSerializer());
                    return;
                }
                if(event.getClick().equals(ClickType.RIGHT)){
                    userHome.setLocationSerializer(new LocationSerializer(player.getLocation().getWorld().getName(), player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ()));
                    player.closeInventory();
                    player.sendTitle(ChatUtilities.colored("&8[&bDOM&8]"),
                            ChatUtilities.colored("&a&l• &aPomyślnie ustawiono dom: &2" + userHome.getName() + " &a&l•"));
                    player.playSound(player.getLocation(), Sound.MUSIC_CREDITS, 10f, 10f);
                    return;
                }
            });
        });
    }
    public void teleport(Player player, LocationSerializer locationSerializer){
        new BukkitRunnable(){

            long time = System.currentTimeMillis() + TimeType.SECOND.getTime(10);
            Location location = player.getLocation();

            @Override
            public void run() {
                bukkitCache.findBukkitUserByNickName(player.getName()).ifPresent(bukkitUser -> {
                    if (time <= System.currentTimeMillis()) {
                        player.teleport(new Location(Bukkit.getWorld(locationSerializer.getWorld()), locationSerializer.getX(), locationSerializer.getY(), locationSerializer.getZ()));
                        this.cancel();
                        bukkitUser.removeActionBar(UserActionBarType.TELEPORT);
                        return;
                    }
                    if (move(location, player.getLocation())) {
                        player.sendTitle(ChatUtilities.colored("&3&lTELEPORTACJA"),
                                ChatUtilities.colored("&fBłąd podczas teleportacji nie można się poruszać"));
                        this.cancel();
                        bukkitUser.removeActionBar(UserActionBarType.TELEPORT);
                        return;
                    }
                    bukkitUser.updateActionBar(UserActionBarType.TELEPORT, " &3&lTELEPORTACJA&8: &fZostaniesz przeteleportowany za: &b" + DataUtilities.getTimeToString(time) + " ");
                });
            }
        }.runTaskTimer(BukkitMain.getInstance(), 0, 5);
    }
    public static boolean move(Location l, Location x) {
        return l.getBlockX() != x.getBlockX() || l.getBlockY() != x.getBlockY() || l.getBlockZ() != x.getBlockZ();
    }
}
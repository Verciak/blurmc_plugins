package pl.pieszku.sectors.transfer;

import com.google.gson.Gson;
import net.minecraft.server.v1_16_R3.EnumItemSlot;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.pieszku.api.objects.user.User;
import org.pieszku.api.redis.packet.type.UpdateType;
import org.pieszku.api.redis.packet.user.UserTransferInformationPacket;
import org.pieszku.api.redis.packet.user.sync.UserInformationSynchronizePacket;
import org.pieszku.api.sector.Sector;
import org.pieszku.api.sector.SectorService;
import org.pieszku.api.serializer.LocationSerializer;
import org.pieszku.api.service.UserService;
import org.pieszku.api.type.ArmorType;
import org.pieszku.api.type.TimeType;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.handler.events.SectorChangeEvent;
import pl.pieszku.sectors.packet.PacketEquipment;
import pl.pieszku.sectors.serializer.ItemSerializer;
import pl.pieszku.sectors.utilities.ChatUtilities;

public class TransferPlayerSectorUtilities {


    private final UserService userService = BukkitMain.getInstance().getUserService();
    private final SectorService sectorService = BukkitMain.getInstance().getSectorService();

    public void addTransferPlayer(Player player, Sector sector, Location location){
        SectorChangeEvent sectorChangeEvent = new SectorChangeEvent(player, sector, location);
        if(!sector.isOnline()){
            if(sector.isSpawn()){
                Sector findSector = this.sectorService.findSectorSpawn();
                if(findSector == null){
                    Location locationKnockBack = new Location(player.getWorld(), (sector.getLocationMinimum().getX() + sector.getLocationMaximum().getX()) / 2, 80, (sector.getLocationMinimum().getZ() + sector.getLocationMaximum().getZ()) / 2);
                    this.knockBackPlayer(player, locationKnockBack);
                    player.sendMessage(ChatUtilities.colored("&4Blad: &cWszystkie sektory &4spawn &csa aktualnie offline!"));
                    return;
                }
                Bukkit.getPluginManager().callEvent(sectorChangeEvent);
                return;
            }
            Location locationKnockBack = new Location(player.getWorld(), (sector.getLocationMinimum().getX() + sector.getLocationMaximum().getX()) / 2, 80, (sector.getLocationMinimum().getZ() + sector.getLocationMaximum().getZ()) / 2);
            this.knockBackPlayer(player, locationKnockBack);
            player.sendMessage(ChatUtilities.colored("&4&lSEKTOR&8: &cAktualnie sektor: &4" + sector.getName() + " &cjest offline!"));
            return;
        }
        Bukkit.getPluginManager().callEvent(sectorChangeEvent);
    }
    public void transferPlayer(Player player, Sector sector, Location location) {
        if (BukkitMain.getInstance().getCurrentSector().get().equals(sector)) return;

        this.userService.findUserByNickName(player.getName()).ifPresent(user -> {
            if (user.getChangeSector() >= System.currentTimeMillis()) return;


            player.setSneaking(false);
            user.setLastArmorType(user.getArmorType());
            user.setArmorType(ArmorType.CLEAR);
            PacketEquipment.sendEquipment(player, player.getEntityId(), EnumItemSlot.FEET, new ItemStack(Material.AIR));
            PacketEquipment.sendEquipment(player, player.getEntityId(), EnumItemSlot.LEGS, new ItemStack(Material.AIR));
            PacketEquipment.sendEquipment(player, player.getEntityId(), EnumItemSlot.CHEST, new ItemStack(Material.AIR));
            PacketEquipment.sendEquipment(player, player.getEntityId(), EnumItemSlot.HEAD, new ItemStack(Material.AIR));

            user.setChangeSector(System.currentTimeMillis() + TimeType.SECOND.getTime(3));
            user.setExp(player.getExp());
            user.setFlying(player.getAllowFlight());
            user.setFoodLevel(player.getFoodLevel());
            user.setSpeedLevel(player.getFlySpeed());
            user.setNowSector(sector.getName());
            user.setLevel(player.getLevel());
            user.setSerializedInventory(ItemSerializer.decodeItems(player.getInventory().getContents()));
            user.setSerializedEnderChest(ItemSerializer.decodeItems(player.getEnderChest().getContents()));
            user.setSerializedArmorInventory(ItemSerializer.decodeItems(player.getInventory().getArmorContents()));
            user.setLocation(new LocationSerializer(location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), location.getPitch(), location.getYaw()));
            user.setGameMode(player.getGameMode().name());
            user.setHeldItemSlot(player.getInventory().getHeldItemSlot());
            user.setHealth(player.getHealth());
            new UserTransferInformationPacket(player.getName(), new Gson().toJson(user)).sendToChannel(sector.getName());
            new UserInformationSynchronizePacket(player.getName(), new Gson().toJson(user), UpdateType.UPDATE).sendToChannel("MASTER");

            Bukkit.getScheduler().runTaskLaterAsynchronously(BukkitMain.getInstance(), () -> {
                BukkitMain.getInstance().connect(player, sector.getName());
            }, 5L);
        });
    }
    public void loadTransferPlayer(Player player, User user) {
        user.setInteractSector(true);
        user.setNowSector(BukkitMain.getInstance().getSectorName());
        new UserInformationSynchronizePacket(player.getName(), new Gson().toJson(user), UpdateType.UPDATE).sendToChannel("MASTER");
        if (user.getLocation() == null) return;
        Location location = new Location(Bukkit.getWorld(user.getLocation().getWorld()), user.getLocation().getX(), user.getLocation().getY(), user.getLocation().getZ(), user.getLocation().getYaw(), user.getLocation().getPitch());
        player.teleport(location);
        player.setHealth(user.getHealth());
        player.setFoodLevel((int) user.getFoodLevel());
        player.setAllowFlight(user.isFlying());
        player.setGameMode(GameMode.valueOf(user.getGameMode()));
        player.setLevel(user.getLevel());
        player.setExp(user.getExp());


        player.getInventory().setContents(ItemSerializer.encodeItem(user.getSerializedInventory()));
        player.getInventory().setArmorContents(ItemSerializer.encodeItem(user.getSerializedArmorInventory()));
        player.getEnderChest().setContents(ItemSerializer.encodeItem(user.getSerializedEnderChest()));
    }
    public static void saveUserInSector(Player player, User user, Location location, Sector sector){
        user.setChangeSector(0);
        user.setExp(player.getExp());
        user.setFlying(player.getAllowFlight());
        user.setFoodLevel(player.getFoodLevel());
        user.setSpeedLevel(player.getFlySpeed());
        user.setNowSector(sector.getName());
        user.setLevel(player.getLevel());
        user.setSerializedInventory(ItemSerializer.decodeItems(player.getInventory().getContents()));
        user.setSerializedEnderChest(ItemSerializer.decodeItems(player.getEnderChest().getContents()));
        user.setSerializedArmorInventory(ItemSerializer.decodeItems(player.getInventory().getArmorContents()));
        user.setLocation(new LocationSerializer(location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), location.getPitch(), location.getYaw()));
        user.setGameMode(player.getGameMode().name());
        user.setHeldItemSlot(player.getInventory().getHeldItemSlot());
        user.setHealth(player.getHealth());
        user.setNowSector(BukkitMain.getInstance().getSectorName());
        new UserInformationSynchronizePacket(player.getName(), new Gson().toJson(user), UpdateType.UPDATE).sendToChannel("MASTER");
    }
    public void knockBackPlayer(Player player, Location location) {
        Location locationVector = player.getLocation().subtract(location);
        double distance = player.getLocation().distance(location);
        if ((1.0 / distance <= 0)) return;
        if (distance <= 0) return;
        player.setVelocity(locationVector.toVector().add(new Vector(0, 0.10, 0)).multiply(1.20 / distance));
    }
}

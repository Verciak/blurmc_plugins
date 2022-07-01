package pl.pieszku.sectors.holder;

import net.minecraft.server.v1_16_R3.PacketPlayOutWorldBorder;
import net.minecraft.server.v1_16_R3.WorldBorder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import pl.pieszku.sectors.BukkitMain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BorderHolder {

    public static void update(Player player) {
        Map<Player, List<Location>> particles = new HashMap<>();
       BukkitMain.getInstance().getCurrentSector().ifPresent(sector -> {

        final List<Location> locations = new ArrayList<>();
        final Location sectorCenter = new Location(player.getWorld(), (sector.getLocationMinimum().getX() + sector.getLocationMaximum().getX()) / 2, 80, (sector.getLocationMinimum().getZ() + sector.getLocationMaximum().getZ()) / 2);
        locations.add(new Location(Bukkit.getWorld(sector.getLocationMinimum().getWorld()), sector.getLocationMinimum().getX(), 64, sector.getLocationMinimum().getZ()));
        locations.add(new Location(Bukkit.getWorld(sector.getLocationMinimum().getWorld()), sector.getLocationMaximum().getX(), 64, sector.getLocationMinimum().getZ()));
        locations.add(new Location(Bukkit.getWorld(sector.getLocationMinimum().getWorld()), sector.getLocationMinimum().getX(), 64, sector.getLocationMaximum().getZ()));
        locations.add(new Location(Bukkit.getWorld(sector.getLocationMinimum().getWorld()), sector.getLocationMaximum().getX(), 64, sector.getLocationMaximum().getZ()));
        double distance = Double.MAX_VALUE;
        final int increase = 99999999;
        Location side = null;
        for (final Location loc : locations) {
            if (!loc.getWorld().equals(player.getWorld())) {
                continue;
            }
            double newDistance = loc.distance(player.getLocation());
            if (newDistance < distance) {
                distance = newDistance;
                side = loc;
            }
        }
        if (side == null) {
            return;
        }
        double offset = 0.5;
        if (side.getX() >= 3000) {
            side.setX(side.getX() - offset);
        } else if (side.getX() <= 3000 * -1) {
            side.setX(side.getX() + offset);
        }
        if (side.getZ() >= 3000) {
            side.setZ(side.getZ() - offset);
        } else if (side.getZ() <= 3000 * -1) {
            side.setZ(side.getZ() + offset);
        }
        final Location borderLoc = side;
        if (side.getZ() < sectorCenter.getZ()) {
            side.setZ(side.getZ() - offset);
            borderLoc.setZ(side.getZ() + increase - offset);
        } else {
            side.setZ(side.getZ() + offset);
            borderLoc.setZ(side.getZ() - increase + offset);
        }
        if (side.getX() < sectorCenter.getX()) {
            side.setX(side.getX() - offset);
            borderLoc.setX(side.getX() + increase - offset);
        } else {
            side.setX(side.getX() + offset);
            borderLoc.setX(side.getX() - increase + offset);
        }
        WorldBorder worldBorder = new WorldBorder();
        worldBorder.world = ((CraftWorld) player.getWorld()).getHandle();
        worldBorder.setSize(increase * 2);
        worldBorder.transitionSizeBetween(worldBorder.getSize(), worldBorder.getSize() - 0.5, Long.MAX_VALUE);
        worldBorder.setCenter(borderLoc.getX(), borderLoc.getZ());
        PacketPlayOutWorldBorder packet = new PacketPlayOutWorldBorder(worldBorder, PacketPlayOutWorldBorder.EnumWorldBorderAction.INITIALIZE);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
       });
    }
}
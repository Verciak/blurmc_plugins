package pl.pieszku.sectors.packet;

import com.mojang.datafixers.util.Pair;
import net.minecraft.server.v1_16_R3.EnumItemSlot;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityEquipment;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PacketEquipment {

    public static void sendEquipment(Player player, int id, EnumItemSlot enumItemSlot, ItemStack item) {
        try {
            List<Pair<EnumItemSlot, net.minecraft.server.v1_16_R3.ItemStack>> pairList = new ArrayList<>();
            pairList.add(new Pair<>(enumItemSlot, CraftItemStack.asNMSCopy(item)));
            PacketPlayOutEntityEquipment packetPlayOutEntityEquipment = new PacketPlayOutEntityEquipment(id, pairList);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutEntityEquipment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
package pl.pieszku.sectors.runnable;

import javafx.util.Pair;
import net.minecraft.server.v1_16_R3.EnumItemSlot;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.pieszku.api.service.UserService;
import org.pieszku.api.type.ArmorType;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.packet.PacketEquipment;
import pl.pieszku.sectors.utilities.ColorUtils;

import java.util.Arrays;
import java.util.List;

public class DiscoArmorRunnable implements Runnable {


    private final UserService userService = BukkitMain.getInstance().getUserService();

    private List<Pair<EnumItemSlot, ItemStack>> enumItemSlotList = Arrays.asList(
            new Pair<>(EnumItemSlot.HEAD, new ItemStack(Material.LEATHER_HELMET)),
            new Pair<>(EnumItemSlot.CHEST, new ItemStack(Material.LEATHER_CHESTPLATE)),
            new Pair<>(EnumItemSlot.LEGS, new ItemStack(Material.LEATHER_LEGGINGS)),
            new Pair<>(EnumItemSlot.FEET, new ItemStack(Material.LEATHER_BOOTS)));

    public void start() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(BukkitMain.getInstance(), this, 1L, 20L);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (Player playerWorld : player.getWorld().getPlayers()) {


                this.userService.findUserByNickName(player.getName()).ifPresent(user -> {
                    ArmorType armorType = user.getArmorType();
                    if (!user.isInteractSector()) return;

                    switch (armorType) {
                        case RANDOM: {
                            Color color = ColorUtils.randomColor();
                            this.enumItemSlotList.forEach(pair -> {

                                EnumItemSlot enumItemSlot = pair.getKey();
                                ItemStack itemStack = pair.getValue();

                                ItemStack item = itemStack;
                                LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
                                meta.setColor(color);
                                item.setItemMeta(meta);

                                if (player.isSneaking()) {
                                    PacketEquipment.sendEquipment(playerWorld, player.getEntityId(), enumItemSlot, item);
                                }
                                if (!player.equals(playerWorld)) {
                                    PacketEquipment.sendEquipment(playerWorld, player.getEntityId(), enumItemSlot, item);
                                }
                            });
                            break;
                        }
                        case ULTRA: {
                            this.enumItemSlotList.forEach(pair -> {
                                EnumItemSlot enumItemSlot = pair.getKey();
                                ItemStack itemStack = pair.getValue();
                                ItemStack item = itemStack;
                                LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
                                meta.setColor(ColorUtils.randomColor());
                                item.setItemMeta(meta);

                                if (player.isSneaking()) {
                                    PacketEquipment.sendEquipment(playerWorld, player.getEntityId(), enumItemSlot, item);
                                }
                                if (!player.equals(playerWorld)) {
                                    PacketEquipment.sendEquipment(playerWorld, player.getEntityId(), enumItemSlot, item);
                                }
                            });
                            break;
                        }
                    }
                });
            }
        }
    }
}

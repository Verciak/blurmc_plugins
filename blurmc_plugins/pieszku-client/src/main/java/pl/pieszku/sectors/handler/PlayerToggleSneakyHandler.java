package pl.pieszku.sectors.handler;

import net.minecraft.server.v1_16_R3.EnumItemSlot;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.pieszku.api.service.UserService;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.packet.PacketEquipment;

public class PlayerToggleSneakyHandler implements Listener {


    private final UserService userService = BukkitMain.getInstance().getUserService();

    @EventHandler
    public void onPlayerToggleSneaky(PlayerToggleSneakEvent event){

        Player player = event.getPlayer();

        this.userService.findUserByNickName(player.getName()).ifPresent(user -> {

            switch (user.getArmorType()){
                case RANDOM:
                case ULTRA:{
                    for(Player playerOnline : Bukkit.getOnlinePlayers()) {
                        PacketEquipment.sendEquipment(playerOnline, player.getEntityId(), EnumItemSlot.FEET, new ItemStack(Material.AIR));
                        PacketEquipment.sendEquipment(playerOnline, player.getEntityId(), EnumItemSlot.LEGS, new ItemStack(Material.AIR));
                        PacketEquipment.sendEquipment(playerOnline, player.getEntityId(), EnumItemSlot.CHEST, new ItemStack(Material.AIR));
                        PacketEquipment.sendEquipment(playerOnline, player.getEntityId(), EnumItemSlot.HEAD, new ItemStack(Material.AIR));
                    }
                }
                player.updateInventory();
                break;
            }
        });
    }
}

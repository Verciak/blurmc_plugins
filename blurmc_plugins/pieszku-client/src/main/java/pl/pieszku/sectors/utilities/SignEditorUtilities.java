package pl.pieszku.sectors.utilities;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import net.minecraft.server.v1_16_R3.PacketPlayOutOpenSignEditor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_16_R3.block.CraftSign;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.pieszku.sectors.BukkitMain;

public class SignEditorUtilities {

    public void openSignEditorToPlayer(Player player, String line0, String line1, String line2, String line3) {
        Location newSign = player.getLocation().add(0.0, 100.0, 0.0);
        Location fixnewSign = player.getLocation().add(0.0, 99.0, 0.0);
        fixnewSign.getBlock().setType(Material.BEDROCK);
        newSign.getBlock().setType(Material.OAK_SIGN);
        Block sign = newSign.getBlock();
        BlockState signState = sign.getState();
        CraftSign signBlock = (CraftSign) signState;
        signBlock.setEditable(true);
        signBlock.setColor(DyeColor.LIGHT_BLUE);
        signBlock.setLine(0, line0);
        signBlock.setLine(1, line1);
        signBlock.setLine(2, line2);
        signBlock.setLine(3, line3);
        signBlock.update();

        new BukkitRunnable() {
            @Override
            public void run() {
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutOpenSignEditor(signBlock.getBlock().getPosition()));
            }
        }.runTaskLaterAsynchronously(BukkitMain.getInstance(), 3L);
    }
    public void listen() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(BukkitMain.getInstance(), PacketType.Play.Client.UPDATE_SIGN) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();

                String[] lines = event.getPacket().getStringArrays().getValues().get(0);

                player.sendMessage("elo ");

                if(lines[2].equalsIgnoreCase("Podaj kwote")) {
                    player.sendMessage("SIEMA");
                }
            }
        });
    }
}

package pl.pieszku.sectors.commands.player;

import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.pieszku.api.type.GroupType;
import pl.pieszku.sectors.impl.Command;
import pl.pieszku.sectors.impl.CommandInfo;
import pl.pieszku.sectors.utilities.ChatUtilities;

@CommandInfo(name = "repairall", permission = GroupType.SVIP)
public class RepairAllCommand extends Command {

    @Override
    public void execute(CommandSender commandSender,  String[] strings) {
        Player p = (Player) commandSender;
        repairAll(p);
        p.sendMessage(ChatUtilities.colored("&3&lNaprawa wszystkiego: &7Naprawiono wszystkie przedmioty!"));
        p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 2f, 2f);
    }
    private int repairAll(final Player player) {
        int repaired = 0;
        final PlayerInventory inv = player.getInventory();
        ItemStack[] items = inv.getContents();
        for (int i = 0; i < items.length; ++i) {
            final ItemStack item = items[i];
            if (item != null) {
                item.setDurability((short)0);
                ++repaired;
            }
        }
        items = inv.getArmorContents();
        for (int i = 0; i < items.length; ++i) {
            final ItemStack item = items[i];
            if (item != null) {
                item.setDurability((short)0);
                ++repaired;
            }
        }
        return repaired;
    }
}

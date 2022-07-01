package pl.pieszku.sectors.commands.player;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.pieszku.api.type.GroupType;
import pl.pieszku.sectors.impl.Command;
import pl.pieszku.sectors.impl.CommandInfo;
import pl.pieszku.sectors.utilities.ChatUtilities;

@CommandInfo(name = "repair", permission = GroupType.SVIP)
public class RepairCommand extends Command {

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        Player p = (Player) commandSender;
        ItemStack is = p.getItemInHand();
        if (is.getType().isBlock() || is.getType() == Material.AIR || is.getType() == Material.GOLDEN_APPLE) {
            p.sendMessage(ChatUtilities.colored("&4Blad: &cTego przedmiotu nie mozesz naprawic!"));
            return;
        }
        if (is.getDurability() == 1) {
            p.sendMessage(ChatUtilities.colored("&4Blad: &cTen przedmiot jest naprawiony"));
            return;
        }
        is.setDurability((short)0);

        p.sendMessage(ChatUtilities.colored("&3&lNAPRAWA: &8>> &fNaprawiles przedmiot: &b" + is.getType()));

        return;
    }
}

package pl.pieszku.sectors.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pieszku.api.type.GroupType;
import pl.pieszku.sectors.impl.Command;
import pl.pieszku.sectors.impl.CommandInfo;
import pl.pieszku.sectors.inventory.DiscoArmorInventories;

@CommandInfo(name = "disco", aliases = {"discozbroja"}, permission = GroupType.VIP)
public class DiscoArmorCommand extends Command {

    private final DiscoArmorInventories discoArmorInventories = new DiscoArmorInventories();

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        Player player = (Player) commandSender;
        this.discoArmorInventories.open(player);
    }
}

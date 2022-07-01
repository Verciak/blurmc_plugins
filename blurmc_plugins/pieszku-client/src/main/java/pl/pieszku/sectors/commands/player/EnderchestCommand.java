package pl.pieszku.sectors.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.pieszku.sectors.impl.Command;
import pl.pieszku.sectors.impl.CommandInfo;
import pl.pieszku.sectors.inventory.EnderchestInventory;

@CommandInfo(name = "ec", aliases = {"enderchest"})
public class EnderchestCommand extends Command {

    private final EnderchestInventory enderchestInventory = new EnderchestInventory();

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        Player player = (Player) commandSender;
        this.enderchestInventory.show(player);
    }
}

package pl.pieszku.sectors.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.pieszku.sectors.impl.Command;
import pl.pieszku.sectors.impl.CommandInfo;
import pl.pieszku.sectors.inventory.DropInventory;

@CommandInfo(name = "drop")
public class DropCommand extends Command {

    private final DropInventory dropInventory = new DropInventory();

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        Player player = (Player) commandSender;
        this.dropInventory.init();
        this.dropInventory.show(player);
    }
}

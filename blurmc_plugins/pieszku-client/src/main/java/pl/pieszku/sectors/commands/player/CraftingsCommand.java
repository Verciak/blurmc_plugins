package pl.pieszku.sectors.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.pieszku.sectors.impl.Command;
import pl.pieszku.sectors.impl.CommandInfo;
import pl.pieszku.sectors.inventory.CraftingInventories;

@CommandInfo(name = "crafting", aliases = {"craftingi"})
public class CraftingsCommand extends Command {

    private final CraftingInventories craftingInventories = new CraftingInventories();

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        Player player = (Player) commandSender;
        this.craftingInventories.showStonework(player);
    }
}

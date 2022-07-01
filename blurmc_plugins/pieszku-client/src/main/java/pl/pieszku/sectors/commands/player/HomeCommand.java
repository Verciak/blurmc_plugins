package pl.pieszku.sectors.commands.player;


import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.pieszku.sectors.impl.Command;
import pl.pieszku.sectors.impl.CommandInfo;
import pl.pieszku.sectors.inventory.HomeInventory;

@CommandInfo(name = "home", aliases = {"dom"})
public class HomeCommand extends Command {

    private final HomeInventory homeInventory = new HomeInventory();

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        Player player = (Player) commandSender;
        this.homeInventory.show(player);
    }
}

package pl.pieszku.sectors.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.pieszku.sectors.impl.Command;
import pl.pieszku.sectors.impl.CommandInfo;
import pl.pieszku.sectors.inventory.ShopInventory;

@CommandInfo(name = "sklep", aliases = {"shop"})
public class ShopCommand extends Command {


    @Override
    public void execute(CommandSender commandSender, String[] args) {
        Player player = (Player) commandSender;
        ShopInventory shopInventory = new ShopInventory();
        shopInventory.show(player);
    }
}

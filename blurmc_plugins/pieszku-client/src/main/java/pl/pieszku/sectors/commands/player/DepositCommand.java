package pl.pieszku.sectors.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.pieszku.sectors.impl.Command;
import pl.pieszku.sectors.impl.CommandInfo;
import pl.pieszku.sectors.inventory.DepositInventory;

@CommandInfo(name = "schowek", aliases = {"deposit", "kieszen", "depozyt"})
public class DepositCommand extends Command {


    private final DepositInventory depositInventory = new DepositInventory();

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        Player player = (Player) commandSender;
        this.depositInventory.show(player);
    }
}

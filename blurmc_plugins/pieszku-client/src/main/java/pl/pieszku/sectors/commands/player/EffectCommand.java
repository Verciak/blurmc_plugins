package pl.pieszku.sectors.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.pieszku.sectors.impl.Command;
import pl.pieszku.sectors.impl.CommandInfo;
import pl.pieszku.sectors.inventory.EffectInventory;

@CommandInfo(name = "efekty", aliases = {"efekt"})
public class EffectCommand extends Command {


    private final EffectInventory effectInventory = new EffectInventory();

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        Player player = (Player) commandSender;
        this.effectInventory.show(player);
    }
}

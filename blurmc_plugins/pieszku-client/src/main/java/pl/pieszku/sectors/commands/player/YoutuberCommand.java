package pl.pieszku.sectors.commands.player;


import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.pieszku.sectors.impl.Command;
import pl.pieszku.sectors.impl.CommandInfo;
import pl.pieszku.sectors.inventory.HelpInventories;

@CommandInfo(name = "youtuber", aliases = {"yt"})
public class YoutuberCommand extends Command {

    private final HelpInventories helpInventories = new HelpInventories();

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        Player player = (Player) commandSender;
        this.helpInventories.show(player);
    }
}

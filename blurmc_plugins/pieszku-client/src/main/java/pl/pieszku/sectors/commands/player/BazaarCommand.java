package pl.pieszku.sectors.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.cache.BazaarGUICache;
import pl.pieszku.sectors.impl.Command;
import pl.pieszku.sectors.impl.CommandInfo;
import pl.pieszku.sectors.inventory.BazaarInventory;
import pl.pieszku.sectors.utilities.ChatUtilities;

@CommandInfo(name = "bazar", aliases = {"rynek", "bazary"})
public class BazaarCommand extends Command {


    private final BazaarInventory bazaarInventory = new BazaarInventory();
    private final BazaarGUICache bazaarGUICache = BukkitMain.getInstance().getBazaarGUICache();

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        Player player = (Player) commandSender;
        player.sendMessage(ChatUtilities.colored("&4Błąd: &cTa funkcja chwilowo wyłączona!"));
       // this.bazaarInventory.init(player, 1, 1);
    }
}

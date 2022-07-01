package pl.pieszku.sectors.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.impl.Command;
import pl.pieszku.sectors.impl.CommandInfo;
import pl.pieszku.sectors.inventory.ChannelSectorInventory;
import pl.pieszku.sectors.utilities.ChatUtilities;

@CommandInfo(name = "ch", usage = "/channel", aliases = {"channel"})
public class ChannelSectorCommand extends Command {


    private final ChannelSectorInventory channelSectorInventory = new ChannelSectorInventory();


    @Override
    public void execute(CommandSender commandSender, String[] args) {
        Player player = (Player) commandSender;


        if(!BukkitMain.getInstance().getCurrentSector().isPresent()){
            player.sendMessage(ChatUtilities.colored("&4Błąd: &cNie można wczytać twojego sektora!"));
            return;
        }
        if(!BukkitMain.getInstance().getCurrentSector().get().isSpawn()){
            player.sendMessage(ChatUtilities.colored("&4Błąd: &cPodana komenda działa tylko na sektorze spawn!"));
            return;
        }

        player.openInventory(channelSectorInventory.show(player));
    }
}

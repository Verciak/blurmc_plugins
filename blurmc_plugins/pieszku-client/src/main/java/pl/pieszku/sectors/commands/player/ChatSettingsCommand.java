package pl.pieszku.sectors.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.pieszku.sectors.impl.Command;
import pl.pieszku.sectors.impl.CommandInfo;
import pl.pieszku.sectors.inventory.ChatSettingsInventory;

@CommandInfo(name = "cc", aliases = {"ustawienia"})
public class ChatSettingsCommand extends Command {

    private final ChatSettingsInventory chatSettingsInventory = new ChatSettingsInventory();

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        Player player = (Player) commandSender;
        this.chatSettingsInventory.show(player);
    }
}

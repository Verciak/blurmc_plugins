package pl.pieszku.sectors.commands.guild;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.pieszku.sectors.inventory.guild.GuildItemsInventory;

import java.util.ArrayList;
import java.util.List;

public class ItemsGuildCommand extends GuildSubCommand{


    private final GuildItemsInventory guildItemsInventory = new GuildItemsInventory();

    public ItemsGuildCommand() {
        super("itemy", "", "", "", "przedmioty");
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        this.guildItemsInventory.show(player);
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return new ArrayList<>();
    }
}

package pl.pieszku.sectors.commands.guild;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pieszku.api.service.GuildService;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.inventory.guild.GuildPanelInventory;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.util.ArrayList;
import java.util.List;

public class PanelGuildCommand extends GuildSubCommand{


    private final GuildService guildService = BukkitMain.getInstance().getGuildService();
    private final GuildPanelInventory guildPanelInventory = new GuildPanelInventory();

    public PanelGuildCommand() {
        super("panel", "", "", "");
    }

    @Override
    public boolean onCommand(Player player, String[] args) {

        if(!this.guildService.findGuildByMember(player.getName()).isPresent()){
            player.sendMessage(ChatUtilities.colored("&4Błąd: &cNie posiadasz gildii!"));
            return false;
        }
        this.guildService.findGuildByMember(player.getName()).ifPresent(guild -> {
            this.guildPanelInventory.show(player, guild);
        });

        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return new ArrayList<>();
    }
}

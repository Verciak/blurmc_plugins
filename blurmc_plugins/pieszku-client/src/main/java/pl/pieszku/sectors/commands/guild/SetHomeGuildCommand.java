package pl.pieszku.sectors.commands.guild;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pieszku.api.objects.guild.impl.Guild;
import org.pieszku.api.serializer.LocationSerializer;
import org.pieszku.api.service.GuildService;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SetHomeGuildCommand extends GuildSubCommand{

    private final GuildService guildService = BukkitMain.getInstance().getGuildService();

    public SetHomeGuildCommand() {
        super("sethome", "", "", "", "ustawdom", "ustawbaze");
    }
    @Override
    public boolean onCommand(Player player, String[] args) {
        if (!this.guildService.findGuildByMember(player.getName()).isPresent()) {
            player.sendMessage(ChatUtilities.colored("&4Błąd: &cNie posiadasz gildii!"));
            return false;
        }
        Optional<Guild> optionalGuild = this.guildService.findGuildByLocation(player.getLocation().getWorld().getName(), player.getLocation().getBlockX(), player.getLocation().getBlockZ());

        if(!optionalGuild.isPresent()){
            player.sendMessage(ChatUtilities.colored("&4Błąd: &cMusisz być na ternie gildii!"));
            return false;
        }

        optionalGuild.ifPresent(guild -> {
            if(!guild.getMembers().contains(player.getName())){
                player.sendMessage(ChatUtilities.colored("&4Błąd: &cJesteś na ternie wrogiej gildii!"));
                return;
            }
            if(!guild.hasMaster(player.getName())){
                player.sendMessage(ChatUtilities.colored("&4Błąd: &cMusisz być mistrzem!"));
                return;
            }
            guild.setLocationHome(new LocationSerializer(player.getLocation().getWorld().getName(), player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ()));
            player.sendTitle(ChatUtilities.colored("&b&lGILDIA"), ChatUtilities.colored("&fPomyślnie &austawiono &fnowy dom"));
        });

        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return new ArrayList<>();
    }
}

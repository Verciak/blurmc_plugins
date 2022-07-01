package pl.pieszku.sectors.commands.guild;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pieszku.api.service.GuildService;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.util.ArrayList;
import java.util.List;

public class OwnerGuildCommand extends GuildSubCommand{


    private final GuildService guildService = BukkitMain.getInstance().getGuildService();

    public OwnerGuildCommand() {
        super("zalozyciel", "", "/g zalozyciel <nick>", "");
    }

    @Override
    public boolean onCommand(Player player, String[] args) {

        this.guildService.findGuildByMember(player.getName()).ifPresent(guild -> {

            if(args.length < 1){
                player.sendMessage(ChatUtilities.colored("&b&lGILDIA&8: &fPoprawne uzycie: &b" + this.getUsage()));
                return;
            }

            if(!guild.hasOwner(player.getName())){
                player.sendMessage(ChatUtilities.colored("&4Błąd: &cNie jesteś założycielem gildii"));
                return;
            }
            if(guild.getOwner().equalsIgnoreCase(player.getName())){
                player.sendMessage(ChatUtilities.colored("&4Błąd: &cJuż jesteś założycielem!"));
                return;
            }
            String nickName = args[0];
            if(!guild.hasMember(nickName)){
                player.sendMessage(ChatUtilities.colored("&4Błąd: &cPodany gracz nie jest twojej gildii!"));
                return;
            }
            guild.setOwner(nickName);
            guild.sendMessage("&3&lGILDIA &8>> &fNowy załozyciel gildii: &b" + nickName, BukkitMain.getInstance().getSectorService());
        });
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return new ArrayList<>();
    }
}

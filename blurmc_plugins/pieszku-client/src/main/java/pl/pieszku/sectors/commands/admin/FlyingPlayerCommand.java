package pl.pieszku.sectors.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pieszku.api.service.UserService;
import org.pieszku.api.type.GroupType;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.impl.Command;
import pl.pieszku.sectors.impl.CommandInfo;
import pl.pieszku.sectors.utilities.ChatUtilities;

@CommandInfo(name = "fly", permission = GroupType.HELPER)
public class FlyingPlayerCommand extends Command {

    private final UserService userService = BukkitMain.getInstance().getUserService();

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        Player player = (Player) commandSender;

        if(args.length == 0){
            player.setAllowFlight(!player.getAllowFlight());
            player.sendTitle(ChatUtilities.colored("&3&l♦ &b&lLATANIE &3&l♦"),
                    ChatUtilities.colored("&b&l♦ &fZostało&8(" + (player.getAllowFlight() ? "&awłączone" : "&cwyłączone") +"&7, &fdla ciebie&8) &b&l♦"));
            return;
        }
        if(args.length == 1){
            Player targetPlayer = Bukkit.getPlayerExact(args[0]);
            if(targetPlayer == null){
                player.sendMessage(ChatUtilities.colored("&4Błąd: &cPodany użytkownik jest offline bądż na innym sektorze!"));
                return;
            }
            targetPlayer.setAllowFlight(!targetPlayer.getAllowFlight());
            player.sendTitle(ChatUtilities.colored("&3&l♦ &b&lLATANIE &3&l♦"),
                    ChatUtilities.colored("&b&l♦ &fZostało&8(" + (player.getAllowFlight() ? "&awłączone" : "&cwyłączone") +"&7, &fdla&8:&b" + targetPlayer.getName() + "&8) &b&l♦"));

            targetPlayer.sendTitle(ChatUtilities.colored("&3&l♦ &b&lLATANIE &3&l♦"),
                    ChatUtilities.colored("&b&l♦ &fZostało&8(" + (player.getAllowFlight() ? "&awłączone" : "&cwyłączone") +"&7, &fprzez&8:&b" + player.getName() + "&8) &b&l♦"));

        }
    }
}

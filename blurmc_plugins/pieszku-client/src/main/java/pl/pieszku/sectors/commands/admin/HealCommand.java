package pl.pieszku.sectors.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pieszku.api.type.GroupType;
import pl.pieszku.sectors.impl.Command;
import pl.pieszku.sectors.impl.CommandInfo;
import pl.pieszku.sectors.utilities.ChatUtilities;

@CommandInfo(name = "heal", permission = GroupType.HELPER)
public class HealCommand extends Command {


    @Override
    public void execute(CommandSender commandSender, String[] args) {
        Player player = (Player) commandSender;

        if(args.length == 0){
            player.setFireTicks(0);
            player.setHealth(20);
            player.setFoodLevel(20);
            player.sendTitle(ChatUtilities.colored("&3&l♦ &2&lLECZENIE &3&l♦"),
                    ChatUtilities.colored("&b&l♦ &8(&aPomyślnie uleczono&7, &fciebie&8) &b&l♦"));
            return;
        }
        if(args.length == 1){
            Player targetPlayer = Bukkit.getPlayerExact(args[0]);
            if(targetPlayer == null){
                player.sendMessage(ChatUtilities.colored("&4Błąd: &cPodany użytkownik jest offline bądż na innym sektorze!"));
                return;
            }
            targetPlayer.setFireTicks(0);
            targetPlayer.setHealth(20);
            targetPlayer.setFoodLevel(20);
            player.sendTitle(ChatUtilities.colored("&3&l♦ &2&lLECZENIE &3&l♦"),
                    ChatUtilities.colored("&b&l♦ &8(&aPomyślnie uleczono&7, &f" + targetPlayer.getName() +"&8) &b&l♦"));

            targetPlayer.sendTitle(ChatUtilities.colored("&3&l♦ &2&lLECZENIE &3&l♦"),
                    ChatUtilities.colored("&b&l♦ &aZostałeś uleczony&8(&fprzez&8:&b" + player.getName() + "&8) &b&l♦"));

        }

    }
}

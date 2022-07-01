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

@CommandInfo(name = "vanish", aliases = {"v"}, permission = GroupType.HELPER)
public class VanishCommand extends Command {

    private final UserService userService = BukkitMain.getInstance().getUserService();

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        Player player = (Player) commandSender;

        this.userService.findUserByNickName(player.getName()).ifPresent(user -> {
            if(!user.isVanish()){
                user.setVanish(true);
                player.sendTitle(ChatUtilities.colored("&b&lVANISH"), ChatUtilities.colored("&3&l♦ &aWłączono pomyślnie niewidzialność &3&l♦"));
                return;
            }
            user.setVanish(false);
            player.sendTitle(ChatUtilities.colored("&b&lVANISH"), ChatUtilities.colored("&3&l♦ &cWyłączono pomyślnie niewidzialność &3&l♦"));

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.showPlayer(player);
            }
        });
    }
}

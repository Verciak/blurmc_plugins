package pl.pieszku.sectors.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pieszku.api.service.UserService;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.impl.Command;
import pl.pieszku.sectors.impl.CommandInfo;
import pl.pieszku.sectors.utilities.ChatUtilities;
import pl.pieszku.sectors.utilities.SkinUtilities;

@CommandInfo(name = "ukrywajsie", aliases = {"incognito", "ukryj"})
public class IncognitoCommand extends Command {

    private final UserService userService = BukkitMain.getInstance().getUserService();

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        Player player = (Player) commandSender;
        this.userService.findUserByNickName(player.getName()).ifPresent(user -> {


            user.setIncognito(!user.isIncognito());

            if(!user.isIncognito()){
                new SkinUtilities().removeSkin(player);
                player.sendTitle(ChatUtilities.colored("&4&lINCOGNITO"), ChatUtilities.colored("&8:: &cInformacje o tobie zostały odkryte &8::"));
                return;
            }
            new SkinUtilities().changeSkin(player);
            player.sendTitle(ChatUtilities.colored("&a&lINCOGNITO"), ChatUtilities.colored("&8:: &aInformacje o tobie zostały ukryte &8::"));

        });
    }
}

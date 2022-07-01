package pl.pieszku.sectors.commands.admin;

import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pieszku.api.redis.packet.client.teleport.TeleportPlayerRequestPacket;
import org.pieszku.api.sector.SectorService;
import org.pieszku.api.service.UserService;
import org.pieszku.api.type.GroupType;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.impl.Command;
import pl.pieszku.sectors.impl.CommandInfo;
import pl.pieszku.sectors.utilities.ChatUtilities;

@CommandInfo(name = "tphere", aliases = {"s"}, permission = GroupType.HELPER)
public class TeleportHerePlayerCommand extends Command {
    
    private final UserService userService = BukkitMain.getInstance().getUserService();
    private final SectorService sectorService = BukkitMain.getInstance().getSectorService();
    
    @Override
    public void execute(CommandSender commandSender, String[] args) {
        Player player = (Player) commandSender;

        if(args.length < 1){
            player.sendMessage(ChatUtilities.colored("&7Poprawne użycie: &b/tphere <nick>"));
            return;
        }
        String nickName = args[0];
        if(!this.sectorService.isOnlinePlayer(nickName)){
            player.sendMessage(ChatUtilities.colored("&4Błąd: &cPodany użytkownik jest aktualnie offline!"));
            return;
        }
        if(nickName.equalsIgnoreCase(player.getName())){
            player.sendMessage(ChatUtilities.colored("&4Błąd: &cNie możesz przeteleportować się sam do siebie!"));
            return;
        }
        player.sendTitle(ChatUtilities.colored("&6&l♦ &e&lTPHERE &6&l♦"),
                ChatUtilities.colored("&e&l♦ &6Gracz&8(&e" + nickName +"&8) &6został przeteleportowany do&8(&eciebie&8) &e&l♦"));
        player.playSound(player.getLocation(), Sound.MUSIC_CREDITS, 10, 10);

        new TeleportPlayerRequestPacket(nickName, player.getName(), 0).sendToChannel("SECTORS");
    }
}

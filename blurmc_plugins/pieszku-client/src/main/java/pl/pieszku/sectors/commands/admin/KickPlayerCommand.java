package pl.pieszku.sectors.commands.admin;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pieszku.api.redis.packet.client.KickPlayerPacket;
import org.pieszku.api.sector.SectorService;
import org.pieszku.api.type.GroupType;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.impl.Command;
import pl.pieszku.sectors.impl.CommandInfo;
import pl.pieszku.sectors.utilities.ChatUtilities;

@CommandInfo(name = "kick", permission = GroupType.HELPER)
public class KickPlayerCommand extends Command {

    private final SectorService sectorService = BukkitMain.getInstance().getSectorService();

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        Player player = (Player) commandSender;

        if(args.length < 2){
            player.sendMessage(ChatUtilities.colored("&7Poprawne użycie: &b/kick <nick> <powód>"));
            return;
        }
        String nickName = args[0];
        if(!this.sectorService.isOnlinePlayer(nickName)){
            player.sendMessage(ChatUtilities.colored("&4Błąd: &cPodany użytkownik jest aktualnie offline!"));
            return;
        }
        if(nickName.equalsIgnoreCase(player.getName())){
           player.sendMessage(ChatUtilities.colored("&4Błąd: &cNie możesz wyrzucić sam siebie!"));
            return;
        }
        String reason = StringUtils.join(args, " ", 1, args.length);
        new KickPlayerPacket(nickName,
                "&8&m----[&4&m----[&8&m---[ &8 &c&l!!! &8&m ]---&4&m]----&8&m]----&8\n   &cZostałeś wyrzucony z serwera\n" +
                        "&7Admininstrator: &c" + player.getName() + "\n" +
                        "&7Powód: &c" + reason  + "\n" +
                        "&d\n" +
                        "   &7Zazwyczaj nie dzieje się to bez powodu\n" +
                        "       &a&n&lWEJDŻ PONOWNIE DO GRY").sendToChannel("SECTORS");
        player.sendTitle(ChatUtilities.colored("&c&l♦ &4&lKICK &c&l♦"),
                ChatUtilities.colored("&4&l♦ &cPomyślnie wyrzuciłeś gracza&8(&f" + nickName +"&8) &4&l♦"));
    }
}

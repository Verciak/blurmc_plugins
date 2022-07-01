package pl.pieszku.sectors.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pieszku.api.redis.packet.client.teleport.TeleportPlayerRequestPacket;
import org.pieszku.api.sector.SectorService;
import org.pieszku.api.service.UserService;
import org.pieszku.api.type.TimeType;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.impl.Command;
import pl.pieszku.sectors.impl.CommandInfo;
import pl.pieszku.sectors.utilities.ChatUtilities;

@CommandInfo(name = "tpaccept")
public class TeleportRequestAcceptCommand extends Command {

    private final UserService userService = BukkitMain.getInstance().getUserService();
    private final SectorService sectorService = BukkitMain.getInstance().getSectorService();

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        Player player = (Player) commandSender;

        if (args.length < 1) {
            player.sendMessage(ChatUtilities.colored("&7Poprawne użycie: &b/tpaccept <nick/*>"));
            return;
        }


        this.userService.findUserByNickName(player.getName()).ifPresent(user -> {
            if (args[0].equalsIgnoreCase("*")) {
                if(user.getPlayersTeleportRequest().size() <= 0){
                    player.sendMessage(ChatUtilities.colored("&4Błąd: &cNie posiadasz żadnej prośby o teleportacje!"));
                    return;
                }
                user.getPlayersTeleportRequest().forEach(nickName -> {
                    player.sendMessage(ChatUtilities.colored("&b&lTELEPORTACJA&8: &fZaakceptowałeś wszystkie prośby o teleportacje!"));
                    new TeleportPlayerRequestPacket(nickName, player.getName(), System.currentTimeMillis() + TimeType.SECOND.getTime(10)).sendToChannel("SECTORS");
                });
                user.getPlayersTeleportRequest().clear();
                return;
            }
            String nickName = args[0];
            if(!this.sectorService.isOnlinePlayer(nickName)){
                player.sendMessage(ChatUtilities.colored("&4Błąd: &cPodany gracz jest aktualnie offline!"));
                return;
            }
            if(!user.getPlayersTeleportRequest().contains(nickName)){
                player.sendMessage(ChatUtilities.colored("&4Błąd: &cNie masz prośby o teleportacje od tego gracza!"));
                return;
            }
            new TeleportPlayerRequestPacket(nickName, player.getName(), System.currentTimeMillis() + TimeType.SECOND.getTime(10)).sendToChannel("SECTORS");
            player.sendMessage(ChatUtilities.colored("&b&lTELEPORTACJA&8: &fZaakceptowałeś prośbe o teleport od: &b" + nickName));
        });
    }
}

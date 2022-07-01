package pl.pieszku.sectors.commands.player;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pieszku.api.objects.user.User;
import org.pieszku.api.redis.packet.client.SendMessagePacket;
import org.pieszku.api.redis.packet.type.MessageType;
import org.pieszku.api.redis.packet.type.ReceiverType;
import org.pieszku.api.sector.SectorService;
import org.pieszku.api.service.UserService;
import org.pieszku.api.type.GroupType;
import org.pieszku.api.type.TimeType;
import org.pieszku.api.utilities.DataUtilities;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.impl.Command;
import pl.pieszku.sectors.impl.CommandInfo;
import pl.pieszku.sectors.utilities.ChatUtilities;

@CommandInfo(name = "helpop")
public class HelpopCommand extends Command {

    private final UserService userService = BukkitMain.getInstance().getUserService();
    private final SectorService sectorService = BukkitMain.getInstance().getSectorService();

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        Player player = (Player) commandSender;

        if (args.length < 1) {
            player.sendMessage(ChatUtilities.colored("&7Poprawne użycie: &b/helpop <wiadomosc>"));
            return;
        }
        this.userService.findUserByNickName(player.getName()).ifPresent(user -> {

            if (!user.hasCooldownHelpop()) {
                player.sendMessage(ChatUtilities.colored("&b&lHELPOP &fNastępną wiadomość możesz wysłać za: &b" + DataUtilities.getTimeToString(user.getCooldownHelpop())));
                return;
            }

            String message = StringUtils.join(args, " ", 0, args.length);

            player.sendTitle(ChatUtilities.colored("&8[&bHELPOP&8]"),
                    ChatUtilities.colored("&b&l► &aPomyślnie wysłano wiadomość do admininstracji &b&l►"));

            user.setCooldownHelpop(System.currentTimeMillis() + TimeType.SECOND.getTime(30));

            SendMessagePacket sendMessagePacket = new SendMessagePacket("&f&lHELPOP: &bGracz: " + player.getName() + " &fwysyła wiadomość: \n" +
                    "&b" + message, ReceiverType.PLAYER, MessageType.CHAT);

            for (User userFind : this.userService.getUserMap().values()) {
                if (!GroupType.hasPermission(userFind, GroupType.HELPER)) continue;
                sendMessagePacket.setNickNameReceiver(userFind.getNickName());
                sendMessagePacket.sendToAllSectors(this.sectorService);
            }
        });
    }
}

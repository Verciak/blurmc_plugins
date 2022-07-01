package pl.pieszku.sectors.commands.admin;

import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pieszku.api.objects.ban.Ban;
import org.pieszku.api.objects.user.User;
import org.pieszku.api.redis.packet.ban.sync.BanSynchronizeInformationPacket;
import org.pieszku.api.redis.packet.ban.type.BanType;
import org.pieszku.api.redis.packet.client.SendMessagePacket;
import org.pieszku.api.redis.packet.type.MessageType;
import org.pieszku.api.redis.packet.type.ReceiverType;
import org.pieszku.api.redis.packet.type.UpdateType;
import org.pieszku.api.sector.SectorService;
import org.pieszku.api.service.BanService;
import org.pieszku.api.service.UserService;
import org.pieszku.api.type.GroupType;
import org.pieszku.api.utilities.DataUtilities;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.impl.Command;
import pl.pieszku.sectors.impl.CommandInfo;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.util.Optional;

@CommandInfo(name = "ban", permission = GroupType.MODERATOR)
public class BanCommand extends Command {

    private final BanService banService = BukkitMain.getInstance().getBanService();
    private final UserService userService = BukkitMain.getInstance().getUserService();
    private final SectorService sectorService = BukkitMain.getInstance().getSectorService();

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        Player player = (Player) commandSender;

        if (args.length < 3) {
            player.sendMessage(ChatUtilities.colored("&7Poprawne użycie: &b/ban <nick> <czas> <powód>"));
            return;
        }
        String nickNameTarget = args[0];

        Optional<User> userOptional = this.userService.findUserByNickName(nickNameTarget);

        if (!userOptional.isPresent()) {
            player.sendMessage(ChatUtilities.colored("&4Błąd: &cPodany gracz nigdy nie był na serwerze!"));
            return;
        }
        Ban findBan = this.banService.findBanByNickName(nickNameTarget);

        if (findBan != null) {
            player.sendMessage(ChatUtilities.colored("&4Błąd: &cPodany gracz posiada już bana!"));
            return;
        }
        long tempDelay = DataUtilities.parseDateDiff(args[1], true);
        String reason = StringUtils.join(args, " ", 2, args.length);
        Ban ban = this.banService.create(nickNameTarget, player.getName(), userOptional.get().getAddressHostName(), reason, tempDelay);
        player.sendTitle(ChatUtilities.colored("&3&lBAN"),
                ChatUtilities.colored("&b&l♦ &fPomyślnie zbanowałeś gracza&8(&b" + nickNameTarget + " &7| &b" + DataUtilities.getTimeToString(tempDelay) + "&8) &b&l♦"));

        new SendMessagePacket(
                "&8&m----[&b&m----[&8&m---[ &8 &b&lBAN &8&m ]---&b&m]----&8&m]----\n" +
                        "&fGracz: &b" + nickNameTarget + " &fzostał zbanowany\n" +
                        "&fBan mija za: &3" + DataUtilities.getTimeToString(tempDelay) + "\n" +
                        "&fPowód: &b" + reason + "\n" +
                        "&8&m----[&b&m----[&8&m---[ &8 &b&lBAN &8&m ]---&b&m]----&8&m]----\n",
                ReceiverType.ALL, MessageType.CHAT).sendToAllSectors(this.sectorService);

        new BanSynchronizeInformationPacket(nickNameTarget, UpdateType.CREATE, BanType.NICKNAME, new Gson().toJson(ban))
                .sendToAllSectorsAndApp(this.sectorService);
    }
}

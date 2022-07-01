package pl.pieszku.sectors.commands.admin;

import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pieszku.api.API;
import org.pieszku.api.objects.mute.Mute;
import org.pieszku.api.redis.packet.client.SendMessagePacket;
import org.pieszku.api.redis.packet.mute.sync.MuteInformationSynchronizePacket;
import org.pieszku.api.redis.packet.type.MessageType;
import org.pieszku.api.redis.packet.type.ReceiverType;
import org.pieszku.api.redis.packet.type.UpdateType;
import org.pieszku.api.sector.SectorService;
import org.pieszku.api.service.MuteService;
import org.pieszku.api.service.UserService;
import org.pieszku.api.type.GroupType;
import org.pieszku.api.utilities.DataUtilities;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.impl.Command;
import pl.pieszku.sectors.impl.CommandInfo;
import pl.pieszku.sectors.utilities.ChatUtilities;

@CommandInfo(name = "mute", permission = GroupType.HELPER)
public class MuteCommand extends Command {

    private final MuteService muteService = API.getInstance().getMuteService();
    private final SectorService sectorService = BukkitMain.getInstance().getSectorService();
    private final UserService userService = API.getInstance().getUserService();

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        Player player = (Player) commandSender;


        if (args.length < 3) {
            player.sendMessage(ChatUtilities.colored("&7Poprawne użycie: &b/mute <nick> <czas> <powód>"));
            return;
        }

        String nickName = args[0];
        if(!this.userService.findUserByNickName(nickName).isPresent()){
            player.sendMessage(ChatUtilities.colored("&4Błąd: &cPodany gracz nigdy nie był na serwerze!"));
            return;
        }
        if(this.muteService.findMuteByNickName(nickName).isPresent()){
            player.sendMessage(ChatUtilities.colored("&4Błąd: &cPodany gracz posiada już blokadę pisania na czacie!"));
            return;
        }
        long tempDelay = DataUtilities.parseDateDiff(args[1], true);
        String reason = StringUtils.join(args, " ", 2, args.length);
        Mute mute = this.muteService.create(nickName, reason, tempDelay, player.getName());
        new MuteInformationSynchronizePacket(mute.getNickName(), UpdateType.CREATE, new Gson().toJson(mute)).sendToChannel("MASTER");


        player.sendTitle(ChatUtilities.colored("&3&lMUTE"),
                ChatUtilities.colored("&b&l♦ &fPomyślnie wyciszyłeś gracza&8(&b" + nickName + " &7| &b" + DataUtilities.getTimeToString(tempDelay) + "&8) &b&l♦"));

        new SendMessagePacket(
                "&8&m----[&b&m----[&8&m---[ &8 &b&lMUTE &8&m ]---&b&m]----&8&m]----\n" +
                        "&fGracz: &b" + nickName + " &fzostał wyciszony\n" +
                        "&fWyciszenie mija za: &3" + DataUtilities.getTimeToString(tempDelay) + "\n" +
                        "&fPowód: &b" + reason + "\n" +
                        "&8&m----[&b&m----[&8&m---[ &8 &b&lMUTE &8&m ]---&b&m]----&8&m]----\n",
                ReceiverType.ALL, MessageType.CHAT).sendToAllSectors(this.sectorService);


    }
}

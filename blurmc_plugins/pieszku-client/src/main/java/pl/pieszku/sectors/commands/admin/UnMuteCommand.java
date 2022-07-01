package pl.pieszku.sectors.commands.admin;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pieszku.api.API;
import org.pieszku.api.redis.packet.mute.sync.MuteInformationSynchronizePacket;
import org.pieszku.api.redis.packet.type.UpdateType;
import org.pieszku.api.service.MuteService;
import org.pieszku.api.type.GroupType;
import org.pieszku.api.utilities.DataUtilities;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.impl.Command;
import pl.pieszku.sectors.impl.CommandInfo;
import pl.pieszku.sectors.utilities.ChatUtilities;

@CommandInfo(name = "unmute", permission = GroupType.HELPER)
public class UnMuteCommand extends Command {

    private final MuteService muteService = API.getInstance().getMuteService();

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        Player player = (Player) commandSender;


        if (args.length < 1) {
            player.sendMessage(ChatUtilities.colored("&7Poprawne użycie: &b/unmute <nick>"));
            return;
        }
        String nickName = args[0];
        if(!this.muteService.findMuteByNickName(nickName).isPresent()){
            player.sendMessage(ChatUtilities.colored("&4Błąd: &cPodany gracz nie posiada blokady!"));
            return;
        }
        this.muteService.findMuteByNickName(nickName).ifPresent(mute -> {

            player.sendTitle(ChatUtilities.colored("&3&lMUTE"), ChatUtilities.colored("&b&l♦ &fPomyślnie odciszyłeś gracza&8(&b" + mute.getNickName() + " &7| &b" + DataUtilities.getTimeToString(mute.getDelay()) + "&8) &b&l♦"));
            Bukkit.getScheduler().runTaskLaterAsynchronously(BukkitMain.getInstance(), () -> {
                commandSender.sendMessage(ChatUtilities.colored("&3&lMUTE &b&l♦ &fPomyślnie odciszyłeś gracza&8(&b" + mute.getNickName() + " &7| &b" + DataUtilities.getTimeToString(mute.getDelay()) + "&8) &b&l♦"));
                new MuteInformationSynchronizePacket(mute.getNickName(), UpdateType.REMOVE, new Gson().toJson(mute)).sendToChannel("MASTER");
            }, 10L);
        });

    }
}

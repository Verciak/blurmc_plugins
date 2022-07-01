package pl.pieszku.sectors.commands.admin;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pieszku.api.objects.ban.Ban;
import org.pieszku.api.redis.packet.ban.sync.BanSynchronizeInformationPacket;
import org.pieszku.api.redis.packet.ban.type.BanType;
import org.pieszku.api.redis.packet.type.UpdateType;
import org.pieszku.api.sector.SectorService;
import org.pieszku.api.service.BanService;
import org.pieszku.api.type.GroupType;
import org.pieszku.api.utilities.DataUtilities;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.impl.Command;
import pl.pieszku.sectors.impl.CommandInfo;
import pl.pieszku.sectors.utilities.ChatUtilities;

@CommandInfo(name = "unban", permission = GroupType.HELPER)
public class UnBanCommand extends Command {


    private final BanService banService = BukkitMain.getInstance().getBanService();
    private final SectorService sectorService = BukkitMain.getInstance().getSectorService();

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (args.length < 1) {
                player.sendMessage(ChatUtilities.colored("&7Poprawne użycie: &b/unban <nick>"));
                return;
            }
            String nickNameTarget = args[0];
            Ban ban = this.banService.findBanByNickName(nickNameTarget);

            if (ban == null) {
                player.sendMessage(ChatUtilities.colored("&4Błąd: &cPodany gracz nie posiada blokady!"));
                return;
            }
            player.sendTitle(ChatUtilities.colored("&3&lBAN"), ChatUtilities.colored("&b&l♦ &fPomyślnie odbanowałeś gracza&8(&b" + ban.getNickName() + " &7| &b" + DataUtilities.getTimeToString(ban.getTempDelay()) + "&8) &b&l♦"));
            Bukkit.getScheduler().runTaskLaterAsynchronously(BukkitMain.getInstance(), () -> {
                commandSender.sendMessage(ChatUtilities.colored("&3&lBAN &b&l♦ &fPomyślnie odbanowałeś gracza&8(&b" + ban.getNickName() + " &7| &b" + DataUtilities.getTimeToString(ban.getTempDelay()) + "&8) &b&l♦"));
                new BanSynchronizeInformationPacket(ban.getNickName(), UpdateType.REMOVE, BanType.NICKNAME, new Gson().toJson(ban)).sendToAllSectorsAndApp(this.sectorService);
            }, 10L);
            return;
        }
        if (args.length < 1) {
            commandSender.sendMessage(ChatUtilities.colored("&7Poprawne użycie: &b/unban <nick>"));
            return;
        }
        String nickNameTarget = args[0];
        Ban ban = this.banService.findBanByNickName(nickNameTarget);

        if (ban == null) {
            commandSender.sendMessage(ChatUtilities.colored("&4Błąd: &cPodany gracz nie posiada blokady!"));
            return;
        }
        Bukkit.getScheduler().runTaskLaterAsynchronously(BukkitMain.getInstance(), () -> {
            commandSender.sendMessage(ChatUtilities.colored("&3&lBAN &b&l♦ &fPomyślnie odbanowałeś gracza&8(&b" + ban.getNickName() + " &7| &b" + DataUtilities.getTimeToString(ban.getTempDelay()) + "&8) &b&l♦"));
            new BanSynchronizeInformationPacket(ban.getNickName(), UpdateType.REMOVE, BanType.NICKNAME, new Gson().toJson(ban)).sendToAllSectorsAndApp(this.sectorService);
        }, 10L);
    }
}

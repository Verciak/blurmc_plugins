package pl.pieszku.sectors.handler;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.pieszku.api.API;
import org.pieszku.api.objects.ban.Ban;
import org.pieszku.api.proxy.global.WhitelistServer;
import org.pieszku.api.proxy.global.WhitelistServerService;
import org.pieszku.api.redis.packet.ban.sync.BanSynchronizeInformationPacket;
import org.pieszku.api.redis.packet.ban.type.BanType;
import org.pieszku.api.redis.packet.type.UpdateType;
import org.pieszku.api.sector.SectorService;
import org.pieszku.api.service.BanService;
import org.pieszku.api.service.UserService;
import org.pieszku.api.utilities.DataUtilities;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.util.Optional;

public class PlayerLoginHandler implements Listener {

    private final UserService userService = BukkitMain.getInstance().getUserService();
    private final BanService banService =BukkitMain.getInstance().getBanService();
    private final SectorService  sectorService=BukkitMain.getInstance().getSectorService();
    private final WhitelistServerService whitelistServerService = API.getInstance().getWhitelistServerService();

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();


        Optional<WhitelistServer> whitelistServerOptional = this.whitelistServerService.findWhitelistByServerName(BukkitMain.getInstance().getSectorName());

//        if(!whitelistServerOptional.isPresent()){
//            event.setResult(PlayerLoginEvent.Result.KICK_BANNED);
//            event.setKickMessage(ChatUtilities.colored("&4Błąd: &cPodczas konfiguracji sektorów"));
//            return;
//        }
        WhitelistServer whitelistServer = whitelistServerOptional.get();

        if(whitelistServer.isWhitelist() && !whitelistServer.getMembers().contains(event.getPlayer().getName())){
            event.setResult(PlayerLoginEvent.Result.KICK_BANNED);
            event.setKickMessage(ChatUtilities.colored("&a\n&8&m---&c&m---&8&m---[ &8 &4Blur&cMC.PL &8&m ]---&c&m---&8&m---\n" +
                    "&cAktualnie sektor: &4" + whitelistServer.getChannelName().toUpperCase() + " &codmówił dostępu\n" +
                    "&cNa sektorze jest aktualnie włączona whitelista:\n" +
                    "&cPowód: &4" + whitelistServer.getReason() + "\n" +
                    "&7Spróbuj ponownie póżniej."));
            return;
        }


        Ban ban = this.banService.findBanByNickName(player.getName());

        if(ban != null){
            Bukkit.getScheduler().runTaskLater(BukkitMain.getInstance(), () -> {
            if(ban.getTempDelay() <= System.currentTimeMillis()) new BanSynchronizeInformationPacket(player.getName(), UpdateType.REMOVE, BanType.NICKNAME,new Gson().toJson(ban)).sendToAllSectorsAndApp(this.sectorService);
            }, 10L);
            event.setResult(PlayerLoginEvent.Result.KICK_BANNED);
            event.setKickMessage(ChatUtilities.colored(
                       "   &cZostałeś zbanowany\n" +
                            "&7Powód: &c" +ban.getReason()  + "\n" +
                            "&7Admininstrator: &c" + ban.getAdminNickName() + "\n" +
                            "&7Wygasa za: &c" + (ban.getTempDelay() <= System.currentTimeMillis() ? "wejdż ponownie" : DataUtilities.getTimeToString(ban.getTempDelay())) + "\n" +
                            "&d\n" +
                            "   &cNiesluszny ban? udaj się na discord: &4dc.blurmc.pl\n" +
                            "       &club zakup unban na: &4www.blurmc.pl"));
        }
    }
}

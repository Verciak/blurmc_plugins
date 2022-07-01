package pl.pieszku.proxy.handler;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.pieszku.api.API;
import org.pieszku.api.proxy.ProxyService;
import org.pieszku.api.proxy.global.WhitelistServer;
import org.pieszku.api.proxy.global.WhitelistServerService;
import org.pieszku.api.proxy.user.ProxyUserService;
import org.pieszku.api.utilities.DataUtilities;
import pl.pieszku.proxy.ProxyMain;
import pl.pieszku.proxy.service.MasterConnectionHeartbeatService;
import pl.pieszku.proxy.utilities.ChatUtilities;

import java.util.Optional;

public class ServerConnectHandler implements Listener {


    private final ProxyUserService proxyUserService = ProxyMain.getInstance().getProxyUserService();
    private final ProxyService proxyService = ProxyMain.getInstance().getProxyService();
    private final MasterConnectionHeartbeatService masterConnectionHeartbeatService = ProxyMain.getInstance().getMasterConnectionHeartbeatService();
    private final WhitelistServerService whitelistServerService = API.getInstance().getWhitelistServerService();

    @EventHandler
    public void onConnectServer(ServerConnectEvent event){
        ProxiedPlayer proxiedPlayer = event.getPlayer();


        if(!ProxyMain.getInstance().currentProxy().isPresent()){
            proxiedPlayer.disconnect(new TextComponent(
                    ChatUtilities.colored(
                            "&8&m----[&4&m----[&8&m---[ &8 &c&l!!! &8&m ]---&4&m]----&8&m]----&8" +
                                    "\n   &cZostałeś wyrzucony z serwera\n" +
                                    "&cTen serwer proxy &cnie został poprawnie skonfigurowany\n" +
                                    "       &aSpróbuj ponownie póżniej.")));
            return;
        }

        ProxyMain.getInstance().currentProxy().ifPresent(proxy -> {


            if(!this.masterConnectionHeartbeatService.isConnected()){

                proxiedPlayer.disconnect(new TextComponent(ChatUtilities.colored(
                        "&8&m----[&4&m----[&8&m---[ &8 &c&l!!! &8&m ]---&4&m]----&8&m]----&8" +
                                "\n   &cZostałeś wyrzucony z serwera\n" +
                                "&cSerwer głowny od aplikacji nie odpowiada od&8(&4" + DataUtilities.getTimeToString(System.currentTimeMillis() -this.masterConnectionHeartbeatService.getLastHeartbeat(), false) +"&8)\n" +
                                "&cTrzeba poczekać aż zostanie on ponownie uruchomiony.\n" +
                                "      &aSpróbuj ponownie póżniej.")));
                return;
            }

            if(!proxy.isOnline()){
                proxiedPlayer.disconnect(new TextComponent(
                        ChatUtilities.colored(
                          "&8&m----[&4&m----[&8&m---[ &8 &c&l!!! &8&m ]---&4&m]----&8&m]----&8" +
                                "\n   &cZostałeś wyrzucony z serwera\n" +
                                "&cTen serwer proxy&8(&f" + proxy.getName() + "&8) &caktualnie jest wyłączony\n" +
                                "      &aSpróbuj ponownie póżniej.")));
                return;
            }
            Optional<WhitelistServer> whitelistServerOptional = this.whitelistServerService.findWhitelistByServerName(ProxyMain.getInstance().getProxyName());

            if(!whitelistServerOptional.isPresent()){
                event.setCancelled(true);
                proxiedPlayer.disconnect(ChatUtilities.colored("&4Błąd: &cPodczas konfiguracji proxy!!"));
                return;
            }
            WhitelistServer whitelistServer = whitelistServerOptional.get();

            if(whitelistServer.isWhitelist() && !whitelistServer.getMembers().contains(event.getPlayer().getName())){
                event.setCancelled(true);
                proxiedPlayer.disconnect(ChatUtilities.colored("&8&m---&c&m---&8&m---[ &8 &4Blur&cMC.PL &8&m ]---&c&m---&8&m---\n" +
                        "&cAktualnie proxy: &4" + whitelistServer.getChannelName().toUpperCase() + " &codmówił dostępu\n" +
                        "&cNa proxy jest aktualnie włączona whitelista:\n" +
                        "&cPowód: &4" + whitelistServer.getReason() + "\n" +
                        "&7Spróbuj ponownie póżniej."));
                return;
            }

        });

    }

}

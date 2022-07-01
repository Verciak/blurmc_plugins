package pl.pieszku.proxy.handler;

import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import pl.pieszku.proxy.ProxyMain;
import pl.pieszku.proxy.utilities.ChatUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PingServerHandler implements Listener {

    @EventHandler
    public void onPingServer(ProxyPingEvent event){
        event.getResponse().setDescriptionComponent(new TextComponent(ChatUtilities.colored("&8>> &7Wersja: &d&l1.16-1.18")));
        event.getResponse().getVersion().setProtocol(1);
        event.getResponse().getVersion().setName(ChatUtilities.colored((ProxyMain.getInstance().getProxy().getPlayers().size() == 1000 ? "&7W grze: &f1000&8/&31000" : "&7W grze: &f" + ProxyMain.getInstance().getProxy().getPlayers().size()) + "&8/&31000"));

        event.getResponse().setDescription(ChatUtilities.colored(
                "               &3◄&l&3&l► &9&lONE&f&lHARD &7[1.16 - 1.18] &3◄&l&3&l► \n" +
                        "  &9discord: &fdc.onehard.pl &8| &aWejdż i wyczekuj informacji."));


        List<String> motdList = new ArrayList<>();
        motdList.add("&8&l&m---[&3&l&m----&8&l&m[----&8 &9&lONE&f&lHARD &8&l&m----]&3&l&m----&8&l&m]---&8");
        motdList.add(" ");
        motdList.add("&8» &fWersja: &91.16-1.18");
        motdList.add("&8» &fProxy: &9proxy_01");
        motdList.add("&8» &fStrona: &9www.onehard.pl");
        motdList.add("&8» &fDiscord: &9dc.onehard.pl");
        motdList.add(" ");
        motdList.add("&7Licznik nie pokaże więcej niż &9&n1000&7 graczy");
        motdList.add(" ");
        motdList.add("&8&l&m---[&3&l&m----&8&l&m[----&8 &9&lONE&f&lHARD &8&l&m----]&3&l&m----&8&l&m]---&8");

        ServerPing.PlayerInfo[] playerInfos = new ServerPing.PlayerInfo[motdList.size()];
        for (short i = 0; i < playerInfos.length; i++) {
            playerInfos[i] = new ServerPing.PlayerInfo(ChatUtilities.colored(motdList.get(i)), UUID.randomUUID());
        }
        event.getResponse().getPlayers().setSample(playerInfos);

    }
}

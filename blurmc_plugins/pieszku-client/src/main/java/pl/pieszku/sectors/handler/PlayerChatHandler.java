package pl.pieszku.sectors.handler;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.pieszku.api.API;
import org.pieszku.api.objects.guild.impl.Guild;
import org.pieszku.api.redis.packet.client.SendMessagePacket;
import org.pieszku.api.redis.packet.type.MessageType;
import org.pieszku.api.redis.packet.type.ReceiverType;
import org.pieszku.api.sector.SectorService;
import org.pieszku.api.service.GuildService;
import org.pieszku.api.service.MuteService;
import org.pieszku.api.service.UserService;
import org.pieszku.api.utilities.DataUtilities;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.util.Optional;

public class PlayerChatHandler implements Listener {

    private final MuteService muteService  = API.getInstance().getMuteService();
    private final UserService userService = API.getInstance().getUserService();
    private final GuildService guildService = API.getInstance().getGuildService();
    private final SectorService sectorService = BukkitMain.getInstance().getSectorService();


    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerChat(PlayerChatEvent event){
        Player player = event.getPlayer();
        String message = event.getMessage();

        message = message.replace("%", "");

        String finalMessage = message;
        this.userService.findUserByNickName(player.getName()).ifPresent(user -> {


            this.muteService.findMuteByNickName(player.getName()).ifPresent(mute -> {
                event.setCancelled(true);
                player.sendMessage(ChatUtilities.colored("&b&lMUTE &fJesteś aktualnie wyciszony:\n" +
                        "&8:: &fPowód: &b" + mute.getReason() + "\n" +
                        "&8:: &fAdmin: &b" + mute.getAdminNickname() + "\n" +
                        "&8:: &fWyciszenie mija za: &b" + DataUtilities.getTimeToString(mute.getDelay())));
                return;
            });
            if (this.muteService.findMuteByNickName(player.getName()).isPresent()) return;

            event.setCancelled(true);

            Optional<Guild> guildOptional = this.guildService.findGuildByMember(player.getName());

            event.setFormat(ChatUtilities.colored(user.getGroupType().getChatFormat()
                    .replace("{NICKNAME}", player.getName())
                    .replace("{LEVEL}", "&e" + user.getLevelMiner() + "&6&l⛏")
                    .replace("{MESSAGE}", finalMessage)
                    .replace("{GUILD}", (!guildOptional.isPresent() ? "" : "&8[&c" + guildOptional.get().getName().toUpperCase() + "&8] "))));

            new SendMessagePacket(event.getFormat(), ReceiverType.ALL, MessageType.CHAT).sendToAllSectors(this.sectorService);
        });

    }
}

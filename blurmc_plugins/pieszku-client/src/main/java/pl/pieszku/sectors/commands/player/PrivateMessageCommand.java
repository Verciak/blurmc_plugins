package pl.pieszku.sectors.commands.player;

import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pieszku.api.objects.user.User;
import org.pieszku.api.redis.packet.client.SendMessagePacket;
import org.pieszku.api.redis.packet.type.MessageType;
import org.pieszku.api.redis.packet.type.ReceiverType;
import org.pieszku.api.redis.packet.user.UserPlayOutInformationPacket;
import org.pieszku.api.redis.packet.user.request.UserPlayOutRequestPacket;
import org.pieszku.api.sector.SectorService;
import org.pieszku.api.service.UserService;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.impl.Command;
import pl.pieszku.sectors.impl.CommandInfo;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.util.Optional;

@CommandInfo(name = "msg")
public class PrivateMessageCommand extends Command {


    private final UserService userService = BukkitMain.getInstance().getUserService();
    private final SectorService sectorService = BukkitMain.getInstance().getSectorService();

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        Player player = (Player) commandSender;

        if(args.length < 2){
            player.sendMessage(ChatUtilities.colored("&7Poprawne użycie: &b/msg <nick> <wiadomosc>"));
            return;
        }
        Optional<User> optionalUser = this.userService.findUserByNickName(args[0]);

        if(!optionalUser.isPresent()){
            player.sendMessage(ChatUtilities.colored("&4Błąd: &cPodany gracz nigdy nie był na serwerze"));
            return;
        }

        if(args[0].equalsIgnoreCase(player.getName())){
            player.sendMessage(ChatUtilities.colored("&4Błąd: &cNie możesz wysłać wiadomości sam do siebie!"));
            return;
        }

        optionalUser.ifPresent(user -> {
            if(!this.sectorService.isOnlinePlayer(user.getNickName())){
                player.sendMessage(ChatUtilities.colored("&4Błąd: &cPodany gracz jest aktualnie offline!"));
                return;
            }

            String message = StringUtils.join(args, " ", 1, args.length);

            new UserPlayOutRequestPacket(user.getNickName(), BukkitMain.getInstance().getSectorName()).sendToChannel("MASTER");
            Bukkit.getScheduler().runTaskLaterAsynchronously(BukkitMain.getInstance(), () -> {


                User userRequest = this.userService.getOrCreate(user.getNickName());
                userRequest.setLatestPrivateMessageNickName(player.getName());
                userRequest.setInteractSector(true);

                new UserPlayOutInformationPacket(user.getNickName(), new Gson().toJson(userRequest))
                        .sendToChannel(userRequest.getNowSector());

                userRequest = this.userService.getOrCreate(user.getNickName());

                player.sendMessage(ChatUtilities.colored("&b&lMSG &8| &3Ja &b&l► &3" + userRequest.getNickName() + "&8: &b" + message));
                SendMessagePacket sendMessagePacket =  new SendMessagePacket("&b&lMSG &8| &3" + player.getName() + " &b&l► &3Ja&8: &b" + message, ReceiverType.PLAYER, MessageType.CHAT);
                sendMessagePacket.setNickNameReceiver(userRequest.getNickName());
                sendMessagePacket.sendToChannel(userRequest.getNowSector());
            }, 5L);
        });
    }
}

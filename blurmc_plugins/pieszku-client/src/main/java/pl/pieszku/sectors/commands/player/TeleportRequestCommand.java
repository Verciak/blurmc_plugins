package pl.pieszku.sectors.commands.player;

import com.google.gson.Gson;
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

@CommandInfo(name = "tpa")
public class TeleportRequestCommand extends Command {

    private final SectorService sectorService = BukkitMain.getInstance().getSectorService();
    private final UserService userService = BukkitMain.getInstance().getUserService();

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        Player player = (Player) commandSender;

        if(args.length < 1){
            player.sendMessage(ChatUtilities.colored("&7Poprawne użycie: &b/tpa <nick>"));
            return;
        }
        Optional<User> optionalUser = this.userService.findUserByNickName(args[0]);

        if(!optionalUser.isPresent()){
            player.sendMessage(ChatUtilities.colored("&4Błąd: &cPodany gracz nigdy nie był na serwerze"));
            return;
        }
        if(args[0].equalsIgnoreCase(player.getName())){
            player.sendMessage(ChatUtilities.colored("&4Błąd: &cNie możesz wysłać prośby o teleport sam do siebie!"));
            return;
        }
        optionalUser.ifPresent(user -> {
            if(!this.sectorService.isOnlinePlayer(user.getNickName())){
                player.sendMessage(ChatUtilities.colored("&4Błąd: &cPodany gracz jest aktualnie offline!"));
                return;
            }
            new UserPlayOutRequestPacket(user.getNickName(), BukkitMain.getInstance().getSectorName()).sendToChannel("MASTER");
            player.sendMessage(ChatUtilities.colored("&b&lTELEPORTACJA&8: &fTrwa wysyłanie prośby o teleportacje do gracza: &3" + user.getNickName()));
            Bukkit.getScheduler().runTaskLaterAsynchronously(BukkitMain.getInstance(), () -> {


                User userRequest = this.userService.getOrCreate(user.getNickName());
                userRequest.getPlayersTeleportRequest().add(player.getName());
                userRequest.setInteractSector(true);

                new UserPlayOutInformationPacket(user.getNickName(), new Gson().toJson(userRequest))
                        .sendToChannel(userRequest.getNowSector());

               userRequest = this.userService.getOrCreate(user.getNickName());

                player.sendMessage(ChatUtilities.colored("&b&lTELEPORTACJA&8: &fGracz: &b" + user.getNickName() + " &fotrzymał prośbe o teleportacje."));

             SendMessagePacket sendMessagePacket =  new SendMessagePacket("&b&lTELEPORTACJA&8: &fOtrzymałeś prośbe o teleportacje od gracza: &3" + player.getName() + "\n" +
                        "&fAby zaakceptować prośbe o teleportacje: &b/tpaccept " + player.getName() + "\n" +
                        "&fPosiadasz łącznie już&8(&b" +  userRequest.getPlayersTeleportRequest().size() + "&7, &fprósb&8)", ReceiverType.PLAYER,
                        MessageType.CHAT);
             sendMessagePacket.setNickNameReceiver(userRequest.getNickName());
             sendMessagePacket.sendToChannel(userRequest.getNowSector());

            }, 15L);
        });
    }
}

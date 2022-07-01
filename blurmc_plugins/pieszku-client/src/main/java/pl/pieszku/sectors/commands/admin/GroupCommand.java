package pl.pieszku.sectors.commands.admin;

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
import org.pieszku.api.sector.Sector;
import org.pieszku.api.sector.SectorService;
import org.pieszku.api.service.UserService;
import org.pieszku.api.type.GroupType;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.impl.Command;
import pl.pieszku.sectors.impl.CommandInfo;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@CommandInfo(name = "group", permission = GroupType.DEVELOPER)
public class GroupCommand extends Command {


    private final UserService userService = BukkitMain.getInstance().getUserService();
    private final SectorService sectorService = BukkitMain.getInstance().getSectorService();

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;


            if (args.length < 2) {
                player.sendMessage(ChatUtilities.colored("&7Poprawne użycie: &b/group <nick> <group>"));
                return;
            }
            String nickname = args[0];
            String groupName = args[1].toUpperCase();

            if (!this.userService.findUserByNickName(nickname).isPresent()) {
                player.sendMessage(ChatUtilities.colored("&4Błąd: &cPodany gracz nigdy nie był na serwerze!"));
                return;
            }
            if (!GroupType.groupExists(groupName)) {
                player.sendMessage(ChatUtilities.colored("&4Błąd: &cPodana grupa nie istnieje!"));
                return;
            }
            this.userService.findUserByNickName(nickname).ifPresent(user -> {
                Bukkit.getScheduler().runTaskLaterAsynchronously(BukkitMain.getInstance(), () -> {


                    User userRequest = this.userService.getOrCreate(user.getNickName());
                    userRequest.setGroupType(GroupType.valueOf(groupName));
                    userRequest.setInteractSector(true);

                    new UserPlayOutInformationPacket(user.getNickName(), new Gson().toJson(userRequest))
                            .sendToChannel(userRequest.getNowSector());

                    userRequest = this.userService.getOrCreate(user.getNickName());

                    player.sendMessage(ChatUtilities.colored("&b&lRANGA&8: &fPomyślnie nadano nową range&8: " + userRequest.getGroupType().getPrefix() + " &fdla gracza: &b" + user.getNickName()));

                    SendMessagePacket sendMessagePacket = new SendMessagePacket("&b&lRANGA&8: &fNadano ci nową range: " + userRequest.getGroupType().getPrefix() + " &fprzez &3" + player.getName(), ReceiverType.PLAYER,
                            MessageType.CHAT);
                    sendMessagePacket.setNickNameReceiver(userRequest.getNickName());
                    sendMessagePacket.sendToChannel(userRequest.getNowSector());

                }, 15L);
            });
        }else{

            if (args.length < 2) {
                commandSender.sendMessage(ChatUtilities.colored("&7Poprawne użycie: &b/group <nick> <group>"));
                return;
            }
            String nickname = args[0];
            String groupName = args[1].toUpperCase();

            if (!this.userService.findUserByNickName(nickname).isPresent()) {
                commandSender.sendMessage(ChatUtilities.colored("&4Błąd: &cPodany gracz nigdy nie był na serwerze!"));
                return;
            }
            if (!GroupType.groupExists(groupName)) {
                commandSender.sendMessage(ChatUtilities.colored("&4Błąd: &cPodana grupa nie istnieje!"));
                return;
            }
            this.userService.findUserByNickName(nickname).ifPresent(user -> {
                new UserPlayOutRequestPacket(user.getNickName(), BukkitMain.getInstance().getSectorName()).sendToChannel("MASTER");
                Bukkit.getScheduler().runTaskLaterAsynchronously(BukkitMain.getInstance(), () -> {


                    User userRequest = this.userService.getOrCreate(user.getNickName());
                    userRequest.setGroupType(GroupType.valueOf(groupName));
                    userRequest.setInteractSector(true);

                    new UserPlayOutInformationPacket(user.getNickName(), new Gson().toJson(userRequest))
                            .sendToChannel(userRequest.getNowSector());

                    userRequest = this.userService.getOrCreate(user.getNickName());

                    commandSender.sendMessage(ChatUtilities.colored("&b&lRANGA&8: &fPomyślnie nadano nową range&8: " + userRequest.getGroupType().getPrefix() + " &fdla gracza: &b" + user.getNickName()));

                    SendMessagePacket sendMessagePacket = new SendMessagePacket("&b&lRANGA&8: &fNadano ci nową range: " + userRequest.getGroupType().getPrefix() + " &fprzez &3" + commandSender.getName(), ReceiverType.PLAYER,
                            MessageType.CHAT);
                    sendMessagePacket.setNickNameReceiver(userRequest.getNickName());
                    sendMessagePacket.sendToChannel(userRequest.getNowSector());

                }, 15L);
            });
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
        List<String> complete = new ArrayList<>();

        if(args.length == 1) {
            for (Sector sector : this.sectorService.getSectorList()) {
                for(String nickName : sector.getPlayers()){
                    if(nickName.toLowerCase().contains(args[0].toLowerCase())){
                        complete.add(nickName);
                    }
                }
            }
        }

        if (args.length == 2) {
            for (GroupType groupType : GroupType.values()) {
                if (groupType.name().toUpperCase().contains(args[1].toUpperCase())) {
                    complete.add(groupType.name());
                }
            }
        }
        Collections.sort(complete);
        return complete;
    }
}

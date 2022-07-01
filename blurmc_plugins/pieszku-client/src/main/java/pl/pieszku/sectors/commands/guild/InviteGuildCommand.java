package pl.pieszku.sectors.commands.guild;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pieszku.api.redis.packet.client.SendMessagePacket;
import org.pieszku.api.redis.packet.type.MessageType;
import org.pieszku.api.redis.packet.type.ReceiverType;
import org.pieszku.api.redis.packet.type.UpdateType;
import org.pieszku.api.sector.SectorService;
import org.pieszku.api.service.GuildService;
import org.pieszku.api.service.UserService;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.util.ArrayList;
import java.util.List;

public class InviteGuildCommand extends GuildSubCommand{



    private final GuildService guildService = BukkitMain.getInstance().getGuildService();
    private final UserService userService = BukkitMain.getInstance().getUserService();
    private final SectorService sectorService = BukkitMain.getInstance().getSectorService();

    public InviteGuildCommand() {
        super("zapros", "", "/g zapros <nick>", "", "inivte");
    }

    @Override
    public boolean onCommand(Player player, String[] args) {


        if(args.length < 1){
            player.sendMessage(ChatUtilities.colored("&b&lGILDIA&8: &fPoprawne uzycie: &b" + this.getUsage()));
            return false;
        }

        if(!this.guildService.findGuildByMember(player.getName()).isPresent()){
            player.sendMessage(ChatUtilities.colored("&4Błąd: &cNie posiadasz gildii!"));
            return false;
        }
        String nickName = args[0];


        if(!this.userService.findUserByNickName(nickName).isPresent()){
            player.sendMessage(ChatUtilities.colored("&4Błąd: &cPodany gracz nigdy nie był na serwerze!"));
            return false;
        }
        this.guildService.findGuildByMember(player.getName()).ifPresent(guild -> {


            if(this.guildService.findGuildByMember(nickName).isPresent()){
                player.sendMessage(ChatUtilities.colored("&4Błąd: &cPodany gracz posiada już gildię!"));
                return;
            }
            if(!guild.hasMaster(player.getName())){
                player.sendMessage(ChatUtilities.colored("&4Błąd: &cNie jesteś mistrzem!"));
                return;
            }

            if(!this.sectorService.isOnlinePlayer(nickName)){
                player.sendMessage(ChatUtilities.colored("&4Błąd: &cPodany gracz jest aktualnie offline!"));
                return;
            }
            if(guild.getMembersInvite().contains(nickName)){
                player.sendMessage(ChatUtilities.colored("&4Błąd: &cPodany gracz posiada już zaproszenie do twojej gildii!"));
                return;
            }

            guild.getMembersInvite().add(nickName);
            guild.synchronize(UpdateType.UPDATE);


            SendMessagePacket sendMessagePacket = new SendMessagePacket(
                    "&b&lGILDIA &8->> &fOtrzymałeś zaproszenie do gildii: &8[&b" + guild.getName().toUpperCase() + "&8]\n" +
                            "&b&lGILDIA &8->> &fAby dołączyć wpisz: &3/g dolacz", ReceiverType.PLAYER, MessageType.CHAT);
            sendMessagePacket.setNickNameReceiver(nickName);
            sendMessagePacket.sendToAllSectors(this.sectorService);
            player.sendMessage(ChatUtilities.colored("&b&lGILDIA &8->> &fWysłano zaproszenie do gildii dla: &3" + nickName));

        });
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return new ArrayList<>();
    }
}

package pl.pieszku.sectors.commands.guild;

import com.google.gson.Gson;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pieszku.api.API;
import org.pieszku.api.redis.packet.client.SendMessagePacket;
import org.pieszku.api.redis.packet.guild.synchronize.GuildInformationSynchronizePacket;
import org.pieszku.api.redis.packet.type.MessageType;
import org.pieszku.api.redis.packet.type.ReceiverType;
import org.pieszku.api.redis.packet.type.UpdateType;
import org.pieszku.api.service.GuildService;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.util.ArrayList;
import java.util.List;

public class DeleteGuildCommand extends GuildSubCommand{


    private final GuildService guildService = API.getInstance().getGuildService();

    public DeleteGuildCommand() {
        super("usun", "", "", "", "delete");
    }

    @Override
    public boolean onCommand(Player player, String[] args) {


        if(!this.guildService.findGuildByMember(player.getName()).isPresent()){
            player.sendMessage(ChatUtilities.colored("&4Błąd: &cNie posiadasz gildii!"));
            return false;
        }
        this.guildService.findGuildByMember(player.getName()).ifPresent(guild -> {

            if(!guild.hasOwner(player.getName())){
                player.sendMessage(ChatUtilities.colored("&4Błąd: &cNie jesteś założycielem!"));
                return;
            }
            new GuildInformationSynchronizePacket(guild.getName(), new Gson().toJson(guild), UpdateType.REMOVE).sendToChannel("MASTER");
            new SendMessagePacket("&3&lGILDIA &8:: &fGracz: &b" + player.getName() + " &fusunąl swoją gildie &8[&c" + guild.getName().toUpperCase() + "&8]",
                    ReceiverType.ALL, MessageType.CHAT).sendToAllSectors(BukkitMain.getInstance().getSectorService());

        });

        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return new ArrayList<>();
    }
}

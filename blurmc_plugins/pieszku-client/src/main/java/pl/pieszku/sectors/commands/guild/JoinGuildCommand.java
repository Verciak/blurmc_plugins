package pl.pieszku.sectors.commands.guild;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pieszku.api.redis.packet.client.SendMessagePacket;
import org.pieszku.api.redis.packet.type.MessageType;
import org.pieszku.api.redis.packet.type.ReceiverType;
import org.pieszku.api.sector.SectorService;
import org.pieszku.api.service.GuildService;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.util.ArrayList;
import java.util.List;

public class JoinGuildCommand extends GuildSubCommand{

    private final GuildService guildService = BukkitMain.getInstance().getGuildService();
    private final SectorService sectorService = BukkitMain.getInstance().getSectorService();

    public JoinGuildCommand() {
        super("dolacz", "", "", "", "join");
    }

    @Override
    public boolean onCommand(Player player, String[] args) {


        if(this.guildService.findGuildByMember(player.getName()).isPresent()){
            player.sendMessage(ChatUtilities.colored("&4Błąd: &cPosiadasz już gildię!"));
            return false;
        }

        if(!this.guildService.findGuildByInvite(player.getName()).isPresent()){
            player.sendMessage(ChatUtilities.colored("&4Błąd: &cNie posiadasz żadnego zaproszenia do gildii!"));
            return false;
        }

        this.guildService.findGuildByInvite(player.getName()).ifPresent(guild -> {
            guild.getMembersInvite().remove(player.getName());
            guild.addMember(player.getName());
            new SendMessagePacket("&b&lGILDIA &8-> &fGracz: &3" + player.getName() + " &fdołączył do gildii: &8[&b" + guild.getName().toUpperCase() + "&8]", ReceiverType.ALL, MessageType.CHAT).sendToAllSectors(this.sectorService);
        });


        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return new ArrayList<>();
    }
}

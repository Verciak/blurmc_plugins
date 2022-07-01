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

public class LeaveGuildCommand extends GuildSubCommand{


    private final SectorService sectorService = BukkitMain.getInstance().getSectorService();
    private final GuildService guildService = BukkitMain.getInstance().getGuildService();

    public LeaveGuildCommand() {
        super("opusc", "", "", "", "leave");
    }

    @Override
    public boolean onCommand(Player player, String[] args) {

        if(!this.guildService.findGuildByMember(player.getName()).isPresent()){
            player.sendMessage(ChatUtilities.colored("&4Błąd: &cNie posiadasz gildii!"));
            return false;
        }
        this.guildService.findGuildByMember(player.getName()).ifPresent(guild -> {

            if(guild.hasOwner(player.getName())){
                player.sendMessage(ChatUtilities.colored("&4Błąd: &cNie możesz opuścić własnej gildii!"));
                return;
            }
            if(guild.isMaster(player.getName())){
                guild.changeMaster("null");
            }
            if(guild.isLeader(player.getName())){
                guild.changeLeader("null");
            }
            guild.removeMember(player.getName());
            player.sendTitle(ChatUtilities.colored("&8[&c" + guild.getName().toUpperCase() + "&8]"),
                    ChatUtilities.colored("&fOd teraz nie jesteś członkiem tej gildii!"));
            new SendMessagePacket("&b&lGILDIA &8->> &fGracz: &3" + player.getName() + " &fopuscił gildie: &8[&3" + guild.getName().toUpperCase() + "&8]", ReceiverType.ALL, MessageType.CHAT).sendToAllSectors(this.sectorService);
        });

        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return new ArrayList<>();
    }
}

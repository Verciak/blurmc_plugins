package pl.pieszku.sectors.commands.guild;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pieszku.api.redis.packet.type.UpdateType;
import org.pieszku.api.sector.SectorService;
import org.pieszku.api.service.GuildService;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.util.ArrayList;
import java.util.List;

public class FriendlyFireGuildCommand extends GuildSubCommand{


    private final GuildService guildService = BukkitMain.getInstance().getGuildService();
    private final SectorService sectorService = BukkitMain.getInstance().getSectorService();


    public FriendlyFireGuildCommand() {
        super("pvp", "", "", "ff");
    }

    @Override
    public boolean onCommand(Player player, String[] args) {

        if(!this.guildService.findGuildByMember(player.getName()).isPresent()){
            player.sendMessage(ChatUtilities.colored("&4Błąd: &cNie posiadasz gildii!"));
            return false;
        }
        this.guildService.findGuildByMember(player.getName()).ifPresent(guild -> {

            if(!guild.hasMaster(player.getName())){
                player.sendMessage(ChatUtilities.colored("&4Błąd: &cNie jesteś mistrzem!"));
                return;
            }
            guild.setFriendlyFire(!guild.isFriendlyFire());
            guild.synchronize(UpdateType.UPDATE);

            guild.sendMessage("&bGILDIA&8(&3&lPVP&8) &8->> &fZostało: " + (guild.isFriendlyFire() ? "&awłączone" : "&cwyłączone"), this.sectorService);
        });

        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return new ArrayList<>();
    }
}

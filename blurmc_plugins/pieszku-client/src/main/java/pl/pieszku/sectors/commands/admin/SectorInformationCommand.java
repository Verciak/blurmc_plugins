package pl.pieszku.sectors.commands.admin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pieszku.api.sector.Sector;
import org.pieszku.api.sector.SectorService;
import org.pieszku.api.type.GroupType;
import org.pieszku.api.utilities.DataUtilities;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.impl.Command;
import pl.pieszku.sectors.impl.CommandInfo;
import pl.pieszku.sectors.service.MasterConnectionHeartbeatService;
import pl.pieszku.sectors.utilities.ChatUtilities;

@CommandInfo(name = "sector", permission = GroupType.ADMIN)
public class SectorInformationCommand extends Command {


    private final SectorService sectorService = BukkitMain.getInstance().getSectorService();
    private final MasterConnectionHeartbeatService masterConnectionHeartbeatService = BukkitMain.getInstance().getMasterConnectionHeartbeatService();

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        Player player = (Player) commandSender;


        player.sendMessage(" ");
        player.sendMessage(ChatUtilities.colored( "     &7SERWER GŁÓWNY: " + (masterConnectionHeartbeatService.isConnected() ? "&a&l&nSTABILNIE" : "&c&l&nAWARIA")));
        for (Sector sector : this.sectorService.getSectorList()) {
            player.sendMessage(ChatUtilities.colored((sector.isOnline() ? "&a&l•" : "&c&l•") + " " + sector.getName().toUpperCase() + " &8:: " + (sector.isOnline() ? "&aONLINE" : "&cOFFLINE") + " &8:: &a" + sector.getPlayers().size() + " online" +
                    " &8:: &flastSync&8(&c" + DataUtilities.getTimeToString(sector.getLatestInformation()) + "&8)"));
        }

    }
}

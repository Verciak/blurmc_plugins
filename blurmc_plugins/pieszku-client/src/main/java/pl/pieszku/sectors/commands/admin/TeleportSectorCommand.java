package pl.pieszku.sectors.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pieszku.api.sector.Sector;
import org.pieszku.api.sector.SectorService;
import org.pieszku.api.type.GroupType;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.impl.Command;
import pl.pieszku.sectors.impl.CommandInfo;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CommandInfo(name = "tpsector", permission = GroupType.HELPER)
public class TeleportSectorCommand extends Command {


    private final SectorService sectorService = BukkitMain.getInstance().getSectorService();

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        Player player = (Player) commandSender;

        if (args.length < 1) {
            player.sendMessage(ChatUtilities.colored("&7Poprawne użycie: &b/tpsector <name>"));
            return;
        }

        String sectorName = args[0];


        if(!this.sectorService.findSectorByName(sectorName).isPresent()){
            player.sendMessage(ChatUtilities.colored("&4Błąd: &cPodany sektor nie istnieje!"));
            return;
        }

        this.sectorService.findSectorByName(sectorName).ifPresent(sector -> {
            if(!sector.isOnline()){
                player.sendMessage(ChatUtilities.colored("&4Błąd: &cPodany sektor jest aktualnie offline!"));
                return;
            }

            Location location = new Location(Bukkit.getWorld(sector.getLocationMinimum().getWorld()), (sector.getLocationMinimum().getX() + sector.getLocationMaximum().getX()) / 2, 80, (sector.getLocationMinimum().getZ() + sector.getLocationMaximum().getZ()) / 2);
            player.teleport(location);
        });

    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
        List<String> complete = new ArrayList<>();
        if (args.length == 1) {
            for (Sector sector : this.sectorService.getSectorList()) {
                if (sector.getName().toUpperCase().contains(args[0].toUpperCase())) {
                    complete.add(sector.getName());
                }
            }
        }
        Collections.sort(complete);
        return complete;
    }
}

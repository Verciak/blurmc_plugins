package pl.pieszku.sectors.commands.admin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pieszku.api.API;
import org.pieszku.api.sector.Sector;
import org.pieszku.api.sector.SectorService;
import org.pieszku.api.service.BackupService;
import org.pieszku.api.type.GroupType;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.impl.Command;
import pl.pieszku.sectors.impl.CommandInfo;
import pl.pieszku.sectors.inventory.admin.BackupInventory;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CommandInfo(name = "backup", permission = GroupType.MODERATOR)
public class BackupCommand extends Command {

    private final BackupService backupService = API.getInstance().getBackupService();
    private final SectorService sectorService = BukkitMain.getInstance().getSectorService();
    private final BackupInventory backupInventory = new BackupInventory();

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        Player player = (Player) commandSender;

        if(args.length < 1){
            player.sendMessage(ChatUtilities.colored("&7Poprawne użycie: &b/backup <nick>"));
            return;
        }
        String nickName = args[0];
        if(!this.backupService.findBackupByNickName(nickName).isPresent()){
            player.sendMessage(ChatUtilities.colored("&4Błąd: &cPodany gracz nie posiada żadnego backup!"));
            return;
        }
        this.backupService.findBackupByNickName(nickName).ifPresent(backup -> {
        this.backupInventory.show(player, backup.getNickName());
        });
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
        Collections.sort(complete);
        return complete;
    }
}

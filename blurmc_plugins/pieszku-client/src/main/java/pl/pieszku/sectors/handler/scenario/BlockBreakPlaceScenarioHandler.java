package pl.pieszku.sectors.handler.scenario;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.pieszku.api.sector.SectorService;
import org.pieszku.api.service.UserService;
import org.pieszku.api.type.GroupType;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.utilities.ChatUtilities;

public class BlockBreakPlaceScenarioHandler implements Listener {


    private final UserService userService = BukkitMain.getInstance().getUserService();
    private final SectorService sectorService = BukkitMain.getInstance().getSectorService();

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent event) {
        this.userService.findUserByNickName(event.getPlayer().getName()).ifPresent(user -> {

            if (!user.isInteractSector()) {
                event.setCancelled(true);
                return;
            }

            if(BukkitMain.getInstance().getCurrentSector().get().isSpawn() && !GroupType.hasPermission(user, GroupType.HEADADMIN)){
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatUtilities.colored("&4Błąd: &cNa spawnie nie można tego zrobić!"));
                return;
            }

            Block block = event.getBlock();
            this.sectorService.getSectorByLocation(block.getWorld().getName(), block.getLocation().getBlockX(), block.getLocation().getBlockZ()).ifPresent(sector -> {
                if(sector.getDistanceToBorder(block.getLocation().getBlockX(), block.getLocation().getBlockZ()) <= 20){
                    event.getPlayer().sendMessage(ChatUtilities.colored("&4Błąd: &cPrzy granicy sektora nie można budować!"));
                    event.setCancelled(true);
                }
            });
        });
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockPlace(BlockPlaceEvent event){
        this.userService.findUserByNickName(event.getPlayer().getName()).ifPresent(user -> {
            if(!user.isInteractSector()){
                event.setCancelled(true);
                return;
            }
            if(BukkitMain.getInstance().getCurrentSector().get().isSpawn() && !GroupType.hasPermission(user, GroupType.HEADADMIN)){
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatUtilities.colored("&4Błąd: &cNa spawnie nie można tego zrobić!"));
                return;
            }
            Block block = event.getBlock();
            this.sectorService.getSectorByLocation(block.getWorld().getName(), block.getLocation().getBlockX(), block.getLocation().getBlockZ()).ifPresent(sector -> {
                if(sector.getDistanceToBorder(block.getLocation().getBlockX(), block.getLocation().getBlockZ()) <= 20){
                    event.getPlayer().sendMessage(ChatUtilities.colored("&4Błąd: &cPrzy granicy sektora nie można budować!"));
                    event.setCancelled(true);
                }
            });
        });
    }
}

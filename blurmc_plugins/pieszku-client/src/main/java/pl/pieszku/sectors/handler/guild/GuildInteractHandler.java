package pl.pieszku.sectors.handler.guild;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.pieszku.api.service.GuildService;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.utilities.ChatUtilities;

public class GuildInteractHandler implements Listener {

    private final GuildService guildService = BukkitMain.getInstance().getGuildService();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInteract(PlayerInteractEvent event){
        if(event.isCancelled())return;
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        this.guildService.findGuildByLocation(block.getWorld().getName(), block.getX(), block.getZ()).ifPresent(guild -> {
            if(event.getAction() == Action.RIGHT_CLICK_BLOCK && player.getItemInHand().getType().equals(Material.FLINT_AND_STEEL)){
                if(!guild.hasMember(player.getName())){
                    player.sendMessage(ChatUtilities.colored("&4Błąd: &cTeren należy do wrogiej gildii: &8[&c" + guild.getName().toUpperCase() + "&8]"));
                    event.setCancelled(true);
                    return;
                }
            }
        });
    }

}

package pl.pieszku.sectors.handler.guild;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.pieszku.api.data.guild.GuildPermissionJson;
import org.pieszku.api.data.guild.PermissionGuildActionType;
import org.pieszku.api.data.guild.PermissionGuildData;
import org.pieszku.api.objects.guild.GuildPermission;
import org.pieszku.api.service.GuildService;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.utilities.ChatUtilities;

public class GuildBlockBreakPlaceHandler implements Listener {


    private final GuildService guildService = BukkitMain.getInstance().getGuildService();
    private final GuildPermissionJson guildPermissionJson = BukkitMain.getInstance().getConfigurationData().getGuildPermissionJson();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();

        this.guildService.findGuildByLocation(block.getWorld().getName(), block.getX(), block.getZ()).ifPresent(guild -> {


            if(!guild.hasMember(player.getName())) {
                player.sendMessage(ChatUtilities.colored("&4Błąd: &cTeren należy do wrogiej gildii: &8[&c" + guild.getName().toUpperCase() + "&8]"));
                event.setCancelled(true);
                return;
            }
            GuildPermission permissionGuild = guild.findGuildPermissionByNickName(player.getName());
            if(permissionGuild == null)return;
            boolean status = true;
            for (PermissionGuildData guildPermission : this.guildPermissionJson.getPermissionData()) {
                if(!guildPermission.getActionType().equals(PermissionGuildActionType.BREAK))continue;
                if(!event.getBlock().getType().equals(Material.valueOf(guildPermission.getMaterial()))) continue;
                if(permissionGuild.hasPermissionDisable(guildPermission.getId())) status = false;
            }
            if(!status){
                event.setCancelled(true);
                player.sendMessage(ChatUtilities.colored("&4Blad: &cNie masz dostępu aby niszczyć ten blok na terenie gildii poproś kogoś!"));
            }
        });
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockPlace(BlockPlaceEvent event){
        if(event.isCancelled())return;
        Player player = event.getPlayer();
        Block block = event.getBlock();

        this.guildService.findGuildByLocation(block.getWorld().getName(), block.getX(), block.getZ()).ifPresent(guild -> {

            if(!guild.hasMember(player.getName())) {
                player.sendMessage(ChatUtilities.colored("&4Błąd: &cTeren należy do wrogiej gildii: &8[&c" + guild.getName().toUpperCase() + "&8]"));
                event.setCancelled(true);
                return;
            }
            GuildPermission permissionGuild = guild.findGuildPermissionByNickName(player.getName());
            if(permissionGuild == null)return;
            boolean status = true;
            for (PermissionGuildData guildPermission : this.guildPermissionJson.getPermissionData()) {
                if(!guildPermission.getActionType().equals(PermissionGuildActionType.PLACE))continue;
                if(!event.getBlock().getType().equals(Material.valueOf(guildPermission.getMaterial()))) continue;
                if(permissionGuild.hasPermissionDisable(guildPermission.getId())) status = false;
            }
            if(!status){
                event.setCancelled(true);
                player.sendMessage(ChatUtilities.colored("&4Blad: &cNie masz dostępu aby niszczyć ten blok na terenie gildii poproś kogoś!"));
            }
        });
    }
}

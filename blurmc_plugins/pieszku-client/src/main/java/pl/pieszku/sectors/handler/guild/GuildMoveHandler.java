package pl.pieszku.sectors.handler.guild;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.pieszku.api.objects.guild.impl.Guild;
import org.pieszku.api.objects.user.User;
import org.pieszku.api.sector.SectorService;
import org.pieszku.api.service.GuildService;
import org.pieszku.api.service.UserService;
import org.pieszku.api.type.GroupType;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.util.Optional;

public class GuildMoveHandler implements Listener {


    private final GuildService guildService = BukkitMain.getInstance().getGuildService();
    private final UserService userService = BukkitMain.getInstance().getUserService();
    private final SectorService sectorService = BukkitMain.getInstance().getSectorService();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onMove(PlayerMoveEvent event) {
        if (event.isCancelled()) return;

        Player player = event.getPlayer();
        Location from = event.getFrom();
        Location to = event.getTo();


        if (from.getBlockX() == to.getBlockX() && from.getBlockY() == to.getBlockY() && from.getBlockZ() == to.getBlockZ()) return;
        Optional<Guild> guildFrom = this.guildService.findGuildByLocation(from.getWorld().getName(), from.getBlockX(), from.getBlockZ());
        Optional<Guild> guildTo = this.guildService.findGuildByLocation(to.getWorld().getName(), to.getBlockX(), to.getBlockZ());
        User user = this.userService.getOrCreate(player.getName());

        if (!guildFrom.isPresent()) {
            guildTo.ifPresent(guild -> {
                if (guild.hasMember(player.getName())) {
                    player.sendTitle(ChatUtilities.colored("&8[&a" + guild.getName().toUpperCase() + "&8]"),
                            ChatUtilities.colored("&a&l• &aWkroczyłeś na teren swojej gildii &a&l•"));
                    return;
                }
                player.sendTitle(ChatUtilities.colored("&8[&c" + guild.getName().toUpperCase() + "&8]"),
                        ChatUtilities.colored("&c&l• &aWkroczyłeś na teren wrogiej gildii &c&l•"));

                if (!GroupType.hasPermission(user, GroupType.HELPER)) {
                    guild.sendMessage("&c&l• &cWróg wkorczył na teren twojej gildii &c&l•", this.sectorService);
                }
            });
        }
        if (!guildTo.isPresent()) {
            guildFrom.ifPresent(guild -> {
                if (guild.hasMember(player.getName())) {
                    player.sendTitle(ChatUtilities.colored("&8[&a" + guild.getName().toUpperCase() + "&8]"),
                            ChatUtilities.colored("&a&l• &aOpuściłeś teren swojej gildii &a&l•"));
                    return;
                }
                player.sendTitle(ChatUtilities.colored("&8[&c" + guild.getName().toUpperCase() + "&8]"),
                        ChatUtilities.colored("&c&l• &aOpusciłeś teren wrogiej gildii &c&l•"));

                if (!GroupType.hasPermission(user, GroupType.HELPER)) {
                    guild.sendMessage("&c&l• &cWróg opuścił teren twojej gildii &c&l•", this.sectorService);
                }
            });
        }
    }
}
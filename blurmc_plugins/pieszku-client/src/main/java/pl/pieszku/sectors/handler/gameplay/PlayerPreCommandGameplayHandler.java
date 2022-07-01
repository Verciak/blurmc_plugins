package pl.pieszku.sectors.handler.gameplay;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.pieszku.api.API;
import org.pieszku.api.service.GuildService;
import org.pieszku.api.service.UserService;
import org.pieszku.api.type.GroupType;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.util.Arrays;
import java.util.List;

public class PlayerPreCommandGameplayHandler implements Listener {

    private final UserService userService = BukkitMain.getInstance().getUserService();
    private final GuildService guildService = API.getInstance().getGuildService();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerPreCommand(PlayerCommandPreprocessEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();

        this.userService.findUserByNickName(player.getName()).ifPresent(user -> {

            List<String> combats = Arrays.asList("/spawn", "/tpa", "/tpaccept", "/kit", "/schowek", "/home", "/ec", "/repair", "/repairall", "/g", "/baza", "/sethome", "/panel", "/uprawnienia", "/g panel", "/g baza", "/enderchest", "/zadania", "/eventy", "/event", "/zadanie", "/events", "/sklep", "/tpaccept", "/tpa");
            if (!GroupType.hasPermission(user, GroupType.HELPER)) {
                if (!user.getUserAntiLogout().hasAntiLogout()) {
                    for (String i2 : combats) {
                        if (event.getMessage().toLowerCase().contains(i2)) {
                            player.sendMessage(ChatUtilities.colored("&4Błąd: &cPodczas walki nie możesz tego zrobić!"));
                            event.setCancelled(true);
                            return;
                        }
                    }
                }
                this.guildService.findGuildByLocation(player.getWorld().getName(), player.getLocation().getBlockX(), player.getLocation().getBlockZ()).ifPresent(guild -> {
                    List<String> commandGuild = Arrays.asList("/tpa", "/home", "/sethome", "/sklep", "/tpaccept");
                    if (!guild.hasMember(player.getName())) {
                        for (String command : commandGuild) {
                            if (event.getMessage().toLowerCase().contains(command)) {
                                player.sendMessage(ChatUtilities.colored("&4Błąd: &cNa terenie wrogiej gildi nie możesz użyć tej komendy!"));
                                event.setCancelled(true);
                                return;
                            }
                        }
                    }
                });
            }
        });
    }
}

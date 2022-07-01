package pl.pieszku.sectors.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.pieszku.api.serializer.LocationSerializer;
import org.pieszku.api.service.UserService;
import org.pieszku.api.type.GroupType;
import org.pieszku.api.type.TimeType;
import org.pieszku.api.utilities.DataUtilities;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.cache.BukkitCache;
import pl.pieszku.sectors.impl.Command;
import pl.pieszku.sectors.impl.CommandInfo;
import pl.pieszku.sectors.impl.UserActionBarType;
import pl.pieszku.sectors.utilities.ChatUtilities;

@CommandInfo(name = "spawn")
public class SpawnCommand extends Command {

    private final BukkitCache bukkitCache = BukkitMain.getInstance().getBukkitCache();
    private final UserService userService = BukkitMain.getInstance().getUserService();

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        Player player = (Player) commandSender;
        this.userService.findUserByNickName(player.getName()).ifPresent(user -> {

            if(!GroupType.hasPermission(user, GroupType.HELPER)){
                this.teleport(player, new LocationSerializer("world", 0, 80, 0));
                return;
            }
            player.teleport(new Location(Bukkit.getWorld("world"), 0, 80, 0));
            player.sendTitle(ChatUtilities.colored("&3&lSPAWN"), ChatUtilities.colored("&fZostałeś pomyślnie przeteleportowany."));
        });


    }
    public void teleport(Player player, LocationSerializer locationSerializer){
        new BukkitRunnable(){

            long time = System.currentTimeMillis() + TimeType.SECOND.getTime(10);
            Location location = player.getLocation();

            @Override
            public void run() {
                bukkitCache.findBukkitUserByNickName(player.getName()).ifPresent(bukkitUser -> {
                    if (time <= System.currentTimeMillis()) {
                        player.teleport(new Location(Bukkit.getWorld(locationSerializer.getWorld()), locationSerializer.getX(), locationSerializer.getY(), locationSerializer.getZ()));
                        this.cancel();
                        bukkitUser.removeActionBar(UserActionBarType.TELEPORT);
                        return;
                    }
                    if (move(location, player.getLocation())) {
                        player.sendTitle(ChatUtilities.colored("&3&lTELEPORTACJA"),
                                ChatUtilities.colored("&fBłąd podczas teleportacji nie można się poruszać"));
                        this.cancel();
                        bukkitUser.removeActionBar(UserActionBarType.TELEPORT);
                        return;
                    }
                    bukkitUser.updateActionBar(UserActionBarType.TELEPORT, " &3&lTELEPORTACJA&8: &fZostaniesz przeteleportowany za: &b" + DataUtilities.getTimeToString(time) + " ");
                });
            }
        }.runTaskTimer(BukkitMain.getInstance(), 0, 5);
    }
    public static boolean move(Location l, Location x) {
        return l.getBlockX() != x.getBlockX() || l.getBlockY() != x.getBlockY() || l.getBlockZ() != x.getBlockZ();
    }
}

package pl.pieszku.sectors.redis.client.teleport;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.client.teleport.TeleportPlayerInformationPacket;
import org.pieszku.api.utilities.DataUtilities;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.cache.BukkitCache;
import pl.pieszku.sectors.impl.UserActionBarType;
import pl.pieszku.sectors.utilities.ChatUtilities;

public class TeleportPlayerInformationHandler extends RedisListener<TeleportPlayerInformationPacket> {

    private final BukkitCache bukkitCache = BukkitMain.getInstance().getBukkitCache();

    public TeleportPlayerInformationHandler() {
        super("SECTORS", TeleportPlayerInformationPacket.class);
    }

    @Override
    public void onDecode(TeleportPlayerInformationPacket packet) {
        Player player = Bukkit.getPlayerExact(packet.getNickNameTargetTo());
        if(player == null)return;

        new BukkitRunnable(){

            long time = packet.getTeleportDelay();
            Location location = player.getLocation();

            @Override
            public void run() {
                bukkitCache.findBukkitUserByNickName(player.getName()).ifPresent(bukkitUser -> {

                    if (time <= System.currentTimeMillis()) {
                        player.teleport(new Location(Bukkit.getWorld(packet.getWorld()), packet.getX(), packet.getY(), packet.getZ()));
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

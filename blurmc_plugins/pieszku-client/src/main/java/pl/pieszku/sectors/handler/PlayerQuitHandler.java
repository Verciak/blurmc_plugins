package pl.pieszku.sectors.handler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.pieszku.api.redis.packet.client.SendMessagePacket;
import org.pieszku.api.redis.packet.type.MessageType;
import org.pieszku.api.redis.packet.type.ReceiverType;
import org.pieszku.api.service.UserService;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.cache.BukkitCache;
import pl.pieszku.sectors.cache.BukkitUser;
import pl.pieszku.sectors.service.TeamService;
import pl.pieszku.sectors.transfer.TransferPlayerSectorUtilities;

import java.util.Optional;

public class PlayerQuitHandler implements Listener {

    private final UserService userService = BukkitMain.getInstance().getUserService();
    private final BukkitCache bukkitCache = BukkitMain.getInstance().getBukkitCache();
    private final TeamService teamService = BukkitMain.getInstance().getTeamService();

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        event.setQuitMessage(null);
        Player player = event.getPlayer();

        Optional<BukkitUser> bukkitUserOptional = this.bukkitCache.findBukkitUserByNickName(player.getName());
        bukkitUserOptional.ifPresent(this.bukkitCache::delete);
        this.teamService.removeNameTag(player);


        this.userService.findUserByNickName(event.getPlayer().getName()).ifPresent(user -> {


            if(!user.getUserAntiLogout().hasAntiLogout()){
                Player playerKiller = Bukkit.getPlayerExact(user.getUserAntiLogout().getAttackerNickName());
                user.getUserAntiLogout().setAntiLogoutTime(0);
                if(playerKiller != null) {
                    player.damage(100, playerKiller);
                }
                new SendMessagePacket("&4&lWALKA &8:: &fGracz: &c" + player.getName() + " &fwylogował się podczas walki!",
                        ReceiverType.ALL, MessageType.CHAT).sendToAllSectors(BukkitMain.getInstance().getSectorService());
                player.setHealth(0);
            }

            if(user.getChangeSector() <= System.currentTimeMillis()){
                TransferPlayerSectorUtilities.saveUserInSector(event.getPlayer(), user, event.getPlayer().getLocation(), BukkitMain.getInstance().getCurrentSector().get());
            }
        });
    }
}

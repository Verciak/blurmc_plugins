package pl.pieszku.sectors.handler;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.pieszku.api.objects.guild.impl.Guild;
import org.pieszku.api.serializer.LocationSerializer;
import org.pieszku.api.service.GuildService;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.util.Optional;

public class CraftingiHandler implements Listener {

    private final GuildService guildService = BukkitMain.getInstance().getApi().getGuildService();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlaces(BlockPlaceEvent e) {
        if(e.isCancelled())return;
        Player p = e.getPlayer();
        if (p.getItemInHand() == null) {
            return;
        }
        if (!p.getItemInHand().hasItemMeta()) {
            return;
        }
        Block b = e.getBlock();
        final Location l = e.getBlock().getLocation();
        if (p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtilities.colored("&b&lBoyFarmer"))) {

            Optional<Guild> guildOptional = guildService.findGuildByLocation(p.getLocation().getWorld().getName(), p.getLocation().getBlockX(), p.getLocation().getBlockZ());

            if (!guildOptional.isPresent()) {
                p.sendMessage(ChatUtilities.colored("&4Blad: &cTen blok mozna stawiac tylko na terenie twojej gildii!"));
                e.setCancelled(true);
                return;
            }

            guildOptional.ifPresent(guild -> {
                if (!guild.hasMember(p.getName())) {
                    p.sendMessage(ChatUtilities.colored("&4Blad: &cTo jest teren wrogiej gildi!"));
                    e.setCancelled(true);
                    return;
                }
                for (int i = 0; i != -1; ++i) {
                    final Location loc = l.add(0.0, -1.0, 0.0);
                    Block b1 = loc.getBlock();
                    if (b1.getType() == Material.BEDROCK) {
                        break;
                    }
                    if (guild.isInCentrum(new LocationSerializer(b.getWorld().getName(), b.getLocation().getBlockX(), b.getLocation().getBlockY(), b.getLocation().getBlockZ()), 7, 2, 4)) {
                        p.sendMessage(ChatUtilities.colored("&4Blad: &cW środku serca gildyjnego budowanie jest zablokowane!"));
                        e.setCancelled(true);
                        return;
                    }
                    b1.setType(Material.OBSIDIAN);
                }
            });
        }
        if (p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtilities.colored("&e&lSandFarmer"))) {
            Optional<Guild> guildOptional = guildService.findGuildByLocation(p.getLocation().getWorld().getName(), p.getLocation().getBlockX(), p.getLocation().getBlockZ());

            if (!guildOptional.isPresent()) {
                p.sendMessage(ChatUtilities.colored("&4Blad: &cTen blok mozna stawiac tylko na terenie twojej gildii!"));
                e.setCancelled(true);
                return;
            }

            guildOptional.ifPresent(guild -> {
                if (!guild.hasMember(p.getName())) {
                    p.sendMessage(ChatUtilities.colored("&4Blad: &cTo jest teren wrogiej gildi!"));
                    e.setCancelled(true);
                    return;
                }
                for (int i = 0; i != -1; ++i) {
                    final Location loc = l.add(0.0, -1.0, 0.0);
                    Block b1 = loc.getBlock();
                    if (guild.isInCentrum(new LocationSerializer(b1.getWorld().getName(), b1.getLocation().getBlockX(), b1.getLocation().getBlockY(), b1.getLocation().getBlockZ()), 7, 2, 4)) {
                        p.sendMessage(ChatUtilities.colored("&4Blad: &cW środku serca gildyjnego budowanie jest zablokowane!"));
                        e.setCancelled(true);
                        return;
                    }
                    if (b1.getType() == Material.BEDROCK) {
                        break;
                    }
                    b1.setType(Material.SAND);
                }
            });
        }
        if (p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtilities.colored("&f&lKopaczFosy"))) {
            e.getBlockPlaced().setType(Material.AIR);
            (new BukkitRunnable() {
                int i = 0;

                @Override
                public void run() {
                    Optional<Guild> guildOptional = guildService.findGuildByLocation(p.getLocation().getWorld().getName(), p.getLocation().getBlockX(), p.getLocation().getBlockZ());

                    if (!guildOptional.isPresent()) {
                        p.sendMessage(ChatUtilities.colored("&4Blad: &cTen blok mozna stawiac tylko na terenie twojej gildii!"));
                        e.setCancelled(true);
                        this.cancel();
                        return;
                    }

                    guildOptional.ifPresent(guild -> {
                        if (!guild.hasMember(p.getName())) {
                            p.sendMessage(ChatUtilities.colored("&4Blad: &cTo jest teren wrogiej gildi!"));
                            e.setCancelled(true);
                            this.cancel();
                            return;
                        }
                        Block b = e.getBlockPlaced().getLocation().subtract(0, ++i, 0).getBlock();
                        if (guild.isInCentrum(new LocationSerializer(b.getWorld().getName(), b.getLocation().getBlockX(), b.getLocation().getBlockY(), b.getLocation().getBlockZ()), 7, 2, 4)) {
                            p.sendMessage(ChatUtilities.colored("&4Blad: &cW środku serca gildyjnego budowanie jest zablokowane!"));
                            e.setCancelled(true);
                            this.cancel();
                            return;
                        }
                        if (b.getType() == Material.BEDROCK) {
                            this.cancel();
                        } else {
                            b.setType(Material.AIR);
                        }
                    });
                }
            }).runTaskTimer(BukkitMain.getInstance(), 0, 5);
        }
    }
}
  
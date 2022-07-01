package pl.pieszku.sectors.commands.guild;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.pieszku.api.service.GuildService;
import org.pieszku.api.type.TimeType;
import org.pieszku.api.utilities.DataUtilities;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.util.ArrayList;
import java.util.List;

import static pl.pieszku.sectors.redis.client.teleport.TeleportPlayerInformationHandler.move;

public class HomeGuildCommand extends GuildSubCommand{

    private final GuildService guildService = BukkitMain.getInstance().getGuildService();

    public HomeGuildCommand() {
        super("home", "", "", "", "baza", "dom");
    }

    @Override
    public boolean onCommand(Player player, String[] args) {

        if (!this.guildService.findGuildByMember(player.getName()).isPresent()) {
            player.sendMessage(ChatUtilities.colored("&4Błąd: &cNie posiadasz gildii!"));
            return false;
        }


        this.guildService.findGuildByMember(player.getName()).ifPresent(guild -> {

            new BukkitRunnable() {

                long time = System.currentTimeMillis() + TimeType.SECOND.getTime(5);
                Location playerLocation = player.getLocation();
                Location location = new Location(Bukkit.getWorld(guild.getLocationSerializerHome().getWorld()), guild.getLocationSerializerHome().getX(), guild.getLocationSerializerHome().getY(), guild.getLocationSerializerHome().getZ());

                @Override
                public void run() {
                    if (time <= System.currentTimeMillis()) {
                        player.teleport(location);
                        this.cancel();
                        return;
                    }
                    if (move(playerLocation, player.getLocation())) {
                        player.sendTitle(ChatUtilities.colored("&3&lTELEPORTACJA"),
                                ChatUtilities.colored("&fBłąd podczas teleportacji nie można się poruszać"));
                        this.cancel();
                        return;
                    }
                    ChatUtilities.sendActionBar(player, "&3&lTELEPORTACJA&8: &fZostaniesz przeteleportowany za: &b" + DataUtilities.getTimeToString(time));
                }
            }.runTaskTimer(BukkitMain.getInstance(), 0, 5);
        });
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return new ArrayList<>();
    }
}

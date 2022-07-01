package pl.pieszku.sectors.runnable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;
import org.pieszku.api.service.GuildService;
import org.pieszku.api.utilities.DataUtilities;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.cache.BukkitCache;
import pl.pieszku.sectors.helper.SendType;
import pl.pieszku.sectors.impl.HeadType;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class ArmorStandBossBarUpdateRunnable implements Runnable {


    private final GuildService guildService = BukkitMain.getInstance().getGuildService();
    private final BukkitCache bukkitCache = BukkitMain.getInstance().getBukkitCache();
    private static float yaw =0 ;
    private static double height = (float) -0.25;
    private static boolean add = true;

    public void start() {
        Bukkit.getScheduler().runTaskTimer(BukkitMain.getInstance(), this, 0, 0);
    }

    @Override
    public void run() {
        yaw = (float) (yaw + 3.16);
        if (yaw >= 360) yaw = 0;
        height = height + (add ? 0.025 : -0.025);
        if (height >= 0.25 || height <= -0.25) add = !add;

        Bukkit.getOnlinePlayers().forEach(player -> {
            this.bukkitCache.findBukkitUserByNickName(player.getName()).ifPresent(bukkitUser -> {

                if(!this.guildService.findGuildByLocation(player.getWorld().getName(), player.getLocation().getBlockX(), player.getLocation().getBlockZ()).isPresent()){
                   BossBar bossBar = bukkitUser.getBossBar().get(1);
                   if(bossBar.getPlayers().contains(player)) {
                       bossBar.removePlayer(player);
                   }
                }

                this.guildService.findGuildByLocation(player.getWorld().getName(), player.getLocation().getBlockX(), player.getLocation().getBlockZ()).ifPresent(guild -> {

                    Location locationGuild = new Location(Bukkit.getWorld("world"), guild.getLocationSerializer().getX(),38, guild.getLocationSerializer().getZ());


                    double progress = locationGuild.distance(player.getLocation()) / guild.getLocationSerializer().getSize();
                    BossBar bossBar = bukkitUser.getBossBar().get(1);
                    bossBar.setColor((guild.hasMember(player.getName()) ? BarColor.GREEN : BarColor.RED));
                    bossBar.setProgress((progress > 1) ? 1 : progress);

                    bossBar.setTitle((guild.hasMember(player.getName()) ?
                            ChatUtilities.colored("&aZnajdujesz się na ternie swojej gildii&8(&a" + guild.getName().toUpperCase() + "&f, &a" +
                                    new DecimalFormat("##.##").format(player.getLocation().distance(locationGuild))  + "m&8)") :

                            ChatUtilities.colored("&cZnajdujesz się na ternie wrogiej gildii&8(&c" + guild.getName().toUpperCase() + "&f, &c" +
                                    new DecimalFormat("##.##").format(player.getLocation().distance(locationGuild)) + "m&8)")));

                    if(!bossBar.getPlayers().contains(player)){
                        bossBar.addPlayer(player);
                    }

                    bukkitUser.getArmorStandHelper().apply(armorStandHelper -> {
                        if (player.getLocation().distance(locationGuild) <= 10) {
                            armorStandHelper.setDisplayName(Arrays.asList(
                                    "&aOchrona&8(&a" + DataUtilities.getTimeToString(guild.getProtection()) + "&8)",
                                    "&fŻycia&8(" + multiplyLives(guild.getLife()) + "&8)",
                                    "&fZdrowie&8(&c" + guild.getHp() + "&7/&a100&8)",
                                    "&8[" + (guild.hasMember(player.getName()) ? "&a" : "&c") + guild.getName().toUpperCase() + "&8]"));


                            armorStandHelper.setLocation(locationGuild);
                            Location location = armorStandHelper.getLocation();
                            location.setYaw(yaw);
                            location.setPitch(yaw - player.getLocation().getPitch());
                            location.setY(37 + height);
                            armorStandHelper.send(SendType.UPDATE, guild.getLocationSerializer(), HeadType.HEAD_SIX.getUrl());
                        }else{
                            armorStandHelper.send(SendType.REMOVE, guild.getLocationSerializer(), "");
                            armorStandHelper.setEntity(new ArrayList<>());
                        }
                    });
                });
            });
        });
    }
    public String multiplyLives(int lives){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < lives; i++){
            stringBuilder.append("&c❤");
        }
        return stringBuilder.toString();
    }
}

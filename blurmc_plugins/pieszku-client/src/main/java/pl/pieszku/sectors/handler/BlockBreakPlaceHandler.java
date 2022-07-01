package pl.pieszku.sectors.handler;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.pieszku.api.data.ConfigurationData;
import org.pieszku.api.data.drop.Drop;
import org.pieszku.api.serializer.LocationSerializer;
import org.pieszku.api.service.UserService;
import org.pieszku.api.type.TimeType;
import org.pieszku.api.utilities.DataUtilities;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.cache.BukkitCache;
import pl.pieszku.sectors.helper.ItemHelper;
import pl.pieszku.sectors.helper.SendType;
import pl.pieszku.sectors.utilities.ChatUtilities;
import pl.pieszku.sectors.utilities.RandomUtilities;

import java.util.ArrayList;
import java.util.Arrays;

public class BlockBreakPlaceHandler implements Listener {


    private final ConfigurationData configurationData = BukkitMain.getInstance().getConfigurationData();
    private final UserService userService = BukkitMain.getInstance().getUserService();
    private final BukkitCache bukkitCache = BukkitMain.getInstance().getBukkitCache();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (block.getType() == Material.STONE) {
            block.setType(Material.AIR);
            if (player.getGameMode().equals(GameMode.CREATIVE)) return;
            this.userService.findUserByNickName(player.getName()).ifPresent(user -> {
                for (Drop drop : this.configurationData.getStoneDropJson().getDrops()) {
                    if (!RandomUtilities.getChance(drop.getChance())) continue;
                    if (user.hasDisable(drop.getId())) continue;
                    int randomAmount = RandomUtilities.getRandInt(1, 3);

                    user.setExpMiner(user.getExpMiner() + 0.003);
                    if(user.getExpMiner() >= 1){
                        user.setExpMiner(0);
                        user.setLevelMiner(user.getLevelMiner() + 1);
                        player.sendTitle(ChatUtilities.colored("&e&l:: &6&lPOZIOM &e&l::"),
                                ChatUtilities.colored("&6&l:: &eGratulacje awansowałeś na: &6" + user.getLevelMiner() + " &epoziom &6&l::"));
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 10);
                    }

                    if (Material.getMaterial(drop.getMaterial().toUpperCase()).equals(Material.COBBLESTONE)) randomAmount = 1;
                    player.getInventory().addItem(new ItemHelper(Material.getMaterial(drop.getMaterial()), randomAmount, drop.getMaterialId()).toItemStack());
                    player.giveExp(15);
                    if (user.hasDisableMessage(drop.getId())) continue;
                    player.sendMessage(ChatUtilities.colored("&b&lDROP &3&l:: &fGratualcje trafiłeś na&8(&b" + drop.getMaterial() + "&f, &b" + randomAmount + "szt&f, &b" + drop.getChance() + "%&8)"));
                }
            });
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (block.getType() == Material.BEDROCK) {
            this.bukkitCache.findBukkitUserByNickName(player.getName()).ifPresent(bukkitUser -> {


                new BukkitRunnable() {

                    final long timeDelay = System.currentTimeMillis() + TimeType.SECOND.getTime(3);

                    float yaw = 0;
                    double height = (float) -0.25;
                    boolean add = true;

                    @Override
                    public void run() {

                        yaw = (float) (yaw + 3.16);
                        if (yaw >= 360) yaw = 0;
                        height = height + (add ? 0.025 : -0.025);
                        if (height >= 0.25 || height <= -0.25) add = !add;

                        int randomNumber = RandomUtilities.getRandInt(0, configurationData.getCaseDropJson().getDrops().length - 1);
                        Drop drop = configurationData.getCaseDropJson().getDrops()[randomNumber];


                        bukkitUser.getArmorStandHelper().apply(armorStandHelper -> {
                            if (timeDelay <= System.currentTimeMillis()) {
                                this.cancel();
                                sendFireworkToLocation(block.getLocation());
                                armorStandHelper.send(SendType.REMOVE, new LocationSerializer(block.getWorld().getName(), block.getX(), block.getY(), block.getZ()),
                                        "");
                                armorStandHelper.setEntity(new ArrayList<>());

                                player.getInventory().addItem(new ItemHelper(Material.valueOf(drop.getMaterial()), (int) drop.getMaterialId()).toItemStack());
                                return;
                            }
                            armorStandHelper.setLocation(block.getLocation());
                            armorStandHelper.setDisplayName(Arrays.asList(
                                    "&7&lWięcej informacji pod: &d/drop",
                                    "&7&lPozostało:&d" + DataUtilities.getTimeToString(timeDelay),
                                    "&5&l♦ &f&lTrwa losowanie... &5&l♦",
                                    "&7Przedmiot: &b" + drop.getMaterial(),
                                    "&5&l♦ &d&lMAGICZNA SKRZYNIA &5&l♦"
                            ));
                            Location location = armorStandHelper.getLocation();
                            location.setYaw(yaw);
                            location.setPitch(yaw - player.getLocation().getPitch());
                            location.setY(block.getY() + height);

                         armorStandHelper.send(SendType.UPDATE, new LocationSerializer(location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ()),
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTg4MTQ5ZTU2Y2RlMDJlZWU2MjA5ODI4ODRiODkzNjM0NzU2YmU5MTQxZjJlMjc3N2I1MWNiMGE2NmMzMWRjZSJ9fX0=");

                        });

                    }
                }.runTaskTimer(BukkitMain.getInstance(), 0L, 0L);
            });
        }
    }
    public void sendFireworkToLocation(Location location){
        Firework firework = location.getWorld().spawn(location, Firework.class);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();
        fireworkMeta.addEffect(FireworkEffect.builder().withColor(Color.BLUE).flicker(false).withFade(Color.WHITE).build());
        firework.setFireworkMeta(fireworkMeta);

        Firework firework1 = location.getWorld().spawn(location, Firework.class);
        FireworkMeta fireworkMeta1 = firework1.getFireworkMeta();
        fireworkMeta1.addEffect(FireworkEffect.builder().withColor(Color.GREEN).flicker(false).withFade(Color.AQUA).build());
        firework1.setFireworkMeta(fireworkMeta1);

        Firework firework2 = location.getWorld().spawn(location, Firework.class);
        FireworkMeta fireworkMeta2 = firework2.getFireworkMeta();
        fireworkMeta2.addEffect(FireworkEffect.builder().withColor(Color.YELLOW).flicker(false).withFade(Color.RED).build());
        firework2.setFireworkMeta(fireworkMeta2);
    }
}

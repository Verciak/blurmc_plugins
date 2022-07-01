package pl.pieszku.sectors.handler;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.pieszku.api.objects.user.User;
import org.pieszku.api.service.UserService;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.cache.BukkitCache;
import pl.pieszku.sectors.cache.BukkitUser;
import pl.pieszku.sectors.transfer.TransferPlayerSectorUtilities;
import pl.pieszku.sectors.utilities.ChatUtilities;
import pl.pieszku.sectors.utilities.ItemUtilities;
import pl.pieszku.sectors.utilities.SkinUtilities;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PlayerJoinHandler implements Listener {

    private final UserService userService = BukkitMain.getInstance().getUserService();
    private final TransferPlayerSectorUtilities transferPlayerSectorUtilities = new TransferPlayerSectorUtilities();
    private final BukkitCache bukkitCache = BukkitMain.getInstance().getBukkitCache();
    private final List<ItemStack> itemStackList = Arrays.asList(
            new ItemStack(Material.STONE_PICKAXE),
            new ItemStack(Material.STONE_AXE),
            new ItemStack(Material.ENDER_CHEST),
            new ItemStack(Material.COOKED_BEEF, 64));

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        Player player = event.getPlayer();

        Optional<BukkitUser> bukkitUserOptional = this.bukkitCache.findBukkitUserByNickName(player.getName());

        if(!bukkitUserOptional.isPresent()){
            this.bukkitCache.create(player.getName()).init(player);
        }

        Bukkit.getScheduler().runTaskLater(BukkitMain.getInstance(), () -> {


            if(player.getHealth() <= 0){
                player.spigot().respawn();
                player.teleport(new Location(Bukkit.getWorld("world"), 0, 80, 0));
                ItemUtilities.addItem(player, itemStackList);
                return;
            }

            Optional<User> optionalUser = this.userService.findUserByNickName(player.getName());
            if (!optionalUser.isPresent()) {
                User user = this.userService.getOrCreate(player.getName());
                user.setNowSector(BukkitMain.getInstance().getSectorName());
                BukkitMain.getInstance().getTeamService().initializeNameTag(player, user);
                return;
            }
            optionalUser.ifPresent(user -> {

                BukkitMain.getInstance().getTeamService().initializeNameTag(player, user);

                player.sendTitle(ChatUtilities.colored("&3&lSEKTOR"), ChatUtilities.colored("&fZostałeś &bpołączony &fz sektorem &b" + BukkitMain.getInstance().getCurrentSector().get().getName().toUpperCase()));
                this.transferPlayerSectorUtilities.loadTransferPlayer(player, user);


                user.setArmorType(user.getLastArmorType());

                if(user.isIncognito()) {
                    new SkinUtilities().changeSkin(player);
                }
            });
        }, 1L);

    }
}

package pl.pieszku.sectors.handler.gameplay;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.pieszku.api.API;
import org.pieszku.api.objects.backup.Backup;
import org.pieszku.api.objects.guild.impl.Guild;
import org.pieszku.api.redis.packet.backup.BackupSynchronizeInformationPacket;
import org.pieszku.api.redis.packet.client.SendMessagePacket;
import org.pieszku.api.redis.packet.type.MessageType;
import org.pieszku.api.redis.packet.type.ReceiverType;
import org.pieszku.api.redis.packet.type.UpdateType;
import org.pieszku.api.sector.SectorService;
import org.pieszku.api.service.BackupService;
import org.pieszku.api.service.GuildService;
import org.pieszku.api.service.UserService;
import org.pieszku.api.type.UserSettingMessageType;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.serializer.ItemSerializer;
import pl.pieszku.sectors.utilities.ChatUtilities;
import pl.pieszku.sectors.utilities.ItemUtilities;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class PlayerDeathGameplayHandler implements Listener {


    private final UserService userService = BukkitMain.getInstance().getUserService();
    private final GuildService guildService = BukkitMain.getInstance().getGuildService();
    private final SectorService sectorService = BukkitMain.getInstance().getSectorService();
    private final BackupService backupService = API.getInstance().getBackupService();

    private final List<ItemStack> itemStackList = Arrays.asList(
            new ItemStack(Material.STONE_PICKAXE),
            new ItemStack(Material.STONE_AXE),
            new ItemStack(Material.ENDER_CHEST),
            new ItemStack(Material.COOKED_BEEF, 64));

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.setDeathMessage(null);
        Player playerEntity = event.getEntity();

        this.userService.findUserByNickName(playerEntity.getName()).ifPresent(userDeath -> {

            userDeath.getUserAntiLogout().setAntiLogoutTime(0);

            Player playerKiller = Bukkit.getPlayerExact(userDeath.getUserAntiLogout().getAttackerNickName());

            Backup backup = new Backup(this.backupService.getBackups().size() + 1, ((CraftPlayer) playerEntity).getHandle().ping,
                    playerEntity.getName(), (playerKiller != null ? playerKiller.getName() : "samobójstwo"),
                    new Date(System.currentTimeMillis()).toGMTString(), userDeath.getKills(), userDeath.getDeaths(), userDeath.getPoints(),
                    ItemSerializer.decodeItems(playerEntity.getInventory().getContents()),
                    ItemSerializer.decodeItems(playerEntity.getInventory().getArmorContents()));

            new BackupSynchronizeInformationPacket(backup.getId(), new Gson().toJson(backup), UpdateType.CREATE).sendToChannel("MASTER");

            if (playerKiller != null) {

                ItemUtilities.addItem(playerKiller, event.getDrops());
                event.getDrops().clear();

                playerEntity.spigot().respawn();
                ItemUtilities.addItem(playerEntity, itemStackList);

                this.userService.findUserByNickName(playerKiller.getName()).ifPresent(userKiller -> {
                    Bukkit.getScheduler().runTaskLaterAsynchronously(BukkitMain.getInstance(), () -> {



                        int userPointsAdd = (int) (100 + (userDeath.getPoints() - userKiller.getPoints()) * 0.2),
                                userPointsRemove = ((userPointsAdd / 6) * 2);

                        Optional<Guild> guildEntityOptional = this.guildService.findGuildByMember(playerEntity.getName());
                        Optional<Guild> guildKillerOptional = this.guildService.findGuildByMember(playerKiller.getName());

                        Guild guildEntity = null;
                        Guild guildKiller = null;

                        if (guildEntityOptional.isPresent()) {
                            guildEntity = guildEntityOptional.get();
                        }
                        if (guildKillerOptional.isPresent()) {
                            guildKiller = guildKillerOptional.get();
                        }

                        Guild finalGuildKiller = guildKiller;
                        Guild finalGuildEntity = guildEntity;

                        playerKiller.sendTitle(ChatUtilities.colored("&a&lZabójstwo"), ChatUtilities.colored(
                                "&fZabiłeś gracza&8(" + (finalGuildEntity != null ? "&7[&c" + finalGuildEntity.getName().toUpperCase() + "&7]" : "") + "&f" + playerEntity.getName() + "&8, &a+" + userPointsAdd + "&8)" ));


                        this.sectorService.getSectorList().stream().flatMap(sector -> sector.getPlayers().stream()).forEach(playerOnline -> this.userService.findUserByNickName(playerOnline).ifPresent(user -> {
                            if (user.findUserSettingMessageByType(UserSettingMessageType.DEATH).isStatus()) {
                                SendMessagePacket sendMessagePacket = new SendMessagePacket(
                                        (finalGuildKiller != null ? "&8[&c" + finalGuildKiller.getName().toUpperCase() + "&8]" : "")
                                                + "&7" + playerKiller.getName() + "&8(&f+" + userPointsAdd + "&8) &czabił gracza: " + (finalGuildEntity != null ? "&8[&c" + finalGuildEntity.getName().toUpperCase() + "&8]" : "")
                                                + "&7" + playerEntity.getName() + "&8(&f-" + userPointsRemove + "&8)",
                                        ReceiverType.PLAYER, MessageType.CHAT);
                                sendMessagePacket.setNickNameReceiver(user.getNickName());
                                sendMessagePacket.sendToAllSectors(this.sectorService);
                            }
                        }));


                        userKiller.addKills(1);
                        userDeath.addDeaths(1);

                        userKiller.addPoints(userPointsAdd);
                        userDeath.removePoints(userPointsRemove);

                        if (guildKiller != null) {
                            guildKiller.addKills(1);
                            guildKiller.addPoints(userPointsAdd);
                        }
                        if (guildEntity != null) {
                            guildEntity.addDeaths(1);
                            guildEntity.removePoints(userPointsRemove);
                        }


                    }, 10L);
                });
            }
        });
    }
}

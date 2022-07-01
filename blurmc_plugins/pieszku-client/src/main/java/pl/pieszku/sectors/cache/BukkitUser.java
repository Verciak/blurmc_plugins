package pl.pieszku.sectors.cache;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import pl.pieszku.sectors.helper.ArmorStandHelper;
import pl.pieszku.sectors.helper.PacketHelper;
import pl.pieszku.sectors.helper.PlayerHelper;
import pl.pieszku.sectors.helper.TablistHelper;
import pl.pieszku.sectors.impl.UserActionBarType;

import java.util.*;

public class BukkitUser {

    private final String nickName;
    private final List<BossBar> bossBar;
    private  PacketHelper packetHelper;
    private  PlayerHelper playerHelper;
    private TablistHelper tablistHelper;
    private ArmorStandHelper armorStandHelper;
    transient Map<UserActionBarType, String> actionBarMap = new LinkedHashMap<>();

    public BukkitUser(String nickName){
        this.nickName = nickName;
        this.bossBar = Arrays.asList(
                Bukkit.createBossBar("0", BarColor.BLUE, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC),
                Bukkit.createBossBar("1", BarColor.RED, BarStyle.SEGMENTED_20, BarFlag.DARKEN_SKY));
    }
    public void init(Player player){
        this.packetHelper = new PacketHelper(player);
        this.playerHelper = new PlayerHelper(player);
        this.tablistHelper = new TablistHelper(player, packetHelper);
        this.armorStandHelper = new ArmorStandHelper(player, packetHelper);
        this.actionBarMap = new LinkedHashMap<>();
    }


    public void updateActionBar(UserActionBarType type, String text) {
        synchronized (this.actionBarMap) {
            this.actionBarMap.put(type, text);
        }
    }

    public void removeActionBar(UserActionBarType type) {
        synchronized (this.actionBarMap) {
            this.actionBarMap.remove(type);
        }
    }

    public void clearActionBars() {
        synchronized (this.actionBarMap) {
            this.actionBarMap.clear();
        }
    }

    public ArmorStandHelper getArmorStandHelper() {
        return armorStandHelper;
    }

    public PacketHelper getPacketHelper() {
        return packetHelper;
    }

    public PlayerHelper getPlayerHelper() {
        return playerHelper;
    }

    public TablistHelper getTablistHelper() {
        return tablistHelper;
    }

    public String getNickName() {
        return nickName;
    }

    public List<BossBar> getBossBar() {
        return bossBar;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.nickName);
    }

    public String collectActiveActionBars() {
        return this.actionBarMap.values().toString().replace("[", "").replace("]", "");
    }
}

package org.pieszku.api.objects.user;

import com.google.gson.Gson;
import org.pieszku.api.data.drop.Drop;
import org.pieszku.api.impl.Identifiable;
import org.pieszku.api.redis.packet.type.UpdateType;
import org.pieszku.api.redis.packet.user.sync.UserInformationSynchronizePacket;
import org.pieszku.api.serializer.LocationSerializer;
import org.pieszku.api.type.ArmorType;
import org.pieszku.api.type.GroupType;
import org.pieszku.api.type.TimeType;
import org.pieszku.api.type.UserSettingMessageType;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class User implements Serializable, Identifiable<String> {

    private final String nickName;
    private int points;
    private int kills;
    private int deaths;
    private String nowSector;
    private String serializedEnderChest;
    private String serializedInventory;
    private String serializedArmorInventory;
    private LocationSerializer location;
    private int level;
    private float exp;
    private float foodLevel;
    private double health;
    private int heldItemSlot;
    private String gameMode;
    private boolean flying;
    private float speedLevel;
    private boolean interactSector;
    private long changeSector;
    private String addressHostName;
    private GroupType groupType;
    private final ConcurrentHashMap<String, Long> kitMap;
    private final ConcurrentHashMap<String, Integer> depositMap;
    private final Set<Integer> disableDrops;
    private final Set<Integer> disableDropMessages;
    private Set<String> playersTeleportRequest;
    private String latestPrivateMessageNickName;
    private final long protectionTime;
    private long cooldownHelpop = 0;
    private final UserAntiLogout userAntiLogout;
    private final List<UserHome> homeList;
    private final List<UserEnderchest> userEnderchestList;
    private final List<UserSettingMessage> userSettingMessageList;
    private boolean incognito;
    private double expMiner = 0.10;
    private int levelMiner = 0;
    private ArmorType armorType;
    private ArmorType lastArmorType;
    private List<UserItemShop> userItemShopList;
    private boolean vanish;

    public User(String nickName){
        this.nickName = nickName;
        this.points = 1000;
        this.kills = 0;
        this.deaths = 0;
        this.groupType = GroupType.ROOT;
        this.kitMap = new ConcurrentHashMap<>();
        this.playersTeleportRequest = new HashSet<>();
        this.latestPrivateMessageNickName = null;
        this.depositMap = new ConcurrentHashMap<>();
        this.disableDropMessages = new HashSet<>();
        this.disableDrops = new HashSet<>();
        this.protectionTime = System.currentTimeMillis() + TimeType.MINUTE.getTime(2);
        this.userAntiLogout = new UserAntiLogout(null, 0);
        this.homeList = Arrays.asList(
                new UserHome("#1" ,10, GroupType.PLAYER),
                new UserHome("#2" ,11, GroupType.VIP),
                new UserHome("#3" ,12, GroupType.SVIP),
                new UserHome("#4" ,13, GroupType.YOUTUBER),
                new UserHome("#5" ,14, GroupType.SPONSOR),
                new UserHome("#6" ,15, GroupType.PREMIUM));

        this.userEnderchestList = Arrays.asList(
                new UserEnderchest("#1" ,10, GroupType.PLAYER),
                new UserEnderchest("#2" ,11, GroupType.VIP),
                new UserEnderchest("#3" ,12, GroupType.SVIP),
                new UserEnderchest("#4" ,13, GroupType.YOUTUBER),
                new UserEnderchest("#5" ,14, GroupType.SPONSOR),
                new UserEnderchest("#6" ,15, GroupType.PREMIUM));

        this.userSettingMessageList = Arrays.asList(
                new UserSettingMessage(UserSettingMessageType.BROADCAST, "wiadmości od admininstracji", 10),
                new UserSettingMessage(UserSettingMessageType.DEATH, "wiadmośći o śmierci/zabójstwach", 11),
                new UserSettingMessage(UserSettingMessageType.CHAT, "czat globalny", 12),
                new UserSettingMessage(UserSettingMessageType.CASE, "wiadmości o magicznych skrzynkach", 13),
                new UserSettingMessage(UserSettingMessageType.ITEMSHOP, "wiadmości o zakupionych usługach", 14),
                new UserSettingMessage(UserSettingMessageType.PRIVATE, "prywatne wiadomości /msg", 15),
                new UserSettingMessage(UserSettingMessageType.TELEPORT, "wiadomości o teleportacji /tpaccept", 16)
        );

        this.userItemShopList = new ArrayList<>();
        this.armorType = ArmorType.CLEAR;
        this.lastArmorType = ArmorType.CLEAR;
        this.incognito = false;
        this.vanish = false;
        this.setInteractSector(true);
        new UserInformationSynchronizePacket(nickName, new Gson().toJson(this), UpdateType.CREATE).sendToChannel("MASTER");

    }

    public void setVanish(boolean vanish) {
        this.vanish = vanish;
    }

    public boolean isVanish() {
        return vanish;
    }

    public ArmorType getLastArmorType() {
        return lastArmorType;
    }

    public ArmorType getArmorType() {
        return armorType;
    }

    public void setLastArmorType(ArmorType lastArmorType) {
        this.lastArmorType = lastArmorType;
    }

    public void setArmorType(ArmorType armorType) {
        this.armorType = armorType;
    }

    public List<UserEnderchest> getUserEnderchestList() {
        return userEnderchestList;
    }
    public UserEnderchest findUserEnderchestBySlot(int slot){
        return this.userEnderchestList
                .stream()
                .filter(userEnderchest -> userEnderchest.getInventorySlot() == slot)
                .findFirst()
                .orElse(null);
    }

    public List<UserSettingMessage> getUserSettingMessageList() {
        return userSettingMessageList;
    }
    public UserSettingMessage findUserSettingMessageByType(UserSettingMessageType settingMessageType){
        return this.userSettingMessageList
                .stream()
                .filter(userSettingMessage -> userSettingMessage.getSettingMessageType().equals(settingMessageType))
                .findFirst()
                .orElse(null);
    }

    public double getExpMiner() {
        return expMiner;
    }

    public int getLevelMiner() {
        return levelMiner;
    }

    public void setLevelMiner(int levelMiner) {
        this.levelMiner = levelMiner;
        this.synchronizeUser(UpdateType.UPDATE);
    }

    public void setExpMiner(double expMiner) {
        this.expMiner = expMiner;
        this.synchronizeUser(UpdateType.UPDATE);
    }

    public void setIncognito(boolean incognito) {
        this.incognito = incognito;
    }

    public boolean isIncognito() {
        return incognito;
    }

    public long getCooldownHelpop() {
        return cooldownHelpop;
    }

    public void setCooldownHelpop(long cooldownHelpop) {
        this.cooldownHelpop = cooldownHelpop;
    }
    public boolean hasCooldownHelpop(){
        return this.cooldownHelpop <= System.currentTimeMillis();
    }

    public List<UserHome> getHomeList() {
        return homeList;
    }
    public UserHome findHomeByInventorySlot(int inventorySlot){
        return this.homeList
                .stream()
                .filter(home -> home.getInventorySlot() == inventorySlot)
                .findFirst()
                .orElse(null);
    }

    public long getProtectionTime() {
        return protectionTime;
    }
    public boolean hasProtection(){
        return this.protectionTime <= System.currentTimeMillis();
    }

    public UserAntiLogout getUserAntiLogout() {
        return userAntiLogout;
    }

    public int getPoints() {
        return points;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setGroupType(GroupType groupType) {
        this.groupType = groupType;
        this.synchronizeUser(UpdateType.UPDATE);
    }
    public void addPoints(int points){
        this.points += points;
        this.synchronizeUser(UpdateType.UPDATE);
    }
    public void removePoints(int points){
        this.points -= points;
        this.synchronizeUser(UpdateType.UPDATE);
    }
    public void addDeaths(int deaths){
        this.deaths += deaths;
        this.synchronizeUser(UpdateType.UPDATE);
    }
    public void addKills(int kills){
        this.kills += kills;
        this.synchronizeUser(UpdateType.UPDATE);
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public String getNickName() {
        return nickName;
    }

    public String getNowSector() {
        return nowSector;
    }

    public void setNowSector(String nowSector) {
        this.nowSector = nowSector;
    }

    public String getSerializedEnderChest() {
        return serializedEnderChest;
    }

    public void setSerializedEnderChest(String serializedEnderChest) {
        this.serializedEnderChest = serializedEnderChest;
    }

    public String getSerializedInventory() {
        return serializedInventory;
    }

    public void setSerializedInventory(String serializedInventory) {
        this.serializedInventory = serializedInventory;
    }

    public String getSerializedArmorInventory() {
        return serializedArmorInventory;
    }

    public void setSerializedArmorInventory(String serializedArmorInventory) {
        this.serializedArmorInventory = serializedArmorInventory;
    }

    public LocationSerializer getLocation() {
        return location;
    }

    public void setLocation(LocationSerializer location) {
        this.location = location;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public float getExp() {
        return exp;
    }

    public void setExp(float exp) {
        this.exp = exp;
    }

    public float getFoodLevel() {
        return foodLevel;
    }

    public void setFoodLevel(float foodLevel) {
        this.foodLevel = foodLevel;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public int getHeldItemSlot() {
        return heldItemSlot;
    }

    public void setHeldItemSlot(int heldItemSlot) {
        this.heldItemSlot = heldItemSlot;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public boolean isFlying() {
        return flying;
    }

    public void setFlying(boolean flying) {
        this.flying = flying;
    }

    public float getSpeedLevel() {
        return speedLevel;
    }

    public void setSpeedLevel(float speedLevel) {
        this.speedLevel = speedLevel;
    }

    public boolean isInteractSector() {
        return interactSector;
    }

    public void setInteractSector(boolean interactSector) {
        this.interactSector = interactSector;
    }

    public long getChangeSector() {
        return changeSector;
    }

    public void setChangeSector(long changeSector) {
        this.changeSector = changeSector;
    }

    public String getAddressHostName() {
        return addressHostName;
    }

    public void setAddressHostName(String addressHostName) {
        this.addressHostName = addressHostName;
        this.synchronizeUser(UpdateType.UPDATE);
    }
    public void addKit(String name, long delay){
        this.kitMap.put(name, delay);
        this.synchronizeUser(UpdateType.UPDATE);
    }
    public boolean hasKitPickup(String name){
        if(!this.kitMap.containsKey(name)){
            this.kitMap.put(name, 0L);
            return true;
        }
        return this.kitMap.get(name) <= System.currentTimeMillis();
    }

    public int getDepositCount(String depositName){
        if (!this.depositMap.containsKey(depositName)) {
            this.depositMap.put(depositName, 0);
        }
        return this.depositMap.get(depositName);
    }
    public void addDepositItem(String depositName, int value){
        if (!this.depositMap.containsKey(depositName)) {
            this.depositMap.put(depositName, 0);
        }
        this.depositMap.put(depositName, this.depositMap.get(depositName) + value);
        this.synchronizeUser(UpdateType.UPDATE);
    }
    public void removeDepositItem(String depositName, int value){
        if (!this.depositMap.containsKey(depositName)) {
            this.depositMap.put(depositName, 0);
        }
        this.depositMap.put(depositName, this.depositMap.get(depositName) - value);
        this.synchronizeUser(UpdateType.UPDATE);
    }

    public long getTimeDelayKit(String kitName) {
        return this.kitMap.get(kitName);
    }
    public void setPlayersTeleportRequest(Set<String> playersTeleportRequest) {
        this.playersTeleportRequest = playersTeleportRequest;
    }

    public Set<Integer> getDisableDropMessages() {
        return disableDropMessages;
    }

    public Set<Integer> getDisableDrops() {
        return disableDrops;
    }
    public void enableDrop(int id){
        this.disableDrops.remove(id);
        this.synchronizeUser(UpdateType.UPDATE);
    }
    public void disableDrop(int id){
        this.disableDrops.add(id);
        this.synchronizeUser(UpdateType.UPDATE);
    }
    public boolean hasDisable(int id){
        return this.disableDrops.contains(id);
    }

    public void enableDropMessage(int id){
        this.disableDropMessages.remove(id);
        this.synchronizeUser(UpdateType.UPDATE);
    }
    public void disableDropMessage(int id){
        this.disableDropMessages.add(id);
        this.synchronizeUser(UpdateType.UPDATE);
    }
    public boolean hasDisableMessage(int id){
        return this.disableDropMessages.contains(id);
    }
    public void enableALLDrop() {
        this.disableDrops.clear();
        this.synchronizeUser(UpdateType.UPDATE);
    }
    public void disableALLDrop(Drop[] drops){
        Arrays.stream(drops).forEach(drop -> {this.disableDrops.add(drop.getId());});
        this.synchronizeUser(UpdateType.UPDATE);
    }

    public ConcurrentHashMap<String, Long> getKitMap() {
        return kitMap;
    }

    public Set<String> getPlayersTeleportRequest() {
        return playersTeleportRequest;
    }

    public String getLatestPrivateMessageNickName() {
        return latestPrivateMessageNickName;
    }

    public void setLatestPrivateMessageNickName(String latestPrivateMessageNickName) {
        this.latestPrivateMessageNickName = latestPrivateMessageNickName;
    }

    public ConcurrentHashMap<String, Integer> getDepositMap() {
        return depositMap;
    }
    public void synchronizeUser(UpdateType updateType){
        new UserInformationSynchronizePacket(this.getNickName(), new Gson().toJson(this), updateType).sendToChannel("MASTER");
    }

    @Override
    public String getId() {
        return this.nickName;
    }

    @Override
    public void setId(String s) {
        //final nickName
    }

    public UserSettingMessage findUserSettingMessageBySlot(int slot) {
        return this.userSettingMessageList
                .stream()
                .filter(userSettingMessage -> userSettingMessage.getSlot() == slot)
                .findFirst()
                .orElse(null);
    }
}

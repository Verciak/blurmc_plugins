package org.pieszku.api.objects.guild.impl;

import com.google.gson.Gson;
import org.pieszku.api.impl.Identifiable;
import org.pieszku.api.objects.guild.GuildInterface;
import org.pieszku.api.objects.guild.GuildPermission;
import org.pieszku.api.objects.guild.GuildPermissionType;
import org.pieszku.api.redis.packet.client.SendMessagePacket;
import org.pieszku.api.redis.packet.guild.synchronize.GuildInformationSynchronizePacket;
import org.pieszku.api.redis.packet.type.MessageType;
import org.pieszku.api.redis.packet.type.ReceiverType;
import org.pieszku.api.redis.packet.type.UpdateType;
import org.pieszku.api.sector.SectorService;
import org.pieszku.api.serializer.LocationSerializer;
import org.pieszku.api.type.TimeType;

import java.io.Serializable;
import java.util.*;

public class Guild implements Serializable, Identifiable<String>, GuildInterface {

    private final String name;
    private final String fullName;
    private String owner;
    private String master;
    private String leader;
    private int points;
    private int kills;
    private int deaths;
    private final Set<String> allys;
    private final Set<String> allysInvite;
    private final Set<String> members;
    private final Set<String> membersInvite;
    private Date latestSynchronizeData;
    private final Date createDate;
    private final LocationSerializer locationSerializer;
    private LocationSerializer locationSerializerHome;
    private boolean friendlyFire;
    private boolean allyFriendlyFire;
    private final List<GuildPermission> guildPermissionList;
    private long protection;
    private int life;
    private int hp;


    public Guild(String name, String fullName, String owner, LocationSerializer locationSerializer) {
        this.name = name;
        this.fullName = fullName;
        this.owner = owner;
        this.master = "null";
        this.leader = "null";
        this.points = 1000;
        this.kills = 0;
        this.deaths = 0;
        this.allys = new HashSet<>();
        this.allysInvite = new HashSet<>();
        this.members = new HashSet<>();
        this.membersInvite = new HashSet<>();
        this.latestSynchronizeData = new Date(System.currentTimeMillis());
        this.createDate = new Date(System.currentTimeMillis());
        this.locationSerializer = locationSerializer;
        this.friendlyFire = false;
        this.allyFriendlyFire = false;
        this.life = 3;
        this.hp = 100;
        this.protection = System.currentTimeMillis() + TimeType.HOUR.getTime(24);
        this.locationSerializerHome = new LocationSerializer(locationSerializer.getWorld(), locationSerializer.getX(), locationSerializer.getY(), locationSerializer.getZ());
        this.guildPermissionList = Arrays.asList(
                new GuildPermission(GuildPermissionType.MEMBER, "członek"),
                new GuildPermission(GuildPermissionType.MASTER, "mistrz"),
                new GuildPermission(GuildPermissionType.LEADER, "zastępca"),
                new GuildPermission(GuildPermissionType.OWNER, "założyciel"));
        this.synchronize(UpdateType.CREATE);
        this.addMember(owner);
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getHp() {
        return hp;
    }

    public int getLife() {
        return life;
    }

    public long getProtection() {
        return protection;
    }

    public void setProtection(long protection) {
        this.protection = protection;
    }
    public boolean hasProtection(){
        return this.protection <= System.currentTimeMillis();
    }

    public List<GuildPermission> getGuildPermissionList() {
        return guildPermissionList;
    }



    public Date getLatestSynchronizeData() {
        return latestSynchronizeData;
    }

    public LocationSerializer getLocationSerializer() {
        return locationSerializer;
    }

    public LocationSerializer getLocationSerializerHome() {
        return locationSerializerHome;
    }

    public void setOwner(String owner) {
        this.owner = owner;
        this.synchronize(UpdateType.UPDATE);
    }

    public void setLeader(String leader) {
        this.leader = leader;
        this.synchronize(UpdateType.UPDATE);
    }

    public void setMaster(String master) {
        this.master = master;
        this.synchronize(UpdateType.UPDATE);
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public String getOwner() {
        return owner;
    }

    public String getMaster() {
        return master;
    }

    public String getLeader() {
        return leader;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
        this.synchronize(UpdateType.UPDATE);
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
        this.synchronize(UpdateType.UPDATE);
    }

    public Date getCreateDate() {
        return createDate;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public Set<String> getAllys() {
        return allys;
    }

    public Set<String> getAllysInvite() {
        return allysInvite;
    }

    public Set<String> getMembers() {
        return members;
    }

    public Set<String> getMembersInvite() {
        return membersInvite;
    }

    @Override
    public String getId() {
        return this.name;
    }

    @Override
    public void setId(String s) {
    }

    @Override
    public void addMember(String nickName) {
        this.members.add(nickName);
        this.synchronize(UpdateType.UPDATE);
    }

    @Override
    public void removeMember(String nickName) {
        this.members.remove(nickName);
        this.synchronize(UpdateType.UPDATE);
    }

    @Override
    public void changeOwner(String nickName) {
        this.owner = nickName;
        this.synchronize(UpdateType.UPDATE);
    }

    @Override
    public void changeMaster(String nickName) {
        this.master = nickName;
        this.synchronize(UpdateType.UPDATE);
    }

    @Override
    public void changeLeader(String nickName) {
        this.leader = nickName;
        this.synchronize(UpdateType.UPDATE);
    }

    @Override
    public void incrementKills() {
        this.kills += 1;
        this.synchronize(UpdateType.UPDATE);
    }

    @Override
    public void incrementDeaths() {
        this.deaths += 1;
        this.synchronize(UpdateType.UPDATE);
    }

    @Override
    public void removeKills(int kills) {
        this.kills -= kills;
        this.synchronize(UpdateType.UPDATE);
    }

    @Override
    public void removePoints(int points) {
        this.points -= points;
        this.synchronize(UpdateType.UPDATE);
    }

    @Override
    public void addKills(int kills) {
        this.kills += kills;
        this.synchronize(UpdateType.UPDATE);
    }

    public void addDeaths(int deaths) {
        this.deaths += deaths;
        this.synchronize(UpdateType.UPDATE);
    }

    @Override
    public void addPoints(int points) {
        this.points += points;
        this.synchronize(UpdateType.UPDATE);
    }

    public void setLocationHome(LocationSerializer locationSerializer) {
        this.locationSerializerHome = locationSerializer;
        this.synchronize(UpdateType.UPDATE);
    }

    @Override
    public void synchronize(UpdateType updateType) {
        new GuildInformationSynchronizePacket(this.name, new Gson().toJson(this), updateType).sendToChannel("MASTER");
    }

    public boolean isOnCuboid(String world, int x, int z) {
        if (!world.equals("world")) {
            return false;
        }
        int distancex = Math.abs(x - this.getLocationSerializer().getX());
        int distancez = Math.abs(z - this.getLocationSerializer().getZ());
        return distancex - 1 <= this.getLocationSerializer().getSize() && distancez - 1 <= this.getLocationSerializer().getSize();
    }

    @Override
    public void updateLatestInformation() {
        this.latestSynchronizeData = new Date(System.currentTimeMillis());
    }

    public boolean hasMaster(String nickName) {
        return this.owner.equalsIgnoreCase(nickName) || this.leader.equalsIgnoreCase(nickName) || this.master.equalsIgnoreCase(nickName);
    }

    public boolean isMaster(String name) {
        return this.master.equalsIgnoreCase(name);
    }

    public boolean isLeader(String name) {
        return this.leader.equalsIgnoreCase(name);
    }

    public boolean hasOwner(String nickName) {
        return this.owner.equalsIgnoreCase(nickName);
    }

    public boolean isFriendlyFire() {
        return friendlyFire;
    }

    public void setFriendlyFire(boolean friendlyFire) {
        this.friendlyFire = friendlyFire;
    }

    public boolean isAllyFriendlyFire() {
        return allyFriendlyFire;
    }

    public void setAllyFriendlyFire(boolean allyFriendlyFire) {
        this.allyFriendlyFire = allyFriendlyFire;
    }
    public boolean isInCentrum(LocationSerializer loc, int top, int down, int wall) {
        LocationSerializer c = this.getLocationSerializer();
        return c.getY() - down <= loc.getY() && c.getY() + top >= loc.getY() && loc.getX() <= c.getX() + wall && loc.getX() >= c.getX() - wall && loc.getZ() <= c.getZ() + wall && loc.getZ() >= c.getZ() - wall;
    }

    public void sendMessage(String message, SectorService sectorService) {
        this.members.stream().filter(sectorService::isOnlinePlayer).forEach(member -> {
            SendMessagePacket sendMessagePacket = new SendMessagePacket(message, ReceiverType.PLAYER, MessageType.CHAT);
            sendMessagePacket.setNickNameReceiver(member);
            sendMessagePacket.sendToAllSectors(sectorService);
        });
    }

    public boolean hasMember(String nickName) {
        return this.members.contains(nickName);
    }

    public GuildPermission getPermissionGuildByType(GuildPermissionType permissionGuildType) {
        return this.guildPermissionList
                .stream()
                .filter(guildPermission -> guildPermission.getPermissionType().equals(permissionGuildType))
                .findFirst()
                .orElse(null);
    }

    public GuildPermission findGuildPermissionByNickName(String nickName) {
        return this.guildPermissionList.stream()
                .filter(guildPermission -> guildPermission.hasMember(nickName))
                .findFirst()
                .orElse(null);
    }

}

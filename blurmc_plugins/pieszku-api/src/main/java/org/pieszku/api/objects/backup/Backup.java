package org.pieszku.api.objects.backup;

import org.pieszku.api.impl.Identifiable;

import java.io.Serializable;

public class Backup implements Serializable, Identifiable<Integer> {


    private final int id;
    private final int ping;
    private final String nickName;
    private final String killerNickName;
    private final String data;
    private final int kills;
    private final int deaths;
    private final int points;
    private final String serializedInventory;
    private final String serializedArmorContent;

    public Backup(int id, int ping, String nickName, String killerNickName, String data, int kills, int deaths, int points, String serializedInventory, String serializedArmorContent){
        this.id = id;
        this.ping = ping;
        this.nickName = nickName;
        this.killerNickName = killerNickName;
        this.data = data;
        this.kills = kills;
        this.deaths = deaths;
        this.points = points;
        this.serializedInventory = serializedInventory;
        this.serializedArmorContent = serializedArmorContent;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer integer) {
    }

    public String getNickName() {
        return nickName;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getKills() {
        return kills;
    }

    public int getPing() {
        return ping;
    }

    public int getPoints() {
        return points;
    }

    public String getData() {
        return data;
    }

    public String getKillerNickName() {
        return killerNickName;
    }

    public String getSerializedArmorContent() {
        return serializedArmorContent;
    }

    public String getSerializedInventory() {
        return serializedInventory;
    }
}

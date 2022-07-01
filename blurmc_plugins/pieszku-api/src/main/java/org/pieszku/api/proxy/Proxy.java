package org.pieszku.api.proxy;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Proxy implements Serializable {


    private final String name;
    private final int delayConnection;
    private final int minimumPlayers;
    private final int maximumPlayers;
    private int connections;
    private long latestInformation;
    private Set<String> players;

    public Proxy(String name, int delayConnection, int minimumPlayers, int maximumPlayers){
        this.name = name;
        this.delayConnection = delayConnection;
        this.minimumPlayers = minimumPlayers;
        this.maximumPlayers = maximumPlayers;
        this.connections = 0;
        this.latestInformation = 0L;
        this.players = new HashSet<>();
    }

    public void incrementConnection(){
        this.connections += 1;
    }

    public void setPlayers(Set<String> players) {
        this.players = players;
    }

    public int getDelayConnection() {
        return delayConnection;
    }

    public int getMaximumPlayers() {
        return maximumPlayers;
    }

    public int getMinimumPlayers() {
        return minimumPlayers;
    }

    public Set<String> getPlayers() {
        return players;
    }

    public String getName() {
        return name;
    }

    public int getConnections() {
        return connections;
    }

    public void setConnections(int connections) {
        this.connections = connections;
    }

    public long getLatestInformation() {
        return latestInformation;
    }

    public void setLatestInformation(long latestInformation) {
        this.latestInformation = latestInformation;
    }

    public boolean isOnline() {
        return this.latestInformation > System.currentTimeMillis();
    }
}

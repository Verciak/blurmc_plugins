package org.pieszku.api.redis.packet.proxy.sync;

import org.pieszku.api.redis.packet.Packet;

import java.util.Set;

public class ProxySynchronizeInformationPacket extends Packet {


    private final String proxyName;
    private final Set<String> players;
    private final int connections;
    private final long latestInformation;

    public ProxySynchronizeInformationPacket(String proxyName, Set<String> players, int connections, long latestInformation){
        this.proxyName = proxyName;
        this.players = players;
        this.connections = connections;
        this.latestInformation = latestInformation;
    }

    public Set<String> getPlayers() {
        return players;
    }

    public String getProxyName() {
        return proxyName;
    }

    public int getConnections() {
        return connections;
    }

    public long getLatestInformation() {
        return latestInformation;
    }

    @Override
    public String toString() {
        return "ProxySynchronizeInformationPacket{" +
                "proxyName='" + proxyName + '\'' +
                ", players=" + players +
                ", connections=" + connections +
                ", latestInformation=" + latestInformation +
                '}';
    }
}

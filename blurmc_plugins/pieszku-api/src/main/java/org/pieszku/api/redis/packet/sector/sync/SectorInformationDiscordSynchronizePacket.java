package org.pieszku.api.redis.packet.sector.sync;

import org.pieszku.api.redis.packet.Packet;

import java.util.List;

public class SectorInformationDiscordSynchronizePacket extends Packet {

    private final String sectorName;
    private final List<String> players;
    private final boolean online;
    private final long latestInformation;

    public SectorInformationDiscordSynchronizePacket(String sectorName, List<String> players, boolean online, long latestInformation){
        this.sectorName = sectorName;
        this.players = players;
        this.online = online;
        this.latestInformation = latestInformation;
    }

    public String getSectorName() {
        return sectorName;
    }

    public List<String> getPlayers() {
        return players;
    }

    public boolean isOnline() {
        return online;
    }

    public long getLatestInformation() {
        return latestInformation;
    }

    @Override
    public String toString() {
        return "SectorInformationDiscordSynchronizePacket{" +
                "sectorName='" + sectorName + '\'' +
                ", players=" + players +
                ", online=" + online +
                ", latestInformation=" + latestInformation +
                '}';
    }
}

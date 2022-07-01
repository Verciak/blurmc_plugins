package org.pieszku.api.redis.packet.sector.sync;

import org.pieszku.api.redis.packet.Packet;

import java.util.List;

public class SectorInformationSynchronizePacket extends Packet {

    private String sectorName;
    private List<String> players;
    private boolean online;
    private long latestInformation;

    public SectorInformationSynchronizePacket(String sectorName, List<String> players, boolean online, long latestInformation){
        this.sectorName = sectorName;
        this.players = players;
        this.online = online;
        this.latestInformation = latestInformation;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }


    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public long getLatestInformation() {
        return latestInformation;
    }

    public void setLatestInformation(long latestInformation) {
        this.latestInformation = latestInformation;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }
    @Override
    public String toString() {
        return "SectorInformationSynchronizePacket{" +
                "sectorName='" + sectorName + '\'' +
                ", players=" + players +
                ", online=" + online +
                ", latestInformation=" + latestInformation +
                '}';
    }
}

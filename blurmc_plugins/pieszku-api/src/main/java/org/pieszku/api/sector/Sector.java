package org.pieszku.api.sector;

import org.pieszku.api.serializer.LocationSerializer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sector implements Serializable {


    private final String name;
    private final SectorType sectorType;
    private final LocationSerializer locationMinimum;
    private final LocationSerializer locationMaximum;
    private final int minSlots;
    private final int maxSlots;
    private boolean online;
    private List<String> players;
    private SectorState sectorState;
    private long latestInformation;

    public Sector(String name,SectorType sectorType, LocationSerializer locationMinimum, LocationSerializer locationMaximum, int minSlots, int maxSlots){
        this.name = name;
        this.sectorType = sectorType;
        this.locationMinimum = locationMinimum;
        this.locationMaximum = locationMaximum;
        this.minSlots = minSlots;
        this.maxSlots = maxSlots;
        this.online = false;
        this.players = new ArrayList<>();
        this.sectorState = SectorState.WAITING;
        this.latestInformation = 0L;
    }
    public boolean isInSector(int x, int z, String world) {
        if (!world.equals(this.locationMinimum.getWorld())) {
            return false;
        }
        return x >= this.locationMinimum.getX() &&
                z >= this.locationMinimum.getZ() &&
                x < this.locationMaximum.getX() &&
                z < this.locationMaximum.getZ();
    }
    public double getDistanceToBorder(int x, int z) {
        double x1 = Math.abs(x - this.locationMinimum.getX());
        double x2 = Math.abs(x - this.locationMaximum.getX());
        double z1 = Math.abs(z - this.locationMinimum.getZ());
        double z2 = Math.abs(z - this.locationMaximum.getZ());
        return  Math.min(Math.min(x1, x2), Math.min(z1, z2));
    }



    public String getName() {
        return name;
    }


    public SectorType getSectorType() {
        return sectorType;
    }

    public LocationSerializer getLocationMinimum() {
        return locationMinimum;
    }

    public LocationSerializer getLocationMaximum() {
        return locationMaximum;
    }

    public int getMinSlots() {
        return minSlots;
    }

    public int getMaxSlots() {
        return maxSlots;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public long getLatestInformation() {
        return latestInformation;
    }
    public boolean hasPlayer(String nickName){
        return this.players.contains(nickName);
    }

    public void setLatestInformation(long latestInformation) {
        this.latestInformation = latestInformation;
    }

    public boolean isSpawn() {
        return this.getSectorType().equals(SectorType.SPAWN);
    }

    public SectorState getSectorState() {
        return sectorState;
    }

    public void setSectorState(SectorState sectorState) {
        this.sectorState = sectorState;
    }

    public Sector getNearestSector(double distance, int x, int z, String world, SectorService sectorService) {
        double border = distance + 5;

        List<Sector> sectors = new ArrayList<>(Arrays.asList(
                sectorService.getSectorByLocation(world, (int) (x + border), z).get(),
                sectorService.getSectorByLocation(world, (int) (x - border), z).get(),
                sectorService.getSectorByLocation(world, x, (int) (z + border)).get(),
                sectorService.getSectorByLocation(world, x, (int) (z - border)).get()));

        for (Sector sector : sectors) {
            if (sector != null && !this.equals(sector) && sector.getLocationMinimum().getWorld().equals(this.locationMaximum.getWorld())) {
                return sector;
            }
        }

        return null;
    }
}

package org.pieszku.api.sector;

import org.pieszku.api.serializer.LocationSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SectorService {

    private final List<Sector> sectorList = new ArrayList<>();


    public Optional<Sector> findSectorByName(String sectorName){
        return this.sectorList
                .stream()
                .filter(sector -> sector.getName().equalsIgnoreCase(sectorName))
                .findFirst();
    }
    public Sector findSectorByPlayer(String nickName){
        return this.sectorList
                .stream()
                .filter(sector -> sector.hasPlayer(nickName))
                .findFirst()
                .orElse(null);
    }
    public Optional<Sector> getSectorByLocation(String world, int x, int z){
        return this.sectorList
                .stream()
                .filter(sector -> sector.isInSector(x, z, world))
                .findFirst();
    }
    public Sector findSectorSpawn(){
     return    this.sectorList
                .stream()
                .filter(Sector::isOnline)
                .filter(sector -> sector.getSectorType().equals(SectorType.SPAWN))
                .findFirst()
                .orElse(null);
    }
    public Sector findSectorTeleport(){
        return this.sectorList
                .stream()
                .filter(Sector::isOnline)
                .filter(sector -> sector.getSectorType().equals(SectorType.TELEPORT))
                .findFirst()
                .orElse(null);
    }
    public Sector findSectorCustom(){
        return this.sectorList
                .stream()
                .filter(Sector::isOnline)
                .filter(sector -> sector.getSectorType().equals(SectorType.CUSTOM))
                .findFirst()
                .orElse(null);
    }
    public int getCountOnlineSectors(){
        return this.sectorList.stream().map(Sector::isOnline).collect(Collectors.toSet()).size() - 1;
    }
    public boolean isOnlinePlayer(String nickName){
        return this.findSectorByPlayer(nickName) != null;
    }

    public void create(String name, SectorType sectorType, LocationSerializer locationMinimum, LocationSerializer locationMaximum, int minSlots, int maxSlots){
        Sector sector = new Sector(name, sectorType, locationMinimum, locationMaximum, minSlots, maxSlots);
        this.sectorList.add(sector);
    }

    public List<Sector> getSectorList() {
        return sectorList;
    }
}

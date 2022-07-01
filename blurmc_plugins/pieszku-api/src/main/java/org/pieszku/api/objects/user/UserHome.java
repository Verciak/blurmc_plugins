package org.pieszku.api.objects.user;

import org.pieszku.api.serializer.LocationSerializer;
import org.pieszku.api.type.GroupType;

import java.io.Serializable;

public class UserHome implements Serializable {

    private final String name;
    private final int inventorySlot;
    private LocationSerializer locationSerializer;
    private final GroupType groupType;

    public UserHome(String name, int inventorySlot, GroupType groupType){
        this.name = name;
        this.inventorySlot = inventorySlot;
        this.locationSerializer = new LocationSerializer("world", 0, 80, 0);
        this.groupType = groupType;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public int getInventorySlot() {
        return inventorySlot;
    }

    public LocationSerializer getLocationSerializer() {
        return locationSerializer;
    }

    public String getName() {
        return name;
    }
    public void setLocationSerializer(LocationSerializer locationSerializer) {
        this.locationSerializer = locationSerializer;
    }
    public boolean hasSet(){
        return this.locationSerializer.getX() != 0 && this.locationSerializer.getY() != 80 && this.locationSerializer.getZ() != 0;
    }
}

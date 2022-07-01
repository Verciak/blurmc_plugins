package org.pieszku.api.objects.user;

import org.pieszku.api.redis.packet.type.UpdateType;
import org.pieszku.api.type.GroupType;

import java.io.Serializable;

public class UserEnderchest implements Serializable {

    private final String name;
    private final int inventorySlot;
    private String inventorySerialized;
    private final GroupType groupType;

    public UserEnderchest(String name, int inventorySlot, GroupType groupType){
        this.name = name;
        this.inventorySlot = inventorySlot;
        this.groupType = groupType;
        this.inventorySerialized = "null";
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public int getInventorySlot() {
        return inventorySlot;
    }

    public void setInventorySerialized(User user, String inventorySerialized) {
        this.inventorySerialized = inventorySerialized;
        user.synchronizeUser(UpdateType.UPDATE);
    }

    public String getInventorySerialized() {
        return inventorySerialized;
    }

    public String getName() {
        return name;
    }
}

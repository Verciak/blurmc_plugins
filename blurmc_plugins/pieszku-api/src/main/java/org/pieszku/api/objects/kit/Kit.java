package org.pieszku.api.objects.kit;

import org.pieszku.api.impl.Identifiable;
import org.pieszku.api.type.GroupType;

import java.io.Serializable;

public class Kit implements Serializable, Identifiable<String> {

    private String kitName;
    private String inventoryName;
    private int inventorySlot;
    private String inventoryMaterial;
    private String itemSerialized;
    private String glassItemSerialized;
    private GroupType permissionAccess;
    private int pickupDelay;

    public Kit(String kitName, String inventoryName, int inventorySlot, String inventoryMaterial){
        this.kitName = kitName;
        this.inventoryName = inventoryName;
        this.inventorySlot = inventorySlot;
        this.inventoryMaterial= inventoryMaterial;
        this.itemSerialized = null;
        this.glassItemSerialized = null;
        this.permissionAccess = GroupType.PLAYER;
        this.pickupDelay = 1;
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public void setInventoryName(String inventoryName) {
        this.inventoryName = inventoryName;
    }

    public int getInventorySlot() {
        return inventorySlot;
    }

    public void setInventorySlot(int inventorySlot) {
        this.inventorySlot = inventorySlot;
    }

    public String getInventoryMaterial() {
        return inventoryMaterial;
    }

    public void setInventoryMaterial(String inventoryMaterial) {
        this.inventoryMaterial = inventoryMaterial;
    }

    public String getItemSerialized() {
        return itemSerialized;
    }

    public void setItemSerialized(String itemSerialized) {
        this.itemSerialized = itemSerialized;
    }

    public String getGlassItemSerialized() {
        return glassItemSerialized;
    }

    public void setGlassItemSerialized(String glassItemSerialized) {
        this.glassItemSerialized = glassItemSerialized;
    }

    public GroupType getPermissionAccess() {
        return permissionAccess;
    }

    public void setPermissionAccess(GroupType permissionAccess) {
        this.permissionAccess = permissionAccess;
    }

    public int getPickupDelay() {
        return pickupDelay;
    }

    public void setPickupDelay(int pickupDelay) {
        this.pickupDelay = pickupDelay;
    }

    public String getKitName() {
        return kitName;
    }

    public void setKitName(String kitName) {
        this.kitName = kitName;
    }

    @Override
    public String getId() {
        return this.kitName;
    }

    @Override
    public void setId(String s) {

    }
}

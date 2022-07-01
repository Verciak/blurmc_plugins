package org.pieszku.api.data.guild;

import java.io.Serializable;
import java.util.Arrays;

public class PermissionGuildData implements Serializable {

    public int id;
    public String inventoryName;
    public String[] inventoryLore;
    private int inventorySlot;
    private int materialAmount;
    private String material;
    public PermissionGuildActionType actionType;

    public int getId() {
        return id;
    }

    public PermissionGuildActionType getActionType() {
        return actionType;
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public String[] getInventoryLore() {
        return inventoryLore;
    }

    public int getInventorySlot() {
        return inventorySlot;
    }

    public String getMaterial() {
        return material;
    }

    public int getMaterialAmount() {
        return materialAmount;
    }

    @Override
    public String toString() {
        return "PermissionGuildData{" +
                "id=" + id +
                ", inventoryName='" + inventoryName + '\'' +
                ", inventoryLore=" + Arrays.toString(inventoryLore) +
                ", inventorySlot=" + inventorySlot +
                ", materialAmount=" + materialAmount +
                ", material='" + material + '\'' +
                ", actionType=" + actionType +
                '}';
    }
}

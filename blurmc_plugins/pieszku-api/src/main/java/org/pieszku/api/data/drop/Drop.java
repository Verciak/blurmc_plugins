package org.pieszku.api.data.drop;

import java.io.Serializable;
import java.util.Arrays;

public class Drop implements Serializable {

    public int id;
    public String inventoryName;
    public String[] inventoryLore;
    public int inventorySlot;
    public double chance;
    public String material;
    public short materialId;
    public String materialName;
    public String[] materialLore;
    public String[] materialEnchants;


    public int getId() {
        return id;
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
    public double getChance() {
        return chance;
    }
    public String getMaterial() {
        return material;
    }
    public short getMaterialId() {
        return materialId;
    }
    public String getMaterialName() {
        return materialName;
    }
    public String[] getMaterialLore() {
        return materialLore;
    }
    public String[] getMaterialEnchants() {
        return materialEnchants;
    }

    @Override
    public String toString() {
        return "Drop{" +
                "id=" + id +
                ", inventoryName='" + inventoryName + '\'' +
                ", inventoryLore=" + Arrays.toString(inventoryLore) +
                ", inventorySlot=" + inventorySlot +
                ", chance=" + chance +
                ", material='" + material + '\'' +
                ", materialId=" + materialId +
                ", materialName='" + materialName + '\'' +
                ", materialLore=" + Arrays.toString(materialLore) +
                ", materialEnchants=" + Arrays.toString(materialEnchants) +
                '}';
    }
}

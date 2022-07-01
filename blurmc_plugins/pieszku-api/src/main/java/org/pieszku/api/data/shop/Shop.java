package org.pieszku.api.data.shop;

import java.io.Serializable;
import java.util.Arrays;

public class Shop implements Serializable {


    public String inventoryName;
    public String[] inventoryLore;
    public int inventorySlot;
    public int count;
    public String material;
    public String materialName;
    public int materialAmount;
    public String[] materialLore;
    public String[] materialEnchants;


    public int getMaterialAmount() {
        return materialAmount;
    }

    public String getInventoryName() {
        return inventoryName;
    }


    public String getMaterialName() {
        return materialName;
    }

    public String[] getMaterialEnchants() {
        return materialEnchants;
    }

    public String[] getMaterialLore() {
        return materialLore;
    }


    public int getCount() {
        return count;
    }

    public String getMaterial() {
        return material;
    }

    public int getInventorySlot() {
        return inventorySlot;
    }

    public String[] getInventoryLore() {
        return inventoryLore;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "inventoryName='" + inventoryName + '\'' +
                ", inventoryLore=" + Arrays.toString(inventoryLore) +
                ", inventorySlot=" + inventorySlot +
                ", count=" + count +
                ", material='" + material + '\'' +
                ", materialName='" + materialName + '\'' +
                ", materialAmount=" + materialAmount +
                ", materialLore=" + Arrays.toString(materialLore) +
                ", materialEnchants=" + Arrays.toString(materialEnchants) +
                '}';
    }
}

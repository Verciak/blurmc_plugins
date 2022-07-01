package org.pieszku.api.data.shop;

import java.io.Serializable;
import java.util.Arrays;

public class ShopVillagerJson implements Serializable {

    public Shop[] tntShop;
    public Shop[] generatorShop;
    public Shop[] enchantmentShop;
    public Shop[] fightShop;
    public Shop[] pickaxeShop;
    public Shop[] designShop;
    public Shop[] blocksShop;

    public Shop[] getEnchantmentShop() {
        return enchantmentShop;
    }

    public Shop[] getFightShop() {
        return fightShop;
    }

    public Shop[] getBlocksShop() {
        return blocksShop;
    }

    public Shop[] getDesignShop() {
        return designShop;
    }

    public Shop[] getGeneratorShop() {
        return generatorShop;
    }

    public Shop[] getPickaxeShop() {
        return pickaxeShop;
    }

    public Shop[] getTntShop() {
        return tntShop;
    }

    @Override
    public String toString() {
        return "ShopVillager{" +
                "tntShop=" + Arrays.toString(tntShop) +
                ", generatorShop=" + Arrays.toString(generatorShop) +
                ", enchantmentShop=" + Arrays.toString(enchantmentShop) +
                ", fightShop=" + Arrays.toString(fightShop) +
                ", pickaxeShop=" + Arrays.toString(pickaxeShop) +
                ", designShop=" + Arrays.toString(designShop) +
                ", blocksShop=" + Arrays.toString(blocksShop) +
                '}';
    }
}

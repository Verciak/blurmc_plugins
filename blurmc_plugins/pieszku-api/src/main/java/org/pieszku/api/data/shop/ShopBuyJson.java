package org.pieszku.api.data.shop;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

public class ShopBuyJson implements Serializable {


    public Shop[] shops;

    public Shop[] getShops() {
        return shops;
    }
    @Override
    public String toString() {
        return "ShopBuyJson{" +
                "shops=" + Arrays.toString(shops) +
                '}';
    }

    public Optional<Shop> findShopByInventorySlot(int slot) {
        return Arrays.stream(shops)
                .filter(shop -> shop.getInventorySlot() == slot)
                .findFirst();
    }
}

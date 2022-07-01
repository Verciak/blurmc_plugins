package org.pieszku.api.data.shop;

import java.io.Serializable;
import java.util.Arrays;

public class ShopSellJson implements Serializable {

    public Shop[] shops;

    public Shop[] getShops() {
        return shops;
    }

    @Override
    public String toString() {
        return "ShopSellJson{" +
                "shops=" + Arrays.toString(shops) +
                '}';
    }
}

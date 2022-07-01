package org.pieszku.api.data.drop;

import java.io.Serializable;
import java.util.Arrays;

public class StoneDropJson implements Serializable {

    public Drop[] drops;

    public Drop[] getDrops() {
        return drops;
    }

    @Override
    public String toString() {
        return "StoneDropJson{" +
                "drops=" + Arrays.toString(drops) +
                '}';
    }

    public Drop findDropByInventorySlot(int slot) {
        return Arrays.stream(this.drops)
                .filter(drop -> drop.getInventorySlot() == slot)
                .findFirst()
                .orElse(null);
    }
}

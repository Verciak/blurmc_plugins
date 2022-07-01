package org.pieszku.api.data.drop;

import java.io.Serializable;
import java.util.Arrays;

public class CaseDropJson implements Serializable {

    public Drop[] drops;

    public Drop[] getDrops() {
        return drops;
    }

    @Override
    public String toString() {
        return "CaseDropJson{" +
                "drops=" + Arrays.toString(drops) +
                '}';
    }
}

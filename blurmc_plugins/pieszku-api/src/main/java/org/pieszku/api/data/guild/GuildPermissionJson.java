package org.pieszku.api.data.guild;

import java.io.Serializable;
import java.util.Arrays;

public class GuildPermissionJson implements Serializable {

    public PermissionGuildData[] permissionData;

    public PermissionGuildData[] getPermissionData() {
        return permissionData;
    }

    @Override
    public String toString() {
        return "GuildPermissionJson{" +
                "permissionData=" + Arrays.toString(permissionData) +
                '}';
    }

    public PermissionGuildData findPermissionByInventorySlot(int slot) {
        return Arrays.stream(this.permissionData)
                .filter(permissionGuildData -> permissionGuildData.getInventorySlot() == slot)
                .findFirst()
                .orElse(null);
    }
}

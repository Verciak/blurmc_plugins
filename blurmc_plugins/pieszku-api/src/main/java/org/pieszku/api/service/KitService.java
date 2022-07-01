package org.pieszku.api.service;

import org.pieszku.api.objects.kit.Kit;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class KitService {

    private Set<Kit> kits = new HashSet<>();

    public Optional<Kit> findKitByName(String kitName){
        return this.kits
                .stream()
                .filter(kit -> kit.getKitName().equalsIgnoreCase(kitName))
                .findFirst();
    }
    public Optional<Kit> findKitBySlot(int inventorySlot){
        return this.kits
                .stream()
                .filter(kit -> kit.getInventorySlot() == inventorySlot)
                .findFirst();
    }

    public void setKits(Set<Kit> kits) {
        this.kits = kits;
    }

    public Set<Kit> getKits() {
        return kits;
    }
}

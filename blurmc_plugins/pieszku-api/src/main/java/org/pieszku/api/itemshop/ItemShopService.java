package org.pieszku.api.itemshop;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ItemShopService {

    private final Set<ItemShop> itemShops = new HashSet<>();

    public Optional<ItemShop> findItemShopById(int id){
        return this.itemShops
                .stream()
                .filter(itemShop -> itemShop.getId() == id)
                .findFirst();
    }
    public ItemShop create(String commandLine){
        ItemShop itemShop = new ItemShop(this.itemShops.size() + 1, commandLine);
        this.itemShops.add(itemShop);
        return itemShop;
    }

    public Set<ItemShop> getItemShops() {
        return itemShops;
    }
}

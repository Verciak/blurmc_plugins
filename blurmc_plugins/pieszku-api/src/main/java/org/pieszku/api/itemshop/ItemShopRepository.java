package org.pieszku.api.itemshop;

import org.pieszku.api.impl.repository.json.JsonRepository;

public class ItemShopRepository extends JsonRepository<Integer, ItemShop> {

    public ItemShopRepository() {
        super(ItemShop.class, "id", "itemshops");
    }
}

package org.pieszku.server.handler.itemshop;

import org.pieszku.api.API;
import org.pieszku.api.itemshop.ItemShopRepository;
import org.pieszku.api.itemshop.ItemShopService;
import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.itemshop.ItemShopWebsiteSynchronizePacket;
import org.pieszku.api.sector.SectorService;
import org.pieszku.server.ServerMain;
import org.pieszku.server.utilities.ItemShopInfoUtilities;

public class ItemShopWebsiteSynchronizeHandler extends RedisListener<ItemShopWebsiteSynchronizePacket> {


    private final ItemShopService itemShopService = API.getInstance().getItemShopService();
    private final ItemShopRepository itemShopRepository = API.getInstance().getItemShopRepository();
    private final SectorService sectorService = ServerMain.getInstance().getSectorService();

    private final ItemShopInfoUtilities itemShopInfoUtilities = new ItemShopInfoUtilities();

    public ItemShopWebsiteSynchronizeHandler() {
        super("MASTER", ItemShopWebsiteSynchronizePacket.class);
    }

    @Override
    public void onDecode(ItemShopWebsiteSynchronizePacket packet) {
        switch (packet.getUpdateType()){
            case REMOVE:{
                this.itemShopService.findItemShopById(packet.getId()).ifPresent(itemShop -> {
                    this.itemShopService.getItemShops().remove(itemShop);
                    this.itemShopRepository.update(itemShop, itemShop.getId(), packet.getUpdateType());
                });
                break;
            }
            case CREATE:{
                this.itemShopService.findItemShopById(packet.getId()).ifPresent(itemShop -> {
                    this.itemShopService.getItemShops().add(itemShop);
                    this.itemShopRepository.update(itemShop, itemShop.getId(), packet.getUpdateType());
                    this.itemShopInfoUtilities.activeProduct(itemShop.getCommandLine());
                });
                break;
            }
        }
    }
}

package org.pieszku.server.handler.itemshop;

import com.google.gson.Gson;
import org.pieszku.api.API;
import org.pieszku.api.itemshop.ItemShop;
import org.pieszku.api.itemshop.ItemShopService;
import org.pieszku.api.redis.packet.itemshop.ItemShopWebsiteSynchronizePacket;
import org.pieszku.api.redis.packet.type.UpdateType;

public class ItemShopReceivedWebsiteHandler implements io.lettuce.core.pubsub.RedisPubSubListener<String, String>{


    private final ItemShopService itemShopService = API.getInstance().getItemShopService();

    @Override
    public void message(String channel, String message) {
        if(channel.equals("blurmc")){
            ItemShop itemShop = this.itemShopService.create(message);
            System.out.println("[MASTER-SERVER] new itemshop buy: " + itemShop.getId() + " : " + itemShop.getCommandLine());
            new ItemShopWebsiteSynchronizePacket(itemShop.getId(), new Gson().toJson(itemShop), UpdateType.CREATE).sendToChannel("MASTER");
        }
    }

    @Override
    public void message(String pattern, String channel, String message) {

    }

    @Override
    public void subscribed(String channel, long count) {

    }

    @Override
    public void psubscribed(String pattern, long count) {

    }

    @Override
    public void unsubscribed(String channel, long count) {

    }

    @Override
    public void punsubscribed(String pattern, long count) {

    }
}

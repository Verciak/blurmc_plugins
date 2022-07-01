package org.pieszku.api.redis.packet.itemshop;

import org.pieszku.api.redis.packet.Packet;
import org.pieszku.api.redis.packet.type.UpdateType;

public class ItemShopWebsiteSynchronizePacket extends Packet {

    private final int id;
    private final String serializedItemShop;
    private final UpdateType updateType;

    public ItemShopWebsiteSynchronizePacket(int id, String serializedItemShop, UpdateType updateType){
        this.id = id;
        this.serializedItemShop = serializedItemShop;
        this.updateType =  updateType;
    }

    public int getId() {
        return id;
    }

    public UpdateType getUpdateType() {
        return updateType;
    }

    public String getSerializedItemShop() {
        return serializedItemShop;
    }

    @Override
    public String toString() {
        return "ItemShopWebsiteSynchronizePacket{" +
                "id=" + id +
                ", serializedItemShop='" + serializedItemShop + '\'' +
                ", updateType=" + updateType +
                '}';
    }
}

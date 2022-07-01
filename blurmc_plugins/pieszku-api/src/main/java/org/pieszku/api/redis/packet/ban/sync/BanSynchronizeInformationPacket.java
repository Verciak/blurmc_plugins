package org.pieszku.api.redis.packet.ban.sync;

import org.pieszku.api.redis.packet.Packet;
import org.pieszku.api.redis.packet.ban.type.BanType;
import org.pieszku.api.redis.packet.type.UpdateType;

public class BanSynchronizeInformationPacket extends Packet {


    private final String nickName;
    private final UpdateType updateType;
    private final BanType banType;
    private final String banGsonSerializer;

    public BanSynchronizeInformationPacket(String nickName, UpdateType updateType, BanType banType, String banGsonSerializer){
        this.nickName = nickName;
        this.updateType =  updateType;
        this.banType = banType;
        this.banGsonSerializer = banGsonSerializer;
    }

    public String getNickName() {
        return nickName;
    }

    public UpdateType getUpdateType() {
        return updateType;
    }

    public String getBanGsonSerializer() {
        return banGsonSerializer;
    }

    public BanType getBanType() {
        return banType;
    }

    @Override
    public String
    toString() {
        return "BanSynchronizeInformationPacket{" +
                "nickName='" + nickName + '\'' +
                ", updateType=" + updateType +
                ", banType=" + banType +
                ", banGsonSerializer=" + banGsonSerializer +
                '}';
    }
}

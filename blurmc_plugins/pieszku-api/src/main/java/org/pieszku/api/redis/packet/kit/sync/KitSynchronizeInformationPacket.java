package org.pieszku.api.redis.packet.kit.sync;

import org.pieszku.api.redis.packet.Packet;
import org.pieszku.api.redis.packet.type.UpdateType;

public class KitSynchronizeInformationPacket extends Packet {


    private final String kitName;
    private final UpdateType updateType;
    private final String serializedKit;

    public KitSynchronizeInformationPacket(String kitName, UpdateType updateType, String serializedKit){
        this.kitName = kitName;
        this.updateType = updateType;
        this.serializedKit = serializedKit;
    }


    public String getKitName() {
        return kitName;
    }

    public UpdateType getUpdateType() {
        return updateType;
    }

    public String getSerializedKit() {
        return serializedKit;
    }

    @Override
    public String toString() {
        return "KitSynchronizeInformationPacket{" +
                "kitName='" + kitName + '\'' +
                ", updateType=" + updateType +
                ", serializedKit='" + serializedKit + '\'' +
                '}';
    }
}

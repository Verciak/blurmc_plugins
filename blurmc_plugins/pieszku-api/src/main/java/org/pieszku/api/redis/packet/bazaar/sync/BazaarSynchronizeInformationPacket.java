package org.pieszku.api.redis.packet.bazaar.sync;

import org.pieszku.api.redis.packet.Packet;
import org.pieszku.api.redis.packet.type.UpdateType;

public class BazaarSynchronizeInformationPacket extends Packet {


    private final int id;
    private final UpdateType updateType;
    private final String serializedBazaar;

    public BazaarSynchronizeInformationPacket(int id, UpdateType updateType, String serializedBazaar){
        this.id = id;
        this.updateType = updateType;
        this.serializedBazaar = serializedBazaar;
    }

    public int getId() {
        return id;
    }

    public UpdateType getUpdateType() {
        return updateType;
    }

    public String getSerializedBazaar() {
        return serializedBazaar;
    }

    @Override
    public String toString() {
        return "BazaarSynchronizeInformationPacket{" +
                "id=" + id +
                ", updateType=" + updateType +
                ", serializedBazaar='" + serializedBazaar + '\'' +
                '}';
    }
}

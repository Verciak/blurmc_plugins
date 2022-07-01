package org.pieszku.api.redis.packet.backup;

import org.pieszku.api.redis.packet.Packet;
import org.pieszku.api.redis.packet.type.UpdateType;

public class BackupSynchronizeInformationPacket extends Packet {

    private final int id;
    private final String serializedBackup;
    private final UpdateType updateType;


    public BackupSynchronizeInformationPacket(int id, String serializedBackup, UpdateType updateType){
        this.id = id;
        this.serializedBackup = serializedBackup;
        this.updateType = updateType;
    }

    public UpdateType getUpdateType() {
        return updateType;
    }

    public int getId() {
        return id;
    }

    public String getSerializedBackup() {
        return serializedBackup;
    }

    @Override
    public String toString() {
        return "BackupSynchronizeInformationPacket{" +
                "id=" + id +
                ", serializedBackup='" + serializedBackup + '\'' +
                ", updateType=" + updateType +
                '}';
    }
}

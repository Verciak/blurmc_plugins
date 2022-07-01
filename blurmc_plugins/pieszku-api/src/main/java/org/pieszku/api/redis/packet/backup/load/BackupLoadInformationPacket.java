package org.pieszku.api.redis.packet.backup.load;

import org.pieszku.api.objects.backup.Backup;
import org.pieszku.api.redis.packet.Packet;

import java.util.Set;

public class BackupLoadInformationPacket extends Packet {

    private final Set<Backup> backups;

    public BackupLoadInformationPacket(Set<Backup> backups){
        this.backups = backups;
    }

    public Set<Backup> getBackups() {
        return backups;
    }

    @Override
    public String toString() {
        return "BackupLoadInformationPacket{" +
                "backups=" + backups +
                '}';
    }
}

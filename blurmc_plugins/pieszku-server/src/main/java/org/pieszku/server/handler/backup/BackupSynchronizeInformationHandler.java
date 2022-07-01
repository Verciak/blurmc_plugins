package org.pieszku.server.handler.backup;

import com.google.gson.Gson;
import org.pieszku.api.API;
import org.pieszku.api.objects.backup.Backup;
import org.pieszku.api.objects.repository.BackupRepository;
import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.backup.BackupSynchronizeInformationPacket;
import org.pieszku.api.sector.SectorService;
import org.pieszku.api.service.BackupService;
import org.pieszku.server.ServerMain;

public class BackupSynchronizeInformationHandler extends RedisListener<BackupSynchronizeInformationPacket> {


    private final BackupService backupService = API.getInstance().getBackupService();
    private final BackupRepository backupRepository = API.getInstance().getBackupRepository();
    private final SectorService sectorService = ServerMain.getInstance().getSectorService();

    public BackupSynchronizeInformationHandler() {
        super("MASTER", BackupSynchronizeInformationPacket.class);
    }

    @Override
    public void onDecode(BackupSynchronizeInformationPacket packet) {
        switch (packet.getUpdateType()){
            case CREATE:{
                Backup backup = new Gson().fromJson(packet.getSerializedBackup(), Backup.class);
                this.backupService.getBackups().add(backup);
                this.backupRepository.update(backup, backup.getId(), packet.getUpdateType());
                new BackupSynchronizeInformationPacket(packet.getId(), new Gson().toJson(backup), packet.getUpdateType()).sendToAllSectors(this.sectorService);
                break;
            }
            case REMOVE:{
                this.backupService.findBackupById(packet.getId()).ifPresent(backup -> {
                    this.backupService.getBackups().remove(backup);
                    this.backupRepository.update(backup, backup.getId(), packet.getUpdateType());
                    new BackupSynchronizeInformationPacket(packet.getId(), new Gson().toJson(backup), packet.getUpdateType()).sendToAllSectors(this.sectorService);
                });

                break;
            }
        }
    }
}

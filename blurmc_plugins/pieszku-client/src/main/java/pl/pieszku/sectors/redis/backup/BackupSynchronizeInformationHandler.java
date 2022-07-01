package pl.pieszku.sectors.redis.backup;

import com.google.gson.Gson;
import org.pieszku.api.API;
import org.pieszku.api.objects.backup.Backup;
import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.backup.BackupSynchronizeInformationPacket;
import org.pieszku.api.service.BackupService;
import pl.pieszku.sectors.BukkitMain;

public class BackupSynchronizeInformationHandler extends RedisListener<BackupSynchronizeInformationPacket> {


    private final BackupService backupService = API.getInstance().getBackupService();

    public BackupSynchronizeInformationHandler() {
        super(BukkitMain.getInstance().getSectorName(), BackupSynchronizeInformationPacket.class);
    }

    @Override
    public void onDecode(BackupSynchronizeInformationPacket packet) {
        switch (packet.getUpdateType()){
            case CREATE:{
                Backup backup = new Gson().fromJson(packet.getSerializedBackup(), Backup.class);
                this.backupService.getBackups().add(backup);
                break;
            }
            case REMOVE:{
                this.backupService.findBackupById(packet.getId()).ifPresent(backup -> {
                    this.backupService.getBackups().remove(backup);
                });
                
                break;
            }
        }
    }
}

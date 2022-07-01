package org.pieszku.api.objects.repository;

import org.pieszku.api.impl.repository.json.JsonRepository;
import org.pieszku.api.objects.backup.Backup;

public class BackupRepository extends JsonRepository<Integer, Backup> {


    public BackupRepository() {
        super(Backup.class, "id", "backups");
    }
}

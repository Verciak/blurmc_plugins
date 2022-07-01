package org.pieszku.api.service;

import org.pieszku.api.objects.backup.Backup;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class BackupService {

    private Set<Backup> backups = new HashSet<>();

    public Optional<Backup> findBackupByNickName(String nickName){
        return this.backups
                .stream()
                .filter(backup -> backup.getNickName().equalsIgnoreCase(nickName))
                .findFirst();
    }

    public Optional<Backup> findBackupById(int id){
        return this.backups
                .stream()
                .filter(backup -> backup.getId() == id)
                .findFirst();
    }

    public Set<Backup> getBackups() {
        return backups;
    }

    public void setBackups(Set<Backup> backups) {
        this.backups = backups;
    }
}

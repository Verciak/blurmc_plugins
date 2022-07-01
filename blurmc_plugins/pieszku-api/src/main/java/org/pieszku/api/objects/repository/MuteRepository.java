package org.pieszku.api.objects.repository;

import org.pieszku.api.impl.repository.json.JsonRepository;
import org.pieszku.api.objects.mute.Mute;

public class MuteRepository extends JsonRepository<String, Mute> {

    public MuteRepository() {
        super(Mute.class, "nickName", "mutes");
    }
}

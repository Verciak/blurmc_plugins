package org.pieszku.api.objects.repository;

import org.pieszku.api.impl.repository.json.JsonRepository;
import org.pieszku.api.objects.ban.Ban;

public class BanRepository extends JsonRepository<String, Ban> {

    public BanRepository() {
        super(Ban.class, "nickName", "bans");
    }
}

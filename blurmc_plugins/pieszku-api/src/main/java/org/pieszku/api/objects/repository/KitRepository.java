package org.pieszku.api.objects.repository;

import org.pieszku.api.impl.repository.json.JsonRepository;
import org.pieszku.api.objects.kit.Kit;

public class KitRepository extends JsonRepository<String, Kit> {

    public KitRepository() {
        super(Kit.class, "kitName", "kits");
    }
}

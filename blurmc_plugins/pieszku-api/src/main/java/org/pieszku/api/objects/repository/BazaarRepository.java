package org.pieszku.api.objects.repository;

import org.pieszku.api.impl.repository.json.JsonRepository;
import org.pieszku.api.objects.bazaar.Bazaar;

public class BazaarRepository extends JsonRepository<Integer, Bazaar> {
    public BazaarRepository() {
        super(Bazaar.class, "id", "bazaars");
    }
}

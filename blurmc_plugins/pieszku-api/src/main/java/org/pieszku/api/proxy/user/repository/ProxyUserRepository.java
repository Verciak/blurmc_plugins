package org.pieszku.api.proxy.user.repository;

import org.pieszku.api.impl.repository.json.JsonRepository;
import org.pieszku.api.proxy.user.ProxyUser;

public class ProxyUserRepository extends JsonRepository<String, ProxyUser> {

    public ProxyUserRepository() {
        super(ProxyUser.class, "nickName", "proxy_users");
    }
}

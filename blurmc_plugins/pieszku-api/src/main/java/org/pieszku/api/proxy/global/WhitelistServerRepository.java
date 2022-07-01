package org.pieszku.api.proxy.global;

import org.pieszku.api.impl.repository.json.JsonRepository;

public class WhitelistServerRepository extends JsonRepository<String, WhitelistServer> {


    public WhitelistServerRepository() {
        super(WhitelistServer.class, "channelName", "whitelist");
    }
}

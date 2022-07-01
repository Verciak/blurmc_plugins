package org.pieszku.api.proxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProxyService {

    private final List<Proxy> proxyList = new ArrayList<>();

    public Optional<Proxy> findProxyByName(String proxyName){
        return this.proxyList
                .stream()
                .filter(proxy -> proxy.getName().equalsIgnoreCase(proxyName))
                .findFirst();
    }

    public List<Proxy> getProxyList() {
        return proxyList;
    }
}

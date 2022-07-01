package org.pieszku.api.proxy.json;

import org.pieszku.api.proxy.Proxy;

import java.io.Serializable;
import java.util.Arrays;

public class ProxyJson implements Serializable {


    public Proxy[] proxies;

    public Proxy[] getProxies() {
        return proxies;
    }

    @Override
    public String toString() {
        return "ProxyJson{" +
                "proxies=" + Arrays.toString(proxies) +
                '}';
    }
}

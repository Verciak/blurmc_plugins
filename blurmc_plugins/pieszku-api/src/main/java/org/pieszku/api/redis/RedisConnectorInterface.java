package org.pieszku.api.redis;

public interface RedisConnectorInterface {

    void connect();
    boolean status();
    void disconnect();
}

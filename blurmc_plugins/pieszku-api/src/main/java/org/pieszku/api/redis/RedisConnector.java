package org.pieszku.api.redis;

import org.pieszku.api.redis.service.RedisService;

public class RedisConnector implements RedisConnectorInterface {

    private final String host;
    private final String password;
    private final RedisSystem redisSystem;
    private final RedisService redisService;

    public RedisConnector(String host, String password) {
        this.host = host;
        this.password = password;
        this.redisSystem = new RedisSystem();
        this.redisService = new RedisService(this);
    }

    @Override
    public void connect() {
        this.redisSystem.connect(this.host, this.password);
    }

    @Override
    public boolean status() {
        return this.redisSystem.getConnection().isOpen();
    }

    @Override
    public void disconnect() {
        this.redisSystem.getRedisClient().shutdown();
    }

    public RedisSystem getRedisSystem() {
        return redisSystem;
    }

    public String getHost() {
        return host;
    }

    public String getPassword() {
        return password;
    }

    public RedisService getRedisService() {
        return redisService;
    }
}

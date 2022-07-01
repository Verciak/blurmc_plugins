package org.pieszku.api.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import org.nustaq.serialization.FSTConfiguration;
import org.pieszku.api.redis.impl.FSTCodec;
import org.pieszku.api.redis.packet.Packet;

public class RedisSystem {

    protected static FSTConfiguration FST_CONFIG;
    protected static FSTCodec FST_CODEC;
    private RedisClient redisClient;
    private StatefulRedisPubSubConnection<String, Packet> pubSubConnection;
    private StatefulRedisConnection<String, String> databaseConnect;
    private StatefulRedisConnection<String, Packet> connection;
    private static RedisSystem instance;

    public static RedisSystem getInstance() {
        return instance;
    }

    public RedisSystem(){
        instance = this;
        FST_CONFIG = FSTConfiguration.createDefaultConfiguration();
        FST_CODEC =  new FSTCodec();
    }

    public void connect(String host, String password) {
        redisClient = RedisClient.create(RedisURI.builder()
                .withHost(host)
                .withPort(6379)
                .withPassword(password)
                .build());

        pubSubConnection = redisClient.connectPubSub(FST_CODEC);
        databaseConnect = redisClient.connect();
        connection = redisClient.connect(FST_CODEC);

        System.out.println("[REDIS-SERVER] Connected successfully");
    }


    public void disconnect(){
        redisClient.shutdown();
    }

    public FSTCodec getFstCodec() {
        return FST_CODEC;
    }

    public  RedisClient getRedisClient() {
        return redisClient;
    }

    public FSTConfiguration getFstConfig() {
        return FST_CONFIG;
    }

    public  StatefulRedisConnection<String, Packet> getConnection() {
        return connection;
    }

    public StatefulRedisConnection<String, String> getDatabaseConnect() {
        return databaseConnect;
    }

    public  StatefulRedisPubSubConnection<String, Packet> getPubSubConnection() {
        return pubSubConnection;
    }
}

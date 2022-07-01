package org.pieszku.api.redis.service;

import org.pieszku.api.redis.RedisConnector;
import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.Packet;

import java.util.ArrayList;
import java.util.List;

public class RedisService {


    private static final List<String> subscribedChannels = new ArrayList<>();
    protected static RedisService instance;
    private final RedisConnector redisConnector;

    public RedisService(RedisConnector redisConnector) {
        instance = this;
        this.redisConnector = redisConnector;
    }
    public static RedisService getInstance() {
        return instance;
    }

    public void publish(String channel, Packet packet){
        this.redisConnector.getRedisSystem().getConnection().sync().publish(channel, packet);
    }
    public void subscribe(String channel, RedisListener<? extends Packet> listener) {
        if (!channel.equals(listener.getChannel())) {
            throw new IllegalStateException("Channel from subscribe method must be the same as Listener channel");
        }
        this.redisConnector.getRedisSystem().getPubSubConnection().addListener(listener);

        if (!subscribedChannels.contains(channel)) {
            this.redisConnector.getRedisSystem().getPubSubConnection().sync().subscribe(channel);
            subscribedChannels.add(channel);
        }
    }

    public RedisConnector getRedisConnector() {
        return redisConnector;
    }
}

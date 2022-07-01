package org.pieszku.api.redis.impl;

import io.lettuce.core.pubsub.RedisPubSubListener;
import org.pieszku.api.redis.packet.Packet;

public abstract class RedisListener<T extends Packet> implements RedisPubSubListener<String, Packet> {

    private final Class<T> packetType;
    private final String channel;

    public RedisListener(String channel, Class<T> packetType){
        this.channel = channel;
        this.packetType = packetType;
    }

    public abstract void onDecode(T packet);

    @Override
    public void message(String channel, Packet packet) {
        if(this.channel.equalsIgnoreCase(channel) && packet.getClass().isAssignableFrom(this.packetType)){
            this.onDecode((T) packet);
        }
    }

    @Override
    public void message(String s, String k1, Packet packet) {

    }

    @Override
    public void subscribed(String channel, long count) {
        System.out.println("[RedisSystem] Subscribed channel: " + channel + " with listener for: " + packetType.getSimpleName());
    }

    @Override
    public void psubscribed(String s, long l) {

    }

    @Override
    public void unsubscribed(String s, long l) {

    }

    @Override
    public void punsubscribed(String s, long l) {

    }


    public String getChannel() {
        return channel;
    }
}

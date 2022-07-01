package org.pieszku.api.redis.impl;

import io.lettuce.core.codec.RedisCodec;
import org.pieszku.api.redis.RedisSystem;
import org.pieszku.api.redis.packet.Packet;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class FSTCodec implements RedisCodec<String, Packet> {

    private final Charset charset = StandardCharsets.UTF_8;

    @Override
    public String decodeKey(ByteBuffer bytes) {
        return charset.decode(bytes).toString();
    }

    @Override
    public Packet decodeValue(ByteBuffer bytes) {
        byte[] buffer = new byte[bytes.remaining()];
        bytes.get(buffer);
        return (Packet) RedisSystem.getInstance().getFstConfig().asObject(buffer);
    }

    @Override
    public ByteBuffer encodeKey(String key) {
        return charset.encode(key);
    }

    @Override
    public ByteBuffer encodeValue(Packet value) {
        return ByteBuffer.wrap(RedisSystem.getInstance().getFstConfig().asByteArray(value));
    }
}

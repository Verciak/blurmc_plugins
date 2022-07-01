package org.pieszku.api.redis.packet.client;

import org.pieszku.api.redis.packet.Packet;

public class ChatSynchronizePacket extends Packet {

    private boolean chatStatus;

    public ChatSynchronizePacket(boolean chatStatus){
        this.chatStatus = chatStatus;
    }

    public boolean isChatStatus() {
        return chatStatus;
    }

    @Override
    public String toString() {
        return "ChatSynchronizePacket{" +
                "chatStatus=" + chatStatus +
                '}';
    }
}

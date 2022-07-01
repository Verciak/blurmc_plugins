package org.pieszku.api.redis.packet.client.teleport;

import org.pieszku.api.redis.packet.Packet;

public class TeleportPlayerRequestPacket extends Packet {

    private final String nickNameTargetTo;
    private final String nickNameTeleport;
    private final long timeDelay;

    public TeleportPlayerRequestPacket(String nickNameTargetTo, String nickNameTeleport, long timeDelay){
        this.nickNameTargetTo = nickNameTargetTo;
        this.nickNameTeleport = nickNameTeleport;
        this.timeDelay = timeDelay;
    }

    public String getNickNameTeleport() {
        return nickNameTeleport;
    }

    public String getNickNameTargetTo() {
        return nickNameTargetTo;
    }

    public long getTimeDelay() {
        return timeDelay;
    }
    @Override
    public String toString() {
        return "TeleportPlayerRequestPacket{" +
                "nickNameTargetTo='" + nickNameTargetTo + '\'' +
                ", nickNameTeleport='" + nickNameTeleport + '\'' +
                ", timeDelay=" + timeDelay +
                '}';
    }
}

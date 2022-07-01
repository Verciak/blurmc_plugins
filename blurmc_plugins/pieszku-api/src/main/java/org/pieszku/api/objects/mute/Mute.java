package org.pieszku.api.objects.mute;

import org.pieszku.api.impl.Identifiable;

import java.io.Serializable;

public class Mute implements Serializable, Identifiable<String> {

    private final String nickName;
    private final String reason;
    private final long delay;
    private final String adminNickname;

    public Mute(String nickName, String reason, long delay, String adminNickname){
        this.nickName = nickName;
        this.reason = reason;
        this.delay = delay;
        this.adminNickname = adminNickname;
    }

    public long getDelay() {
        return delay;
    }

    public String getAdminNickname() {
        return adminNickname;
    }

    public String getNickName() {
        return nickName;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String getId() {
        return this.nickName;
    }

    @Override
    public void setId(String s) {

    }
}

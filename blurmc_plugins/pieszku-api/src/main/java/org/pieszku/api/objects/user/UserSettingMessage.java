package org.pieszku.api.objects.user;

import org.pieszku.api.redis.packet.type.UpdateType;
import org.pieszku.api.type.UserSettingMessageType;

import java.io.Serializable;

public class UserSettingMessage implements Serializable {


    private final UserSettingMessageType settingMessageType;
    private final String messagePolishName;
    private final int slot;
    private boolean status;


    public UserSettingMessage(UserSettingMessageType settingMessageType, String messagePolishName, int slot){
        this.settingMessageType = settingMessageType;
        this.slot = slot;
        this.messagePolishName = messagePolishName;
        this.status = true;
    }

    public int getSlot() {
        return slot;
    }

    public void setStatus(User user, boolean status) {
        this.status = status;
        user.synchronizeUser(UpdateType.UPDATE);
    }

    public String getMessagePolishName() {
        return messagePolishName;
    }

    public UserSettingMessageType getSettingMessageType() {
        return settingMessageType;
    }

    public boolean isStatus() {
        return status;
    }
}

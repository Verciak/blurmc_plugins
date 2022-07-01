package org.pieszku.api.proxy.global;

import com.google.gson.Gson;
import org.pieszku.api.impl.Identifiable;
import org.pieszku.api.redis.packet.type.UpdateType;
import org.pieszku.api.redis.packet.whitelist.sync.WhitelistSynchronizeInformationPacket;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class WhitelistServer implements Serializable, Identifiable<String> {

    private final String channelName;
    private boolean whitelist;
    private String reason;
    private final Set<String> members;
    private final WhitelistServerType whitelistServerType;

    public WhitelistServer(String channelName, WhitelistServerType whitelistServerType){
        this.channelName = channelName;
        this.whitelist = true;
        this.reason = "Serwer aktualnie jest niedostępny dla graczy";
        this.members = new HashSet<>();
        this.whitelistServerType = whitelistServerType;
        this.synchronize(UpdateType.CREATE);
    }

    public WhitelistServerType getWhitelistServerType() {
        return whitelistServerType;
    }
    public boolean isSector(){
        return this.whitelistServerType.equals(WhitelistServerType.SECTOR);
    }
    public boolean isProxy(){
        return this.whitelistServerType.equals(WhitelistServerType.PROXY);
    }

    public void addWhitelist(String nickName){
        this.members.add(nickName);
        this.synchronize(UpdateType.UPDATE);

    }
    public void removeWhitelist(String nickName){
        this.members.remove(nickName);
        this.synchronize(UpdateType.UPDATE);
    }

    public boolean isWhitelist() {
        return whitelist;
    }

    public void setWhitelist(boolean whitelist) {
        this.whitelist = whitelist;
        this.synchronize(UpdateType.UPDATE);
    }

    public void setReason(String reason) {
        this.reason = reason;
        this.synchronize(UpdateType.UPDATE);
    }

    public String getReason() {
        return reason;
    }

    public Set<String> getMembers() {
        return members;
    }

    public String getChannelName() {
        return channelName;
    }

    @Override
    public String getId() {
        return this.channelName;
    }

    @Override
    public void setId(String s) {

    }
    public void synchronize(UpdateType updateType){
        new WhitelistSynchronizeInformationPacket(this.channelName, updateType, new Gson().toJson(this)).sendToChannel("MASTER");
        new WhitelistSynchronizeInformationPacket(this.channelName, updateType, new Gson().toJson(this)).sendToChannel("MASTER");
    }
}

package org.pieszku.api.service;

import org.pieszku.api.objects.mute.Mute;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MuteService {

    private List<Mute> muteList = new ArrayList<>();

    public Mute create(String nickName, String reason, long delay, String adminNickname){
        return new Mute(nickName, reason, delay, adminNickname);
    }


    public Optional<Mute> findMuteByNickName(String nickName){
        return this.muteList
                .stream()
                .filter(mute -> mute.getNickName().equalsIgnoreCase(nickName))
                .findFirst();
    }

    public Mute delete(Mute mute){
        this.muteList.remove(mute);
        return mute;
    }

    public void setMuteList(List<Mute> muteList) {
        this.muteList = muteList;
    }

    public List<Mute> getMuteList() {
        return muteList;
    }
}

package org.pieszku.api.proxy.user;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class ProxyUserService {

    private final ConcurrentHashMap<String, ProxyUser> userMap = new ConcurrentHashMap<>();


    public CompletableFuture<ProxyUser> findUserByNickName(String nickName){
        return CompletableFuture.completedFuture(this.userMap.get(nickName));
    }
    public ProxyUser findOrCreate(String nickName){
        return this.userMap.computeIfAbsent(nickName, ProxyUser::new);
    }

    public ConcurrentHashMap<String, ProxyUser> getUserMap() {
        return userMap;
    }
}

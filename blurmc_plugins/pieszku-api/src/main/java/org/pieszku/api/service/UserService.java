package org.pieszku.api.service;

import org.pieszku.api.objects.user.User;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class UserService {

    private ConcurrentHashMap<String, User> userMap = new ConcurrentHashMap<>();

    public Optional<User> findUserByNickName(String nickName){
        return this.userMap.values()
                .stream()
                .filter(user -> user.getNickName().equalsIgnoreCase(nickName))
                .findFirst();
    }
    public CompletableFuture<User> findUserByName(String nickName) {
      return CompletableFuture.completedFuture(this.userMap.get(nickName));
    }

    public User getOrCreate(String nickName){
        return this.userMap.computeIfAbsent(nickName, User::new);
    }

    public ConcurrentHashMap<String, User> getUserMap() {
        return userMap;
    }

    public void setUserMap(ConcurrentHashMap<String, User> userMap) {
        this.userMap = userMap;
    }
}

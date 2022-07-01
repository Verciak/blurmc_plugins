package org.pieszku.api.service;

import org.pieszku.api.objects.guild.impl.Guild;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GuildService {

    private List<Guild> guildList = new ArrayList<>();


    public Optional<Guild> findGuildByName(String guildName){
        return this.guildList
                .stream()
                .filter(guild -> guild.getName().equalsIgnoreCase(guildName))
                .findFirst();
    }
    public Optional<Guild> findGuildByMember(String nickName){
        return this.guildList
                .stream()
                .filter(guild -> guild.getMembers().contains(nickName))
                .findFirst();
    }
    public Optional<Guild> findGuildByLocation(String world, int x, int z){
        return this.guildList
                .stream()
                .filter(guild -> guild.isOnCuboid(world, x, z))
                .findFirst();
    }
    public Optional<Guild> findGuildByInvite(String nickName){
        return this.guildList
                .stream()
                .filter(guild -> guild.getMembersInvite().contains(nickName))
                .findFirst();
    }

    public List<Guild> getGuildList() {
        return guildList;
    }

    public void setGuildList(List<Guild> guildList) {
        this.guildList = guildList;
    }

    public Guild findGuildByMemberGet(String nickName) {
        return this.guildList
                .stream()
                .filter(guild -> guild.getMembers().contains(nickName))
                .findFirst()
                .orElse(null);
    }
}

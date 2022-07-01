package org.pieszku.api.objects.guild;

import org.pieszku.api.objects.guild.impl.Guild;
import org.pieszku.api.redis.packet.type.UpdateType;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class GuildPermission implements Serializable {


    private final GuildPermissionType permissionType;
    private final String polishName;
    private final Set<Integer> permissionDisable;
    private final Set<String> members;


    public GuildPermission(GuildPermissionType permissionType, String polishName){
        this.permissionType = permissionType;
        this.polishName = polishName;
        this.permissionDisable = new HashSet<>();
        this.members = new HashSet<>();
    }

    public GuildPermissionType getPermissionType() {
        return permissionType;
    }

    public Set<Integer> getPermissionDisable() {
        return permissionDisable;
    }

    public Set<String> getMembers() {
        return members;
    }

    public String getPolishName() {
        return polishName;
    }
    public void addMember(Guild guild, String nickName){
        this.members.add(nickName);
        guild.synchronize(UpdateType.UPDATE);
    }
    public void removeMember(Guild guild,String nickName){
        this.members.remove(nickName);
        guild.synchronize(UpdateType.UPDATE);
    }
    public boolean hasMember(String nickName){
        return this.members.contains(nickName);
    }
    public void enablePermission(Guild guild, int id){
        this.permissionDisable.remove(id);
        guild.synchronize(UpdateType.UPDATE);
    }
    public void disablePermission(Guild guild, int id){
        this.permissionDisable.add(id);
        guild.synchronize(UpdateType.UPDATE);
    }
    public boolean hasPermissionDisable(int id){
        return this.permissionDisable.contains(id);
    }
}

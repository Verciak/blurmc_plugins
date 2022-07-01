package org.pieszku.api.objects.guild;

import org.pieszku.api.redis.packet.type.UpdateType;

public interface GuildInterface {


    void changeOwner(String nickName);
    void changeMaster(String nickName);
    void changeLeader(String nickName);
    void incrementKills();
    void incrementDeaths();
    void removeKills(int kills);
    void removePoints(int points);
    void addKills(int kills);
    void addPoints(int points);
    void addMember(String nickName);
    void removeMember(String nickname);
    void synchronize(UpdateType updateType);
    void updateLatestInformation();

}

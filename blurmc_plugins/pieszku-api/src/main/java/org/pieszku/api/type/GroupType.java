package org.pieszku.api.type;

import org.pieszku.api.objects.user.User;

import java.io.Serializable;
import java.util.Arrays;

public enum GroupType implements Serializable {

    ROOT("&4&lROOT", "&4&lROOT &7{NICKNAME}&8: &c{MESSAGE}", 999),
    DEVELOPER("&b&lDEVELOPER", "&b&lDEVELOPER &7{NICKNAME}&8: &3{MESSAGE}", 999),
    HEADADMIN("&4&lH@", "&4&lH@ &7{NICKNAME}&8: &c{MESSAGE}", 14),
    ADMIN("&c&lADMIN", "&c&lADMIN &7{NICKNAME}&8: &4{MESSAGE}", 13),
    MODERATOR("&a&lMODERATOR", "&a&lMODERATOR &7{NICKNAME}&8: &2{MESSAGE}", 12),
    HELPER("&3&lHELPER", "&3&lHELPER &7{NICKNAME}&8: &b{MESSAGE}", 11),
    PREMIUM("&b&lPREMIUM", "{LEVEL} &b&lPREMIUM {GUILD}&7{NICKNAME}&8: &f{MESSAGE}", 10),
    YOUTUBER("&f&lY&c&lT", "{LEVEL} &f&lY&c&lT {GUILD}&7{NICKNAME}&8: &f{MESSAGE}", 9),
    SPONSOR("&9&lSPONSOR", "{LEVEL} &9&lSPONSOR {GUILD}&7{NICKNAME}&8: &f{MESSAGE}", 9),
    SVIP("&3&lSVIP", "{LEVEL} &3&lSVIP {GUILD}&7{NICKNAME}&8: &f{MESSAGE}", 8),
    VIP("&6&lVIP", "{LEVEL} &6&lVIP {GUILD}&7{NICKNAME}&8: &f{MESSAGE}", 7),
    PLAYER("", "{LEVEL} {GUILD} &7{NICKNAME}&8: &f{MESSAGE}", 6);

    private final String prefix;
    private final String chatFormat;
    private final int level;

    GroupType(String prefix, String chatFormat, int level){
        this.prefix = prefix;
        this.chatFormat = chatFormat;
        this.level = level;
    }

    public static boolean groupExists(String groupName){
        return Arrays.stream(GroupType.values()).anyMatch(groupType -> groupType.name().equalsIgnoreCase(groupName));
    }

    public int getLevel() {
        return level;
    }

    public String getChatFormat() {
        return chatFormat;
    }

    public String getPrefix() {
        return prefix;
    }
    public static boolean hasPermission(User user, GroupType groupType){
        return (user.getGroupType().getLevel() >= groupType.getLevel());
    }
}

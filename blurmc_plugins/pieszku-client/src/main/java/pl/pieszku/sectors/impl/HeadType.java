package pl.pieszku.sectors.impl;

import java.util.Arrays;

public enum HeadType {

    HEAD_CRAFTING("crafting", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGMzNjA0NTIwOGY5YjVkZGNmOGM0NDMzZTQyNGIxY2ExN2I5NGY2Yjk2MjAyZmIxZTUyNzBlZThkNTM4ODFiMSJ9fX0="),
    HEAD_CHEST("chest", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWM5NmJlNzg4NmViN2RmNzU1MjVhMzYzZTVmNTQ5NjI2YzIxMzg4ZjBmZGE5ODhhNmU4YmY0ODdhNTMifX19"),
    HEAD_SEARCH("search", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTBjZDIyNDlmYWU5YTRkNjhmZjE0ODkxMzFlMWVkMTUzZDZlODI5NjZlZjMzNDQ4NWEwZGRhZTE0MmYifX19"),
    HEAD_ANVIL("kowadlo", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWI0MjVhYTNkOTQ2MThhODdkYWM5Yzk0ZjM3N2FmNmNhNDk4NGMwNzU3OTY3NGZhZDkxN2Y2MDJiN2JmMjM1In19fQ=="),
    HEAD_SEVEN("ognista", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjAyYTExNjkzMDlmMDVlZjJmMDYxYjFmYTBmZTIyNWYyOWQ3M2EyNGY4ZjA3Y2NjMmE3MDVkZWVhY2EwNjlkMSJ9fX0="),
    HEAD_SIX("krystaliczna", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjg4MTMwOTI1NmEwNjQxMzVjMDlkNDhiNzM4ODgxYzcwMmU5Y2RjMTMwNjJkYzk5MjdjZWM0ZWM0ZmU1ZWQ3YiJ9fX0="),
    HEAD_FIVE("falowana", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmM4NWUyZmRmOWIxYjAyMGFjMjgyN2QxMWFlMDBkOTBmODFjNWM2YmQzNjFjYmQxYzhiOGU5MDg3NzU3ZTRiMCJ9fX0="),
    HEAD_FOUR("oceaniczna", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzBhMzU5NTdmY2UyMzQ0YWE4NTU3YmE2YjM2MTg3YWI0MTUyNDk5MzQ2MDdiYmJlZDJhMDUzYzNhODZjYWZjMCJ9fX0="),
    HEAD_THREE("galaktyczna", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWMxMDNmNmViYTVkMWYyMzYxM2FmOTA2Mjg2Njg5MjllNGQ4M2ZmMWFiZGRmYWFjNzIyMjZlOTRjMDQ0N2ZhNSJ9fX0="),
    HEAD_TWO("kosmiczna", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDM5MjE3OGNjZDU3OTFlMzU5M2Y4YzJhMTQ0MjM4YWUzZGYzNmFiMjUzOTk5NTQyMDEyYmY0OWE1ZGJhN2FhIn19fQ=="),
    HEAD_FIRST("magiczna", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTVlOGNjOTliYjQyZGRhMmFhZmJmZjQ1Nzc1Njc3NmIyOGM4ZTM0ZWUyNDVjYzU1M2QyNjk0ZTZiMDRiNzIifX19");

    final String url;
    final String name;

    HeadType(String name, String url){
        this.url = url;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public static HeadType findGuildHeadTypeByName(String name){
        return Arrays.stream(HeadType.values()).filter(guildHeadType -> guildHeadType.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}

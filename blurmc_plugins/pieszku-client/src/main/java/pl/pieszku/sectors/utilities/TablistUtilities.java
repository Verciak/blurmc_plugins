package pl.pieszku.sectors.utilities;

import net.minecraft.server.v1_16_R3.MinecraftServer;
import org.pieszku.api.objects.guild.impl.Guild;
import org.pieszku.api.objects.user.User;
import org.pieszku.api.sector.Sector;
import org.pieszku.api.sector.SectorService;
import org.pieszku.api.service.GuildService;
import org.pieszku.api.service.UserService;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.cache.BukkitUser;
import pl.pieszku.sectors.helper.SendType;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.IntStream;

public class TablistUtilities {

    private final UserService userService = BukkitMain.getInstance().getUserService();
    private final GuildService guildService = BukkitMain.getInstance().getGuildService();
    private final SectorService sectorService = BukkitMain.getInstance().getSectorService();


    public void update(Optional<BukkitUser>  bukkitUserOptional){
        bukkitUserOptional.ifPresent(bukkitUser -> {

            List<User> users = new ArrayList<>(this.userService.getUserMap().values());

            synchronized (Collections.unmodifiableList(users)){
                users.sort((o1, o2) -> {
                    Integer pointsUserOne = o1.getPoints();
                    Integer pointsUserTwo = o2.getPoints();
                    return pointsUserTwo.compareTo(pointsUserOne);
                });
            }
            List<Guild> guilds = this.guildService.getGuildList();
            synchronized (Collections.unmodifiableList(guilds)){
                guilds.sort((o1, o2) -> {
                    Integer guildOne = o1.getPoints();
                    Integer guildTwo = o2.getPoints();
                    return guildTwo.compareTo(guildOne);
                });
            }


            bukkitUser.getTablistHelper().apply(tablistHelper -> {

                tablistHelper.setTexture("eyJ0aW1lc3RhbXAiOjE1MTQ1NTk4OTMyNzQsInByb2ZpbGVJZCI6ImIwZDczMmZlMDBmNzQwN2U5ZTdmNzQ2MzAxY2Q5OGNhIiwicHJvZmlsZU5hbWUiOiJPUHBscyIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWE3ZDJkZDg5NTNkYzE5MTJiMWQ3YzhkOWU5Zjc3MjEzZjc5NDQzOTg1YmVlMjM1YmYxOWFlMGIyZWZkIn19fQ==");
                tablistHelper.setSignature("rzUFpcy03vQJyGSUaf2OZG/eJE30o8LScG7eTK4faSbY9/aXGz5chS/xw5LwtSFnp8RPB8CVcMaAh/zBJoyl/iVKuri6i2XmVbD6SYJH/PAKfyzXKskvsU+sBsuYTXBKlxzndVQ+e4AjJdNBBasg0gH09rI6EMvOab4lsLmqFoXnoeOTmYngcXSl2EEdsnuYDDPz9YAJY6CILfNhYhWrs+JYmDdKzGxF1elpHZuN7ryUvrzicV+MCP2DpLbmSywihQxZSDWbleIx8dML6MnKM/E5lKhEfBdfFHIAs5P6Lk6CoyhXTBkwgmVLpRyGlUzdxY3rq5G28hAZ8pqN6VHHekBwXp/E8hTkSklbhbJDbTFFXWHxKeehV2ZcjBGmEEOBq1ySkzBVJAJxZT8b8/bdFsAEdAm1b1Qj7+FsuODebO3OqtCH4QWQI+KnTpmFJKY9CRZx8FyY3ral/Dhj4X4N+Goa7RE3+oQ0dx1EyIBxzP8JuC2cM5HIDC2fSblHSGfbu8rIYqOIitXjILQ0VwcIrTlXWsf4sCmwthdM+OkH4yGFbZfwc34ipyiOym/8fK1gSzua/FkZJl0ubW577Gc6h+2FMXqqM88vW9Cs78vX5d8HxQIOTJGiE05r7YUZ346KCZ+c/39I0x/FzWTu8AnYjfmlmNzsIUzc2yyMnfkbbis=");


                int countPlayers = 0;
                for(Sector sector : this.sectorService.getSectorList()){
                    for (String player : sector.getPlayers()) {
                        countPlayers++;
                    }
                }

                tablistHelper.setHeader(ChatUtilities.colored(Arrays.asList(
                        "&8&m---&b&m---&8&m---&3&m---[ &8 &9&lONE&f&lHARD.PL &3&m ]---&8&m---&b&m---&8&m---",
                        "&aGraczy online aktualnie&8(&a" + countPlayers  +"&f/&21000&8)")));

                List<String> tablistCells = new ArrayList<>();
                tablistCells.add("");
                tablistCells.add("&3&l◄  &f&lTOPOWI GRACZE &3&l►");
                tablistCells.add("         &8(&bPunkty&8)");
                tablistCells.add("");

                IntStream.rangeClosed(1, 15).forEach(i -> {
                    if (users.size() >= i) {
                        User userCompare = users.get(i - 1);
                        if (i == 1) {
                            tablistCells.add("&b&l" + i + "." + (this.sectorService.isOnlinePlayer(userCompare.getNickName()) ? "&a&l•" : "&c&l•") + " " + userCompare.getNickName() + "&8[&3" + userCompare.getPoints() + "&8]"
                                    + (this.guildService.findGuildByMember(userCompare.getNickName()).isPresent() ? "&8[&c" + this.guildService.findGuildByMember(userCompare.getNickName()).get().getName().toUpperCase() + "&8]" : ""));
                        } else {
                            tablistCells.add("&7" + i + "." + (this.sectorService.isOnlinePlayer(userCompare.getNickName()) ? "&a&l•" : "&c&l•") + " " + userCompare.getNickName() + "&8[&b" + userCompare.getPoints() + "&8]"
                                    + (this.guildService.findGuildByMember(userCompare.getNickName()).isPresent() ? "&8[&c" + this.guildService.findGuildByMember(userCompare.getNickName()).get().getName().toUpperCase() + "&8]" : ""));
                        }
                    } else {
                        tablistCells.add("&7" + i + "&f.&fBrak danych.");
                    }
                });
                tablistCells.add("");
                tablistCells.add("");
                tablistCells.add("&3&l◄  &f&lTOPOWE GILDIE &3&l►");
                tablistCells.add("          &8(&bPunkty&8)");
                tablistCells.add("");
                IntStream.rangeClosed(1, 15).forEach(i -> {
                    if (guilds.size() >= i) {
                        Guild guildCompare = guilds.get(i - 1);
                        if (i == 1) {
                            tablistCells.add("&b&l" + i + ".&f" + guildCompare.getName().toUpperCase() + "&8[&3" + guildCompare.getPoints() + "&8]");
                        } else {
                            tablistCells.add("&7" + i + ".&f" + guildCompare.getName().toUpperCase() + "&8[&b" + guildCompare.getPoints() + "&8]");
                        }
                    } else {
                        tablistCells.add("&7" + i + "&f.&fBrak danych.");
                    }
                });


                Guild guild = null;
                if(this.guildService.findGuildByMember(bukkitUser.getNickName()).isPresent()){
                    guild = this.guildService.findGuildByMember(bukkitUser.getNickName()).get();
                }


                Guild finalGuild = guild;
                this.userService.findUserByNickName(bukkitUser.getNickName()).ifPresent(user -> {
                    tablistCells.add("");
                    tablistCells.add("");
                    tablistCells.add("&3&l◄  &f&lINFORMACJE &3&l►");
                    tablistCells.add("        &8(&bO tobie&8)");
                    tablistCells.add("");
                    tablistCells.add("&f&l• &7Twój nick: &b" + user.getNickName());
                    tablistCells.add("&f&l• &7Twoja ranga: " + user.getGroupType().getPrefix());
                    tablistCells.add("&f&l• &7Punkty: &a" + user.getPoints());
                    tablistCells.add("&f&l• &7Zabójstwa: &a" + user.getKills());
                    tablistCells.add("&f&l• &7Zgony: &c" + user.getDeaths());
                    tablistCells.add("");
                    tablistCells.add("&3&l◄  &f&lGILDIA &3&l►");
                    tablistCells.add("    &8(&bTwoja&8)");
                    tablistCells.add("");
                    tablistCells.add("&f&l• &7Nazwa: &a" + ((finalGuild != null ? finalGuild.getName().toUpperCase() : "&bWczytywanie...")));
                    tablistCells.add("&f&l• &7Pełna nazwa: &a" + ((finalGuild != null ? finalGuild.getFullName() : "&bWczytywanie...")));
                    tablistCells.add("&f&l• &7Założyciel: &b" + ((finalGuild != null ? finalGuild.getOwner() : "&bWczytywanie...")));
                    tablistCells.add("&f&l• &7Punkty: &a" + ((finalGuild != null ? finalGuild.getPoints() : "&bWczytywanie...")));
                    tablistCells.add("&f&l• &7Zabójstwa: &a" + ((finalGuild != null ? finalGuild.getKills() : "&bWczytywanie...")));
                    tablistCells.add("&f&l• &7Zgony: &c" + ((finalGuild != null ? finalGuild.getDeaths() : "&bWczytywanie...")));
                    tablistCells.add("");
                    tablistCells.add("");
                    tablistCells.add("&3&l◄  &f&lINFORMACJE &3&l►");
                    tablistCells.add("   &8(&bO serwerze&8)");
                    tablistCells.add("");
                    tablistCells.add("&f&l• &7Sektor: &b" + BukkitMain.getInstance().getSectorName().toUpperCase());
                    tablistCells.add("&f&l• &7TPS: &b" + new DecimalFormat("##.##").format(MinecraftServer.getServer().recentTps[0]));
                    tablistCells.add("");
                    tablistCells.add("&b&l• &3Discord:");
                    tablistCells.add("&f&l•    &fdc.blurmc.pl");
                    tablistCells.add("");
                    tablistCells.add("&b&l• &3Strona internetowa:");
                    tablistCells.add("&f&l•    &fhttps://www.blurmc.pl");
                    tablistCells.add("");
                    tablistCells.add("&b&l• &3Facebook:");
                    tablistCells.add("&f&l•    &ffb.blurmc.pl");
                    tablistCells.add("");
                    tablistCells.add("&b&l• &3Potrzebujesz pomocy?:");
                    tablistCells.add("&f&l•    &f/pomoc");
                    tablistCells.add("");
                    tablistHelper.setCells(ChatUtilities.colored(tablistCells));
                    tablistHelper.setFooter(ChatUtilities.colored(Arrays.asList("&fUkrywaj skina&7,&fnick&7,&frange pod komendą&8(&b/incognito&8)", "&fMożesz edytować ustawienia czatu&8(&b/cc&8)", "&3&l◄ &7Strona: &bhttps://www.blurmc.pl &3&l►", "&3&l◄ &7Discord: &fdc.blurmc.pl &3&l►")));
                });
            }).send(SendType.UPDATE);

        });
    }
}

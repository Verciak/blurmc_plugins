package pl.pieszku.sectors.service;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.pieszku.api.API;
import org.pieszku.api.objects.guild.impl.Guild;
import org.pieszku.api.objects.user.User;
import org.pieszku.api.service.GuildService;
import org.pieszku.api.service.UserService;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.utilities.ChatUtilities;

public class TeamService {


    private final Scoreboard scoreboard;
    private final GuildService guildService = API.getInstance().getGuildService();
    private final UserService userService = BukkitMain.getInstance().getUserService();

    public TeamService() {
        this.scoreboard = new Scoreboard();
    }

    public void initializeNameTag(Player player, User user) {
        ScoreboardTeam scoreboardPlayerTeam = this.scoreboard.getPlayerTeam(player.getName());
        if (scoreboardPlayerTeam == null){
            scoreboardPlayerTeam = this.scoreboard.createTeam(player.getName());
        }
        if (!scoreboardPlayerTeam.getPlayerNameSet().contains(player.getName())) {
            this.scoreboard.addPlayerToTeam(player.getName(), scoreboardPlayerTeam);
            CraftPlayer craftPlayer = (CraftPlayer) player;
            if(user.isIncognito()) scoreboardPlayerTeam.setSuffix(new ChatComponentText(""));
            if(!user.hasProtection())scoreboardPlayerTeam.setSuffix(new ChatComponentText(" " + ChatUtilities.colored("&6&lOCHRONA")));
            scoreboardPlayerTeam.setSuffix(new ChatComponentText(" " + ChatUtilities.colored((user.getGroupType().name().equals("PLAYER") ? "" : user.getGroupType().getPrefix()))));
            PacketPlayOutScoreboardTeam packetPlayOutScoreboardTeam = new PacketPlayOutScoreboardTeam(scoreboardPlayerTeam, 0);
            craftPlayer.getHandle().playerConnection.sendPacket(packetPlayOutScoreboardTeam);

            for (Player playerTo : Bukkit.getOnlinePlayers()) {
                if(playerTo.equals(player))continue;

                ((CraftPlayer) playerTo).getHandle().playerConnection.sendPacket(packetPlayOutScoreboardTeam);
                ScoreboardTeam playerToScoreBoardTeam = this.scoreboard.getPlayerTeam(playerTo.getName());
                if (playerToScoreBoardTeam == null) continue;
                if(user.isIncognito()) scoreboardPlayerTeam.setSuffix(new ChatComponentText(""));
                if(!user.hasProtection())scoreboardPlayerTeam.setSuffix(new ChatComponentText(" " + ChatUtilities.colored("&6&lOCHRONA")));
                scoreboardPlayerTeam.setSuffix(new ChatComponentText(" " + ChatUtilities.colored((user.getGroupType().name().equals("GRACZ") ? "" : user.getGroupType().getPrefix()))));
                craftPlayer.getHandle().playerConnection.sendPacket(new PacketPlayOutScoreboardTeam(playerToScoreBoardTeam, 0));
            }
        }
    }
    public void updateBoard(Player player, Guild team, User user) {
        ScoreboardTeam scoreboardTeam = this.scoreboard.getPlayerTeam(player.getName());
        if(scoreboardTeam == null)return;
        scoreboardTeam.setSuffix(new ChatComponentText(" " + ChatUtilities.colored((user.getGroupType().name().equals("PLAYER") ? "" : user.getGroupType().getPrefix()))));
        if(user.isIncognito()) scoreboardTeam.setSuffix(new ChatComponentText(""));
        if(!user.hasProtection())scoreboardTeam.setSuffix(new ChatComponentText(" " + ChatUtilities.colored("&6&lOCHRONA")));

        for (Player players : Bukkit.getOnlinePlayers()) {
            Guild guild = this.guildService.findGuildByMemberGet(players.getName());

            if(team != null && team.equals(guild)){
                scoreboardTeam.setPrefix(new ChatComponentText(ChatUtilities.colored("&2[&a" + team.getName().toUpperCase() + "&2]&a")));
                if(user.isIncognito()) scoreboardTeam.setSuffix(new ChatComponentText(ChatUtilities.colored(" &a◉")));
            }else if(team != null && !team.equals(guild)){
                scoreboardTeam.setPrefix(new ChatComponentText(ChatUtilities.colored("&4[&c" + team.getName().toUpperCase() + "&4]&c")));
                if(user.isIncognito()){
                    scoreboardTeam.setPrefix(new ChatComponentText(ChatUtilities.colored("&4[&c?&4] &c&k")));
                    scoreboardTeam.setSuffix(new ChatComponentText(ChatUtilities.colored(" &c◉")));
                }
            }
            if(team == null){
                scoreboardTeam.setPrefix(new ChatComponentText(ChatUtilities.colored("&7")));
            }
            send(scoreboardTeam, players);
        }
    }
    public void removeNameTag(final Player player) {
        ScoreboardTeam scoreboardPlayerTeam = this.scoreboard.getPlayerTeam(player.getName());
        if (scoreboardPlayerTeam == null) return;

        this.scoreboard.removePlayerFromTeam(player.getName(), scoreboardPlayerTeam);
        scoreboardPlayerTeam.setNameTagVisibility(ScoreboardTeamBase.EnumNameTagVisibility.NEVER);

        CraftPlayer craftPlayer = (CraftPlayer) player;
        final PacketPlayOutScoreboardTeam packetPlayOutScoreboardTeam = new PacketPlayOutScoreboardTeam(scoreboardPlayerTeam, 1);
        craftPlayer.getHandle().playerConnection.sendPacket(packetPlayOutScoreboardTeam);
        for (Player playerTo : Bukkit.getOnlinePlayers()) {
            if (playerTo.equals(player) || playerTo.hasMetadata("NPC")) continue;
            ((CraftPlayer) playerTo).getHandle().playerConnection.sendPacket(packetPlayOutScoreboardTeam);
            ScoreboardTeam team = this.scoreboard.getTeam(playerTo.getName());
            if (team == null) continue;
            craftPlayer.getHandle().playerConnection.sendPacket(new PacketPlayOutScoreboardTeam(team, 1));
        }
        this.scoreboard.removeTeam(scoreboardPlayerTeam);
    }
    public void send(ScoreboardTeam scoreboardTeam, Player player){
        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam(scoreboardTeam, 2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}

package pl.pieszku.sectors.utilities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.pieszku.api.service.GuildService;
import org.pieszku.api.service.UserService;
import pl.pieszku.sectors.BukkitMain;

import java.util.List;

public class ScoreboardUtilities {

    private final UserService userService = BukkitMain.getInstance().getUserService();
    private final GuildService guildService = BukkitMain.getInstance().getGuildService();
    private Scoreboard scoreboard;

    public void send(Player player, List<String> scoreboardTexts) {
        this.scoreboard  = Bukkit.getScoreboardManager().getNewScoreboard();

        this.userService.findUserByNickName(player.getName()).ifPresent(user -> {

            if(this.scoreboard.getObjective(player.getName()) == null) {
                this.scoreboard.registerNewObjective(player.getName(), player.getName());
            }
            Objective objective = this.scoreboard.getObjective(player.getName());

            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName(ChatUtilities.colored("&8&m-( &8 &9&lONE&f&lHARD &8&m )-"));

            for (int i = 0; i < scoreboardTexts.size(); i++) {
                objective.getScore(ChatUtilities.colored(scoreboardTexts.get(i))).setScore(-i);
            }
            player.setScoreboard(this.scoreboard);
        });
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }
}

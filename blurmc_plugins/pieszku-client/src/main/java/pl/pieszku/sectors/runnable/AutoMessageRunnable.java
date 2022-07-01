package pl.pieszku.sectors.runnable;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.utilities.ChatUtilities;
import pl.pieszku.sectors.utilities.ScoreboardUtilities;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AutoMessageRunnable implements Runnable {

    private final List<String> autoMessageList = Arrays.asList(
            "&8&l:: &9&lONE&f&lHARD &8&l:: &f&lJesteś nowy nie masz pojęcia jak zacząć? &b&l/pomoc",
            "&8&l:: &9&lONE&f&lHARD &8&l:: &f&lZa dużo osób na spawn? &b&l/ch",
            "&8&l:: &9&lONE&f&lHARD &8&l:: &f&lNasza strona&8&l: &b&lhttps://www.blurmc.pl",
            "&8&l:: &9&lONE&f&lHARD &8&l:: &f&lNa serwerze możesz zarobić serwerowe monety&8&l: &b&l/sklep",
            "&8&l:: &9&lONE&f&lHARD &8&l:: &f&lNa serwerze posługujemy się&8&l: &b&l/craftingi",
            "&8&l:: &9&lONE&f&lHARD &8&l:: &f&lPotrzebujesz pomocy? &b&l/helpop",
            "&8&l:: &9&lONE&f&lHARD &8&l:: &f&lZapraszamy na naszego discorda&8&l: &b&l/discord");

    private final List<String> scoreList = Arrays.asList(
            "    ",
            "   &7Nick: &f{PLAYER}",
            "   &7Ranga: {RANK}",
            "   &7Ping: &b{PING}&fms",
            "  ",
            "   &7Jesteś na sektorze",
            "        &8(&b" + BukkitMain.getInstance().getSectorName() + "&8)",
            "   &7Online&8(&a{SECTOR_PLAYERS}&8, &fgraczy&8)",
            "    &7TPS&8(&a*20&f, &a*20&f, &a*20&8)",
            "     ",
            "   &7Sprzedaz przedmiotów?",
            "          &b/bazar",
            "         ",
            "&f/ch &8- &7Aby zmienić sektor");


    private final ScoreboardUtilities scoreboardUtilities = new ScoreboardUtilities();
    private int index = 0;

    @Override
    public void run() {


        if (this.index >= this.autoMessageList.size()) {
            this.index = 0;
        }
        Bukkit.broadcastMessage(ChatUtilities.colored(this.autoMessageList.get(this.index)));
        this.index++;

        for (Player player : Bukkit.getOnlinePlayers()) {


            this.scoreboardUtilities.send(player, this.scoreList.stream().map(s -> {
                s = s.replace("{PLAYER}", player.getName());
                s = s.replace("{RANK}", BukkitMain.getInstance().getUserService().getOrCreate(player.getName()).getGroupType().getPrefix());
                s = s.replace("{SECTOR_PLAYERS}", String.valueOf(BukkitMain.getInstance().getCurrentSector().get().getPlayers().size()));
                s = s.replace("{PING}", String.valueOf(((CraftPlayer) player).getHandle().ping));
                return s;
            }).collect(Collectors.toList()));
        }
    }

    public void start() {
        Bukkit.getScheduler().runTaskTimer(BukkitMain.getInstance(), this, 1L, 500L);
    }
}

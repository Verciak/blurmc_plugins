package pl.pieszku.sectors.commands.admin;

import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pieszku.api.type.GroupType;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.cache.BukkitUser;
import pl.pieszku.sectors.impl.Command;
import pl.pieszku.sectors.impl.CommandInfo;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.util.Arrays;

@CommandInfo(name = "gamemode", aliases = {"gm"}, permission = GroupType.ADMIN)
public class GameModeCommand extends Command {


    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length < 1){
            sender.sendMessage(ChatUtilities.colored("&7Poprawne użycie: &b/gm [tryb] [nick]"));
            return;
        }
        GameMode mode = Arrays.stream(GameMode.values()).filter(mode1 -> mode1.name().equals(args[0]) || this.getInt(args[0], -1) == mode1.getValue()).findAny().orElse(null);
        if(mode == null) {
           sender.sendMessage(ChatUtilities.colored("&4Błąd: &cPodany tryb gry nie istnieje!"));
            return;
        }
        BukkitUser bukkitUser = null;
        if(sender instanceof Player)
            bukkitUser = BukkitMain.getInstance().getBukkitCache().findBukkitUserByNickName(sender.getName()).get();
        if(args.length == 2) bukkitUser = BukkitMain.getInstance().getBukkitCache().findBukkitUserByNickName(args[1]).get();
        if(bukkitUser == null){
            sender.sendMessage(ChatUtilities.colored("&7Podany gracz nie zostal okreslony, albo nie jest aktywny."));
            return;
        }
        bukkitUser.getPlayer().setGameMode(mode);
        if(bukkitUser.getNickName().equals(sender.getName()))
            bukkitUser.getPlayer().sendMessage(ChatUtilities.colored("&7Twoj tryb gry zostal zmieniony. Nowy posiadany tryb &8-> &3" + mode.name()));
        else {
            bukkitUser.getPlayer().sendMessage(ChatUtilities.colored("&7Twoj tryb gry zostal zmieniony przez &b{ADMIN}&7. Nowy posiadany tryb &8-> &3{MODE}").replace("{MODE}", mode.name()).replace("{ADMIN}", sender.getName()));
           sender.sendMessage(ChatUtilities.colored("&7Tryb gry gracza &b{PLAYER} &7zostal zmieniony. Nowy posiadany tryb &8-> &3{MODE}").replace("{MODE}", mode.name()).replace("{PLAYER}", bukkitUser.getNickName()));
        }
    }
    public Integer getInt(String value, int ifNotNumber) {
        if (value.toLowerCase().contains("infinity") || value.toLowerCase().contains("nan")) {
            return 0;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return ifNotNumber;
        }
    }
}

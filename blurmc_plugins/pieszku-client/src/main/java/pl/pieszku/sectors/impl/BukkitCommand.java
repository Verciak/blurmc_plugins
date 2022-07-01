package pl.pieszku.sectors.impl;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.pieszku.api.type.GroupType;
import org.pieszku.api.service.UserService;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BukkitCommand extends Command {

    private final pl.pieszku.sectors.impl.Command command;
    private final UserService userService = BukkitMain.getInstance().getUserService();

    public BukkitCommand(pl.pieszku.sectors.impl.Command command){
        super(command.getCommandInfo().name());
        this.command = command;

        CommandInfo commandInfo = command.getCommandInfo();
        setName(commandInfo.name());
        setAliases(Arrays.asList(commandInfo.aliases()));
        setDescription(commandInfo.description());
        setUsage(commandInfo.usage());

    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            this.userService.findUserByNickName(player.getName()).ifPresent(user -> {
                if(!GroupType.hasPermission(user, this.command.getCommandInfo().permission())){
                    player.sendMessage(ChatUtilities.colored("&4Błąd: &cDostęp do tej komendy od rangi: " + this.command.getCommandInfo().permission().getPrefix()));
                    return;
                }
                this.command.execute(commandSender, strings);
            });
            return false;
        }
        this.command.execute(commandSender, strings);
        return false;
    }

    @NotNull
    @Override
    public List<String> tabComplete(CommandSender sender,  String alias,  String[] args) throws IllegalArgumentException {
        if(this.command.tabComplete(sender, alias, args) == null)return new ArrayList<>();
        return this.command.tabComplete(sender, alias, args);
    }
}

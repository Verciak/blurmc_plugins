package pl.pieszku.sectors.commands.guild;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.pieszku.sectors.inventory.guild.GuildHelpInventory;

import java.util.*;

public class GuildCommand extends GuildSubCommand {


    private final Set<GuildSubCommand> subCommands = new HashSet<>();
    private final GuildHelpInventory guildHelpInventory = new GuildHelpInventory();

    public GuildCommand() {
        super("gildia", "", "", "", "g", "gildie", "guild");
        this.subCommands.add(new CreateGuildCommand());
        this.subCommands.add(new HomeGuildCommand());
        this.subCommands.add(new SetHomeGuildCommand());
        this.subCommands.add(new InviteGuildCommand());
        this.subCommands.add(new LeaveGuildCommand());
        this.subCommands.add(new JoinGuildCommand());
        this.subCommands.add(new FriendlyFireGuildCommand());
        this.subCommands.add(new PanelGuildCommand());
        this.subCommands.add(new ItemsGuildCommand());
        this.subCommands.add(new DeleteGuildCommand());
        this.subCommands.add(new OwnerGuildCommand());
        this.subCommands.add(new LeaderGuildCommand());
        this.subCommands.add(new MasterGuildCommand());
        this.subCommands.add(new KickMemberGuildCommand());
        this.subCommands.add(new InfoGuildCommand());
    }

    @Override
    public boolean onCommand(Player player, String[] args) {


        if(args.length == 0){
            this.guildHelpInventory.show(player);
            return false;
        }

        String name = args[0];

        GuildSubCommand subCommand = this.getSubCommand(name);
        if(subCommand == null){
            this.guildHelpInventory.show(player);
            return false;
        }
        return subCommand.onCommand(player, Arrays.copyOfRange(args, 1, args.length));
    }
    private GuildSubCommand getSubCommand(String sub) {
        for (GuildSubCommand sc : subCommands)
            if (sc.getName().equalsIgnoreCase(sub) || sc.getAliases().contains(sub))
                return sc;
        return null;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return new ArrayList<>();
    }
}

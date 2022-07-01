package pl.pieszku.sectors.commands.guild;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.pieszku.api.API;
import org.pieszku.api.objects.guild.impl.Guild;
import org.pieszku.api.service.GuildService;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InfoGuildCommand extends GuildSubCommand{


    private final GuildService guildService = API.getInstance().getGuildService();

    public InfoGuildCommand() {
        super("info", "", "/g info <tag>", "");
    }

    @Override
    public boolean onCommand(Player player, String[] args) {


        if(args.length < 1){
            player.sendMessage(ChatUtilities.colored("&b&lGILDIA&8: &fPoprawne uzycie: &b" + this.getUsage()));
            return false;
        }

        this.guildService.findGuildByName(args[0]).ifPresent(guild -> {
            player.sendMessage(ChatUtilities.colored("   &3&lZARZAD"));
            player.sendMessage(ChatUtilities.colored("&fZalozyciel: &b" + guild.getOwner()));
            player.sendMessage(ChatUtilities.colored("&fZastepca: &b" + (guild.getLeader().equalsIgnoreCase("null") ? "brak" : guild.getLeader())));
            player.sendMessage(ChatUtilities.colored("&fMistrz: &b" + (guild.getMaster().equalsIgnoreCase("null") ? "brak" : guild.getMaster())));
            player.sendMessage(ChatUtilities.colored(""));
            player.sendMessage(ChatUtilities.colored("  &3&lSTATYSTYKI"));
            player.sendMessage(ChatUtilities.colored("&fPunkty: &a" + guild.getPoints()));
            player.sendMessage(ChatUtilities.colored("&fZabojstwa: &a" + guild.getKills()));
            player.sendMessage(ChatUtilities.colored("&fZgony: &c" + guild.getDeaths()));
            player.sendMessage(ChatUtilities.colored(""));
            player.sendMessage(ChatUtilities.colored("  &3&lINFORMACJE"));
            player.sendMessage(ChatUtilities.colored("&fLokacja: &aX&8:&2" + guild.getLocationSerializer().getX() + "&f, &aZ&8:&2" + guild.getLocationSerializer().getZ()));
            player.sendMessage(ChatUtilities.colored("&fRozmiar gildii&8(&a" + guild.getLocationSerializer().getSize() + "&7x&a" + guild.getLocationSerializer().getSize() + "&8)"));
            player.sendMessage(ChatUtilities.colored("&fOstatnia synchronizacja: &a" + guild.getLatestSynchronizeData().toLocaleString() + "&8(&fInterakcja&8)"));
            player.sendMessage(ChatUtilities.colored("&fData zalozenia: &a" + guild.getCreateDate().toLocaleString()));
            player.sendMessage(ChatUtilities.colored("&fCzlonkowie&8(&a" + guild.getMembers().toString().replace("[", "").replace("]", "")
                    + "&f, &b" + guild.getMembers().size() + " &flacznie&8)"));
            player.sendMessage(ChatUtilities.colored("&fSojusze&8(" + ((guild.getAllys().size() == 0) ? "&cbrak&8)" : "&a" + guild.getAllys().toString().replace("[", "").replace("]", "")
                    + "&f, &b" + guild.getAllys().size() + " &flacznie&8)")));
        });
        return false;
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        List<String> complete = new ArrayList<>();

        if(args.length == 1){
            for(Guild guild : this.guildService.getGuildList()){
                if(guild.getName().toLowerCase().contains(args[0].toLowerCase())){
                    complete.add(args[0]);
                }
            }
        }
        Collections.sort(complete);
        return complete;
    }
}

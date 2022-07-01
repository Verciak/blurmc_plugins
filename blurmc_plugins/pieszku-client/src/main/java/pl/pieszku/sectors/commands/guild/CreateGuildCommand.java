package pl.pieszku.sectors.commands.guild;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pieszku.api.objects.guild.impl.Guild;
import org.pieszku.api.redis.packet.client.SendMessagePacket;
import org.pieszku.api.redis.packet.type.MessageType;
import org.pieszku.api.redis.packet.type.ReceiverType;
import org.pieszku.api.serializer.LocationSerializer;
import org.pieszku.api.service.GuildService;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.utilities.ChatUtilities;
import pl.pieszku.sectors.utilities.GuildSchematic;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CreateGuildCommand extends GuildSubCommand{


    private final GuildService guildService = BukkitMain.getInstance().getGuildService();
    private final Pattern pattern = Pattern.compile("[a-zA-Z0-9]+");

    public CreateGuildCommand() {
        super("zaloz", "", "/zaloz <tag> <nazwa>", "", "create");
    }

    @Override
    public boolean onCommand(Player player, String[] args) {


        if(args.length < 2){
            player.sendMessage(ChatUtilities.colored("&b&lGILDIA&8: &fPoprawne uzycie: &b" + this.getUsage()));
            return false;
        }
        if(this.guildService.findGuildByMember(player.getName()).isPresent()){
            player.sendMessage(ChatUtilities.colored("&4Błąd: &cPosiadasz już gildię!"));
            return false;
        }
        String name = args[0];
        String fullName = args[1];

        if(name.length() < 3 || name.length() > 5 || fullName.length() < 8 || fullName.length() > 24){
            player.sendMessage(ChatUtilities.colored("&4Błąd: &cTag gildi może mieć 3-4 znaki a nazwa 8-24 znaków"));
            return false;
        }
        if(this.guildService.findGuildByName(name).isPresent()){
            player.sendMessage(ChatUtilities.colored("&4Błąd: &cNazwa o podanym tagu już istnieje!"));
            return false;
        }

        if(!pattern.matcher(name).find()){
            player.sendMessage(ChatUtilities.colored("&4Błąd: &cNie możesz uzyć takiej nazwy gildi ponieważ posiada niedozwolone znaki!"));
            return false;
        }

        LocationSerializer locationSerializer =  new LocationSerializer(player.getWorld().getName(), player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ(), 16);
        Guild guild = new Guild(name, fullName, player.getName(), locationSerializer);
        Location location = new Location(Bukkit.getWorld("world"), guild.getLocationSerializer().getX(), 38, guild.getLocationSerializer().getZ());
        player.teleport(location);
        GuildSchematic.paste(guild);


        new SendMessagePacket(
                "&3&lGILDIA &8>> &fGracz: &b" + player.getName() + " &fzałożył gildie\n" +
                        "&8>> &8[&3" + guild.getName().toUpperCase() + "&8] &3" + guild.getFullName(),
                ReceiverType.ALL, MessageType.CHAT).sendToAllSectors(BukkitMain.getInstance().getSectorService());

        player.sendTitle(ChatUtilities.colored("&3&lGILDIA"), ChatUtilities.colored("&aGratulacje założyłeś gildię!"));
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 10, 10);
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return new ArrayList<>();
    }
}

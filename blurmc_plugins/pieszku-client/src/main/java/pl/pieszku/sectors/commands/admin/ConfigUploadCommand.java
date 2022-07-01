package pl.pieszku.sectors.commands.admin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pieszku.api.redis.packet.client.load.ConfigurationReloadClientPacket;
import org.pieszku.api.type.GroupType;
import pl.pieszku.sectors.impl.Command;
import pl.pieszku.sectors.impl.CommandInfo;
import pl.pieszku.sectors.utilities.ChatUtilities;

@CommandInfo(name = "configreload", permission = GroupType.DEVELOPER)
public class ConfigUploadCommand extends Command {


    @Override
    public void execute(CommandSender commandSender, String[] args) {
        Player player = (Player) commandSender;
        new ConfigurationReloadClientPacket().sendToChannel("MASTER");
        player.sendMessage(ChatUtilities.colored("&d&lCONFIGI &fPomyślnie przeladowano wszystkie sektory z configami"));
    }
}

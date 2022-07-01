package pl.pieszku.sectors.commands.admin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pieszku.api.redis.packet.client.ChatSynchronizePacket;
import org.pieszku.api.redis.packet.client.SendMessagePacket;
import org.pieszku.api.redis.packet.type.MessageType;
import org.pieszku.api.redis.packet.type.ReceiverType;
import org.pieszku.api.sector.SectorService;
import org.pieszku.api.type.GroupType;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.impl.Command;
import pl.pieszku.sectors.impl.CommandInfo;
import pl.pieszku.sectors.utilities.ChatUtilities;

@CommandInfo(name = "chat", permission = GroupType.MODERATOR)
public class ChatCommand extends Command {


    private final SectorService sectorService = BukkitMain.getInstance().getSectorService();

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        Player player = (Player) commandSender;

        if(args.length < 1){
            player.sendMessage(ChatUtilities.colored("&7Poprawne użycie: &b/chat <on/off/clear>"));
            return;
        }

        switch (args[0].toLowerCase()){
            case "on":{
                new ChatSynchronizePacket(true).sendToAllSectors(this.sectorService);
                new SendMessagePacket("&b&lCHAT &3->> &fZostał &awłaczony &fprzez admininstratora: &b" + player.getName(), ReceiverType.ALL, MessageType.CHAT).sendToAllSectors(this.sectorService);
                break;
            }
            case "off":{
                new ChatSynchronizePacket(false).sendToAllSectors(this.sectorService);
                new SendMessagePacket("&b&lCHAT &3->> &fZostał &cwyłaczony &fprzez admininstratora: &b" + player.getName(), ReceiverType.ALL, MessageType.CHAT).sendToAllSectors(this.sectorService);
                break;
            }
            case "clear":{
                for(int i = 0; i < 20; i++){
                    new SendMessagePacket(" ", ReceiverType.ALL, MessageType.CHAT).sendToAllSectors(this.sectorService);
                }
                new SendMessagePacket("&b&lCHAT &3->> &fZostał &awyczyszczony &fprzez admininstratora: &b" + player.getName(), ReceiverType.ALL, MessageType.CHAT).sendToAllSectors(this.sectorService);
                break;
            }
        }
    }
}

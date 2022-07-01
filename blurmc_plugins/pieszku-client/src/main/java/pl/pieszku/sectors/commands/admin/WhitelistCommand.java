package pl.pieszku.sectors.commands.admin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pieszku.api.API;
import org.pieszku.api.proxy.global.WhitelistServerService;
import org.pieszku.api.redis.packet.client.SendMessagePacket;
import org.pieszku.api.redis.packet.type.MessageType;
import org.pieszku.api.redis.packet.type.ReceiverType;
import org.pieszku.api.sector.SectorService;
import org.pieszku.api.type.GroupType;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.impl.Command;
import pl.pieszku.sectors.impl.CommandInfo;
import pl.pieszku.sectors.utilities.ChatUtilities;

@CommandInfo(name = "whitelist", permission = GroupType.DEVELOPER)
public class WhitelistCommand extends Command {


    private final WhitelistServerService whitelistServerService = API.getInstance().getWhitelistServerService();
    private final SectorService sectorService = BukkitMain.getInstance().getSectorService();

    @Override//elo
    public void execute(CommandSender commandSender, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (args.length < 2) {
                commandSender.sendMessage(ChatUtilities.colored("&7Typy&8: &eGLOBAL, SECTOR, SECTORSALL"));
                commandSender.sendMessage(ChatUtilities.colored("&6/whitelist <typ> add <sector> <nick> &8- &7Dodanie gracza do whitelist"));
                commandSender.sendMessage(ChatUtilities.colored("&6/whitelist <typ> remove <sector> <nick> &8- &7Usunięcie gracz z whitelist"));
                commandSender.sendMessage(ChatUtilities.colored("&6/whitelist <typ> off &8- &7Wyłączenie whitelisty"));
                commandSender.sendMessage(ChatUtilities.colored("&6/whitelist <typ> on &8- &7Włączenie whitelisty"));
                return;
            }
            switch (args[0].toLowerCase()) {
                case "global": {

                    switch (args[1].toLowerCase()) {
                        case "on": {
                            for (int i = 0; i < whitelistServerService.getWhitelistServersList().size(); i++) {
                                whitelistServerService.getWhitelistServersList().get(i).setWhitelist(true);
                            }
                            new SendMessagePacket("&6&lWHITELIST &8&l:: &fWhitelista zostałą &awłączona &fna całym serwerze przez: &a" + player.getName(), ReceiverType.ALL, MessageType.CHAT).sendToAllSectors(BukkitMain.getInstance().getSectorService());
                            break;
                        }
                        case "off": {
                            for (int i = 0; i < whitelistServerService.getWhitelistServersList().size(); i++) {
                                whitelistServerService.getWhitelistServersList().get(i).setWhitelist(false);
                            }
                            new SendMessagePacket("&6&lWHITELIST &8&l:: &fWhitelista zostałą &cwyłączona &fna całym serwerze przez: &a" + player.getName(), ReceiverType.ALL, MessageType.CHAT).sendToAllSectors(BukkitMain.getInstance().getSectorService());
                            break;
                        }
                        case "add": {
                            for (int i = 0; i < whitelistServerService.getWhitelistServersList().size(); i++) {
                                whitelistServerService.getWhitelistServersList().get(i).addWhitelist(args[2]);
                            }
                            player.sendMessage(ChatUtilities.colored("&6&lWHITELIST &8&l:: &fDodałeś gracza: &e" + args[2] + " &fdo whitelisty na całym serwerze!"));
                            break;
                        }
                        case "remove": {
                            for (int i = 0; i < whitelistServerService.getWhitelistServersList().size(); i++) {
                                whitelistServerService.getWhitelistServersList().get(i).removeWhitelist(args[2]);
                            }
                            player.sendMessage(ChatUtilities.colored("&6&lWHITELIST &8&l:: &fWyrzuciłeś gracza: &e" + args[2] + " &fz whitelisty na całym serwerze!"));
                            break;
                        }
                    }
                    break;
                }
                case "sector": {
                    if (!this.sectorService.findSectorByName(args[2]).isPresent()) {
                        player.sendMessage(ChatUtilities.colored("&4Błąd: &cPodany sektor nie istnieje!"));
                        return;
                    }
                    switch (args[1].toLowerCase()) {
                        case "on": {
                            this.whitelistServerService.findWhitelistByServerName(args[2]).ifPresent(whitelistServer -> {
                                whitelistServer.setWhitelist(true);
                            });
                            new SendMessagePacket("&6&lWHITELIST &8&l:: &fWhitelista zostałą &awłączona &fna sektorze: &e" + args[2] + " &fprzez: &a" + player.getName(), ReceiverType.ALL, MessageType.CHAT).sendToAllSectors(BukkitMain.getInstance().getSectorService());
                            break;
                        }
                        case "off": {
                            this.whitelistServerService.findWhitelistByServerName(args[2]).ifPresent(whitelistServer -> {
                                whitelistServer.setWhitelist(false);
                            });
                            new SendMessagePacket("&6&lWHITELIST &8&l:: &fWhitelista zostałą &awłączona &fna sektorze: &e" + args[2] + " &fprzez: &a" + player.getName(), ReceiverType.ALL, MessageType.CHAT).sendToAllSectors(BukkitMain.getInstance().getSectorService());
                            break;
                        }
                        case "add": {
                            this.whitelistServerService.findWhitelistByServerName(args[2]).ifPresent(whitelistServer -> {
                                whitelistServer.addWhitelist(args[3]);
                            });
                            player.sendMessage(ChatUtilities.colored("&6&lWHITELIST &8&l:: &fDodałeś gracza: &e" + args[3] + " &fdo whitelisty na sektorze: &e" + args[2]));
                            break;
                        }
                        case "remove": {
                            this.whitelistServerService.findWhitelistByServerName(args[2]).ifPresent(whitelistServer -> {
                                whitelistServer.removeWhitelist(args[3]);
                            });
                            player.sendMessage(ChatUtilities.colored("&6&lWHITELIST &8&l:: &fWyrzuciłeś gracza: &e" + args[3] + " &fz whitelisty na sektorze: &e" + args[2]));
                            break;
                        }
                    }
                    break;
                }
                case "sectorsall": {
                    switch (args[1].toLowerCase()) {
                        case "on": {
                            for (int i = 0; i < whitelistServerService.getWhitelistServersList().size(); i++) {
                                if (!whitelistServerService.getWhitelistServersList().get(i).isSector()) continue;
                                whitelistServerService.getWhitelistServersList().get(i).setWhitelist(true);
                            }
                            new SendMessagePacket("&6&lWHITELIST &8&l:: &fWhitelista zostałą &awłączona &fna całym serwerze przez: &a" + player.getName(), ReceiverType.ALL, MessageType.CHAT).sendToAllSectors(BukkitMain.getInstance().getSectorService());
                            break;
                        }
                        case "off": {
                            for (int i = 0; i < whitelistServerService.getWhitelistServersList().size(); i++) {
                                if (!whitelistServerService.getWhitelistServersList().get(i).isSector()) continue;
                                whitelistServerService.getWhitelistServersList().get(i).setWhitelist(false);
                            }
                            new SendMessagePacket("&6&lWHITELIST &8&l:: &fWhitelista zostałą &cwyłączona &fna całym serwerze przez: &a" + player.getName(), ReceiverType.ALL, MessageType.CHAT).sendToAllSectors(BukkitMain.getInstance().getSectorService());
                            break;
                        }
                        case "add": {
                            for (int i = 0; i < whitelistServerService.getWhitelistServersList().size(); i++) {
                                if (!whitelistServerService.getWhitelistServersList().get(i).isSector()) continue;
                                whitelistServerService.getWhitelistServersList().get(i).addWhitelist(args[2]);
                            }
                            player.sendMessage(ChatUtilities.colored("&6&lWHITELIST &8&l:: &fDodałeś gracza: &e" + args[2] + " &fdo whitelisty na wszystkich sektorach!"));
                            break;
                        }
                        case "remove": {
                            for (int i = 0; i < whitelistServerService.getWhitelistServersList().size(); i++) {
                                if (!whitelistServerService.getWhitelistServersList().get(i).isSector()) continue;
                                whitelistServerService.getWhitelistServersList().get(i).removeWhitelist(args[2]);
                            }
                            player.sendMessage(ChatUtilities.colored("&6&lWHITELIST &8&l:: &fWyrzuciłeś gracza: &e" + args[2] + " &fz whitelisty na wszystkich sektorach!"));
                            break;
                        }
                    }

                    break;
                }
            }
            return;
        }
        if (args.length < 2) {
            commandSender.sendMessage(ChatUtilities.colored("&7Typy&8: &eGLOBAL, SECTOR, SECTORS"));
            commandSender.sendMessage(ChatUtilities.colored("&6/whitelist <typ> add <sector> <nick> &8- &7Dodanie gracza do whitelist"));
            commandSender.sendMessage(ChatUtilities.colored("&6/whitelist <typ> remove <sector> <nick> &8- &7Usunięcie gracz z whitelist"));
            commandSender.sendMessage(ChatUtilities.colored("&6/whitelist <typ> off &8- &7Wyłączenie whitelisty"));
            commandSender.sendMessage(ChatUtilities.colored("&6/whitelist <typ> on &8- &7Włączenie whitelisty"));
            return;
        }
        switch (args[0].toLowerCase()) {
            case "global": {

                switch (args[1].toLowerCase()) {
                    case "on": {
                        for (int i = 0; i < whitelistServerService.getWhitelistServersList().size(); i++) {
                            whitelistServerService.getWhitelistServersList().get(i).setWhitelist(true);
                        }
                        new SendMessagePacket("&6&lWHITELIST &8&l:: &fWhitelista zostałą &awłączona &fna całym serwerze przez: &a" + commandSender.getName(), ReceiverType.ALL, MessageType.CHAT).sendToAllSectors(BukkitMain.getInstance().getSectorService());
                        break;
                    }
                    case "off": {
                        for (int i = 0; i < whitelistServerService.getWhitelistServersList().size(); i++) {
                            whitelistServerService.getWhitelistServersList().get(i).setWhitelist(false);
                        }
                        new SendMessagePacket("&6&lWHITELIST &8&l:: &fWhitelista zostałą &cwyłączona &fna całym serwerze przez: &a" + commandSender.getName(), ReceiverType.ALL, MessageType.CHAT).sendToAllSectors(BukkitMain.getInstance().getSectorService());
                        break;
                    }
                    case "add": {
                        for (int i = 0; i < whitelistServerService.getWhitelistServersList().size(); i++) {
                            whitelistServerService.getWhitelistServersList().get(i).addWhitelist(args[2]);
                        }
                        commandSender.sendMessage(ChatUtilities.colored("&6&lWHITELIST &8&l:: &fDodałeś gracza: &e" + args[2] + " &fdo whitelisty na całym serwerze!"));
                        break;
                    }
                    case "remove": {
                        for (int i = 0; i < whitelistServerService.getWhitelistServersList().size(); i++) {
                            whitelistServerService.getWhitelistServersList().get(i).removeWhitelist(args[2]);
                        }
                        commandSender.sendMessage(ChatUtilities.colored("&6&lWHITELIST &8&l:: &fWyrzuciłeś gracza: &e" + args[2] + " &fz whitelisty na całym serwerze!"));
                        break;
                    }
                }
                break;
            }
            case "sector": {
                if (!this.sectorService.findSectorByName(args[2]).isPresent()) {
                    commandSender.sendMessage(ChatUtilities.colored("&4Błąd: &cPodany sektor nie istnieje!"));
                    return;
                }
                switch (args[1].toLowerCase()) {
                    case "on": {
                        this.whitelistServerService.findWhitelistByServerName(args[2]).ifPresent(whitelistServer -> {
                            whitelistServer.setWhitelist(true);
                        });
                        new SendMessagePacket("&6&lWHITELIST &8&l:: &fWhitelista zostałą &awłączona &fna sektorze: &e" + args[2] + " &fprzez: &a" + commandSender.getName(), ReceiverType.ALL, MessageType.CHAT).sendToAllSectors(BukkitMain.getInstance().getSectorService());
                        break;
                    }
                    case "off": {
                        this.whitelistServerService.findWhitelistByServerName(args[2]).ifPresent(whitelistServer -> {
                            whitelistServer.setWhitelist(false);
                        });
                        new SendMessagePacket("&6&lWHITELIST &8&l:: &fWhitelista zostałą &awłączona &fna sektorze: &e" + args[2] + " &fprzez: &a" + commandSender.getName(), ReceiverType.ALL, MessageType.CHAT).sendToAllSectors(BukkitMain.getInstance().getSectorService());
                        break;
                    }
                    case "add": {
                        this.whitelistServerService.findWhitelistByServerName(args[2]).ifPresent(whitelistServer -> {
                            whitelistServer.addWhitelist(args[3]);
                        });
                        commandSender.sendMessage(ChatUtilities.colored("&6&lWHITELIST &8&l:: &fDodałeś gracza: &e" + args[3] + " &fdo whitelisty na sektorze: &e" + args[2]));
                        break;
                    }
                    case "remove": {
                        this.whitelistServerService.findWhitelistByServerName(args[2]).ifPresent(whitelistServer -> {
                            whitelistServer.removeWhitelist(args[3]);
                        });
                        commandSender.sendMessage(ChatUtilities.colored("&6&lWHITELIST &8&l:: &fWyrzuciłeś gracza: &e" + args[3] + " &fz whitelisty na sektorze: &e" + args[2]));
                        break;
                    }
                }
                break;
            }
            case "sectorsall": {
                switch (args[1].toLowerCase()) {
                    case "on": {
                        for (int i = 0; i < whitelistServerService.getWhitelistServersList().size(); i++) {
                            if (!whitelistServerService.getWhitelistServersList().get(i).isSector()) continue;
                            whitelistServerService.getWhitelistServersList().get(i).setWhitelist(true);
                        }
                        new SendMessagePacket("&6&lWHITELIST &8&l:: &fWhitelista zostałą &awłączona &fna całym serwerze przez: &a" + commandSender.getName(), ReceiverType.ALL, MessageType.CHAT).sendToAllSectors(BukkitMain.getInstance().getSectorService());
                        break;
                    }
                    case "off": {
                        for (int i = 0; i < whitelistServerService.getWhitelistServersList().size(); i++) {
                            if (!whitelistServerService.getWhitelistServersList().get(i).isSector()) continue;
                            whitelistServerService.getWhitelistServersList().get(i).setWhitelist(false);
                        }
                        new SendMessagePacket("&6&lWHITELIST &8&l:: &fWhitelista zostałą &cwyłączona &fna całym serwerze przez: &a" + commandSender.getName(), ReceiverType.ALL, MessageType.CHAT).sendToAllSectors(BukkitMain.getInstance().getSectorService());
                        break;
                    }
                    case "add": {
                        for (int i = 0; i < whitelistServerService.getWhitelistServersList().size(); i++) {
                            if (!whitelistServerService.getWhitelistServersList().get(i).isSector()) continue;
                            whitelistServerService.getWhitelistServersList().get(i).addWhitelist(args[2]);
                        }
                        commandSender.sendMessage(ChatUtilities.colored("&6&lWHITELIST &8&l:: &fDodałeś gracza: &e" + args[2] + " &fdo whitelisty na wszystkich sektorach!"));
                        break;
                    }
                    case "remove": {
                        for (int i = 0; i < whitelistServerService.getWhitelistServersList().size(); i++) {
                            if (!whitelistServerService.getWhitelistServersList().get(i).isSector()) continue;
                            whitelistServerService.getWhitelistServersList().get(i).removeWhitelist(args[2]);
                        }
                        commandSender.sendMessage(ChatUtilities.colored("&6&lWHITELIST &8&l:: &fWyrzuciłeś gracza: &e" + args[2] + " &fz whitelisty na wszystkich sektorach!"));
                        break;
                    }
                }
                break;
            }
        }
    }
}
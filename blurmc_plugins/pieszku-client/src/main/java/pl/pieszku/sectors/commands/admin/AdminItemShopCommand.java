package pl.pieszku.sectors.commands.admin;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pieszku.api.API;
import org.pieszku.api.itemshop.ItemShopService;
import org.pieszku.api.redis.packet.itemshop.ItemShopWebsiteSynchronizePacket;
import org.pieszku.api.redis.packet.type.UpdateType;
import org.pieszku.api.service.UserService;
import org.pieszku.api.type.GroupType;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.impl.Command;
import pl.pieszku.sectors.impl.CommandInfo;
import pl.pieszku.sectors.utilities.ChatUtilities;

import java.util.ArrayList;
import java.util.List;

@CommandInfo(name = "ais", permission = GroupType.HEADADMIN)
public class AdminItemShopCommand extends Command {

    private final UserService userService = BukkitMain.getInstance().getUserService();
    private final ItemShopService itemShopService = API.getInstance().getItemShopService();

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if(commandSender instanceof Player)return;

        if(args.length < 3){
            commandSender.sendMessage(ChatUtilities.colored("&7Poprawne użycie: &b/ais <nick> <usluga> <ilosc> <id>"));
            return;
        }

        String nickName = args[0];

        switch (args[1]){
            case "vip": {
                List<String> itemShopMessage = new ArrayList<>();
                itemShopMessage.add("");
                itemShopMessage.add("               &b◄&l&3&l► &9&lONE&f&lHARD &b◄&l&3&l► ");
                itemShopMessage.add("        &7" + nickName + " &fzakupił range: &6&lVIP NA EDYCJE");
                itemShopMessage.add("      &fDziękujemy za wsparcie: &bhttps://www.blurmc.pl/shop");
                itemShopMessage.add("          &fwięcej informacji na temat rangi pod: &b/vip");
                itemShopMessage.add("");

                itemShopMessage.forEach(text -> Bukkit.broadcastMessage(ChatUtilities.colored(text)));
                this.itemShopService.findItemShopById(Integer.parseInt(args[2])).ifPresent(itemShop -> {
                    new ItemShopWebsiteSynchronizePacket(itemShop.getId(), new Gson().toJson(itemShop), UpdateType.REMOVE).sendToChannel("MASTER");
                });
            }
        }
    }
}

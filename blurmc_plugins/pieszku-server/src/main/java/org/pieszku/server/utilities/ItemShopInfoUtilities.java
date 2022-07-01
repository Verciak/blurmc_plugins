package org.pieszku.server.utilities;

import com.google.gson.Gson;
import org.pieszku.api.API;
import org.pieszku.api.redis.packet.client.SendMessagePacket;
import org.pieszku.api.redis.packet.type.MessageType;
import org.pieszku.api.redis.packet.type.ReceiverType;
import org.pieszku.api.redis.packet.user.UserPlayOutInformationPacket;
import org.pieszku.api.sector.SectorService;
import org.pieszku.api.service.UserService;
import org.pieszku.api.type.GroupType;
import org.pieszku.server.ServerMain;

import java.util.ArrayList;
import java.util.List;

public class ItemShopInfoUtilities {

    private final SectorService sectorService = ServerMain.getInstance().getSectorService();
    private final UserService userService = API.getInstance().getUserService();

    public void activeProduct(String message){
        String[] args = message.split(" ");

        String nickName  = args[1];

        this.userService.findUserByNickName(nickName).ifPresent(user -> {

            switch (args[2]){
                case "vip":{
                    List<String> itemShopMessage = new ArrayList<>();
                    itemShopMessage.add("");
                    itemShopMessage.add("               &b◄&l&3&l► &9&lONE&f&lHARD &b◄&l&3&l► ");
                    itemShopMessage.add("        &7" + nickName + " &fzakupił range: &6&lVIP NA EDYCJE");
                    itemShopMessage.add("      &fDziękujemy za wsparcie: &bhttps://www.blurmc.pl/shop");
                    itemShopMessage.add("          &fwięcej informacji na temat rangi pod: &b/vip");
                    itemShopMessage.add("");

                    user.setGroupType(GroupType.VIP);
                    user.setInteractSector(true);

                    new UserPlayOutInformationPacket(user.getNickName(), new Gson().toJson(user)).sendToAllSectors(this.sectorService);

                    itemShopMessage.forEach(text -> {
                        new SendMessagePacket(text, ReceiverType.ALL, MessageType.CHAT).sendToAllSectors(this.sectorService);
                    });
                    break;
                }
                case "svip":{
                    List<String> itemShopMessage = new ArrayList<>();
                    itemShopMessage.add("");
                    itemShopMessage.add("               &b◄&l&3&l► &9&lONE&f&lHARD &b◄&l&3&l► ");
                    itemShopMessage.add("        &7" + nickName + " &fzakupił range: &3&lSVIP NA EDYCJE");
                    itemShopMessage.add("      &fDziękujemy za wsparcie: &bhttps://www.blurmc.pl/shop");
                    itemShopMessage.add("          &fwięcej informacji na temat rangi pod: &b/svip");
                    itemShopMessage.add("");

                    user.setGroupType(GroupType.SVIP);
                    user.setInteractSector(true);

                    new UserPlayOutInformationPacket(user.getNickName(), new Gson().toJson(user)).sendToAllSectors(this.sectorService);

                    itemShopMessage.forEach(text -> {
                        new SendMessagePacket(text, ReceiverType.ALL, MessageType.CHAT).sendToAllSectors(this.sectorService);
                    });
                    break;
                }
            }

        });
    }
}

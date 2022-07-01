package org.pieszku.server.handler.kit.sync;

import com.google.gson.Gson;
import org.pieszku.api.API;
import org.pieszku.api.objects.kit.Kit;
import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.kit.sync.KitSynchronizeInformationPacket;
import org.pieszku.api.redis.packet.type.UpdateType;
import org.pieszku.api.service.KitService;
import org.pieszku.server.ServerMain;

public class KitSynchronizeInformationHandler extends RedisListener<KitSynchronizeInformationPacket> {


    private final KitService kitService = ServerMain.getInstance().getKitService();

    public KitSynchronizeInformationHandler() {
        super("MASTER", KitSynchronizeInformationPacket.class);
    }

    @Override
    public void onDecode(KitSynchronizeInformationPacket packet) {
        Kit serializedKit = new Gson().fromJson(packet.getSerializedKit(), Kit.class);
        this.kitService.findKitByName(packet.getKitName()).ifPresent(kit -> {

            switch (packet.getUpdateType()) {
                case UPDATE: {
                    this.kitService.getKits().remove(kit);
                    this.kitService.getKits().add(serializedKit);
                    API.getInstance().getKitRepository().update(kit, kit.getKitName(), UpdateType.UPDATE);
                    break;
                }
                case REMOVE: {
                    this.kitService.getKits().remove(kit);
                    API.getInstance().getKitRepository().update(kit, kit.getKitName(), UpdateType.REMOVE);
                    break;
                }
            }
        });
        if(packet.getUpdateType().equals(UpdateType.CREATE)){
            this.kitService.getKits().add(serializedKit);
            API.getInstance().getKitRepository().update(serializedKit, serializedKit.getKitName(), UpdateType.CREATE);
        }
    }
}

package pl.pieszku.sectors.redis.kit.sync;

import com.google.gson.Gson;
import org.pieszku.api.objects.kit.Kit;
import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.kit.sync.KitSynchronizeInformationPacket;
import org.pieszku.api.redis.packet.type.UpdateType;
import org.pieszku.api.service.KitService;
import pl.pieszku.sectors.BukkitMain;

public class KitSynchronizeInformationHandler extends RedisListener<KitSynchronizeInformationPacket> {


    private final KitService kitService = BukkitMain.getInstance().getKitService();

    public KitSynchronizeInformationHandler() {
        super(BukkitMain.getInstance().getSectorName(), KitSynchronizeInformationPacket.class);
    }
    @Override
    public void onDecode(KitSynchronizeInformationPacket packet) {
        Kit serializedKit = new Gson().fromJson(packet.getSerializedKit(), Kit.class);
        this.kitService.findKitByName(packet.getKitName()).ifPresent(kit -> {

            switch (packet.getUpdateType()) {
                case UPDATE: {
                    this.kitService.getKits().remove(kit);
                    this.kitService.getKits().add(serializedKit);
                    break;
                }
                case REMOVE: {
                    this.kitService.getKits().remove(kit);
                    break;
                }
            }
        });
        if(packet.getUpdateType().equals(UpdateType.CREATE)){
            this.kitService.getKits().add(serializedKit);
        }
    }
}

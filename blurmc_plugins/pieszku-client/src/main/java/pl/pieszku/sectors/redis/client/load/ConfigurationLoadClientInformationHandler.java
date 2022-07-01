package pl.pieszku.sectors.redis.client.load;

import org.bukkit.Bukkit;
import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.client.load.ConfigurationLoadClientInformationPacket;
import pl.pieszku.sectors.BukkitMain;
import pl.pieszku.sectors.service.MasterConnectionHeartbeatService;
import pl.pieszku.sectors.utilities.ChatUtilities;

public class ConfigurationLoadClientInformationHandler extends RedisListener<ConfigurationLoadClientInformationPacket> {

    private final MasterConnectionHeartbeatService masterConnectionHeartbeatService = BukkitMain.getInstance().getMasterConnectionHeartbeatService();

    public ConfigurationLoadClientInformationHandler() {
        super(BukkitMain.getInstance().getSectorName(), ConfigurationLoadClientInformationPacket.class);
    }

    @Override
    public void onDecode(ConfigurationLoadClientInformationPacket packet) {
        Bukkit.broadcastMessage(ChatUtilities.colored("&4&l:: &c&lSYSTEM &4&l:: &a&lTRWA SYNCHRONIZACJA...."));
        Bukkit.broadcastMessage(ChatUtilities.colored("&4&l:: &c&lSYSTEM &4&l:: &a&lTRWA SYNCHRONIZACJA...."));
        Bukkit.broadcastMessage(ChatUtilities.colored("&4&l:: &c&lSYSTEM &4&l:: &a&lTRWA SYNCHRONIZACJA...."));
        BukkitMain.getInstance().setConfigurationData(packet.getConfigurationData());
        System.out.println("[MASTER-SERVER] sent configuration data");
    }
}

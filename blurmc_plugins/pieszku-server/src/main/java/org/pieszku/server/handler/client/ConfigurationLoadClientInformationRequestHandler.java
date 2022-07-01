package org.pieszku.server.handler.client;

import org.pieszku.api.data.ConfigurationData;
import org.pieszku.api.redis.impl.RedisListener;
import org.pieszku.api.redis.packet.client.load.ConfigurationLoadClientInformationPacket;
import org.pieszku.api.redis.packet.client.load.ConfigurationLoadClientInformationRequestPacket;
import org.pieszku.server.ServerMain;

public class ConfigurationLoadClientInformationRequestHandler extends RedisListener<ConfigurationLoadClientInformationRequestPacket> {


    private final ConfigurationData configurationData = ServerMain.getInstance().getConfigurationData();

    public ConfigurationLoadClientInformationRequestHandler() {
        super("MASTER", ConfigurationLoadClientInformationRequestPacket.class);
    }

    @Override
    public void onDecode(ConfigurationLoadClientInformationRequestPacket packet) {
        new ConfigurationLoadClientInformationPacket(this.configurationData).sendToChannel(packet.getSectorSender());
    }
}

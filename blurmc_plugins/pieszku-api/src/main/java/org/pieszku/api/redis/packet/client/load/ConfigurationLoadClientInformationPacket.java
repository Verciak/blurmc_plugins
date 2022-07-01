package org.pieszku.api.redis.packet.client.load;

import org.pieszku.api.data.ConfigurationData;
import org.pieszku.api.redis.packet.Packet;

public class ConfigurationLoadClientInformationPacket extends Packet {


    private final ConfigurationData configurationData;

    public ConfigurationLoadClientInformationPacket(ConfigurationData configurationData){
        this.configurationData = configurationData;
    }

    public ConfigurationData getConfigurationData() {
        return configurationData;
    }

    @Override
    public String toString() {
        return "ConfigurationLoadClientInformationPacket{" +
                "configurationData=" + configurationData +
                '}';
    }
}

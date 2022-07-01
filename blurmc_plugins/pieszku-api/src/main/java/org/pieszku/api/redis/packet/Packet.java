package org.pieszku.api.redis.packet;


import org.pieszku.api.proxy.Proxy;
import org.pieszku.api.proxy.ProxyService;
import org.pieszku.api.redis.service.RedisService;
import org.pieszku.api.sector.Sector;
import org.pieszku.api.sector.SectorService;

import java.io.Serializable;

public class Packet implements Serializable {


    public void sendToSector(String channel){
        RedisService.getInstance().publish(channel, this);
    }
    public void sendToChannel(String channel){
        RedisService.getInstance().publish(channel, this);
    }
    public void sendToAllSectors(SectorService sectorService) {
        for (Sector sector : sectorService.getSectorList()) {
            RedisService.getInstance().publish(sector.getName(), this);
        }
    }
    public void sendToAllProxies(ProxyService proxyService) {
       for(Proxy proxy : proxyService.getProxyList()){
           RedisService.getInstance().publish(proxy.getName(), this);
       }
    }
    public void sendToAllSectorsAndApp(SectorService sectorService) {
        RedisService.getInstance().publish("MASTER", this);
        for (Sector sector : sectorService.getSectorList()) {
            RedisService.getInstance().publish(sector.getName(), this);
        }
    }

    public void sendToAllServers(SectorService sectorService, ProxyService proxyService) {
        for (Sector sector : sectorService.getSectorList()) {
            RedisService.getInstance().publish(sector.getName(), this);
        }
        for(Proxy proxy : proxyService.getProxyList()){
            RedisService.getInstance().publish(proxy.getName(), this);
        }
    }
}

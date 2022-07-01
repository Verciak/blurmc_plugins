package pl.pieszku.proxy;

import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import org.pieszku.api.API;
import org.pieszku.api.proxy.Proxy;
import org.pieszku.api.proxy.ProxyService;
import org.pieszku.api.proxy.user.ProxyUserService;
import org.pieszku.api.redis.RedisConnector;
import org.pieszku.api.redis.packet.proxy.request.ProxyConfigurationInformationRequestPacket;
import org.pieszku.api.redis.packet.whitelist.request.WhitelistLoadInformationRequestPacket;
import org.reflections.Reflections;
import pl.pieszku.proxy.redis.ProxyConfigurationInformationHandler;
import pl.pieszku.proxy.redis.ProxySynchronizeInformationHandler;
import pl.pieszku.proxy.redis.master.MasterHeartbeatHandler;
import pl.pieszku.proxy.redis.whitelist.WhitelistLoadInformationHandler;
import pl.pieszku.proxy.redis.whitelist.WhitelistSynchronizeInformationHandler;
import pl.pieszku.proxy.runnable.ProxyInformationRunnable;
import pl.pieszku.proxy.service.MasterConnectionHeartbeatService;

import java.util.Optional;

public class ProxyMain extends Plugin {


    private API api;
    private String proxyName;
    private static ProxyMain instance;
    private ProxyService proxyService;
    private RedisConnector redisConnector;
    private ProxyUserService proxyUserService;
    private MasterConnectionHeartbeatService masterConnectionHeartbeatService;

    public static ProxyMain getInstance() {
        return instance;
    }
    public Optional<Proxy> currentProxy(){
        return this.proxyService.findProxyByName(this.proxyName);
    }

    @Override
    public void onEnable(){
        instance = this;
        this.proxyName = "proxy01";
        this.api = new API(false);
        this.masterConnectionHeartbeatService = new MasterConnectionHeartbeatService();
        this.proxyService = this.api.getProxyService();
        this.proxyUserService = this.api.getProxyUserService();
        this.redisConnector = this.api.getRedisConnector();
        this.setupInformation();
        this.registerHandlers();
    }

    private void registerHandlers() {
        new Reflections("pl.pieszku.proxy.handler").getSubTypesOf(Listener.class).forEach(this::accept);
    }

    private void setupInformation() {
        this.redisConnector.connect();
        this.redisConnector.getRedisService().subscribe(this.proxyName, new ProxyConfigurationInformationHandler());
        this.redisConnector.getRedisService().subscribe(this.proxyName, new ProxySynchronizeInformationHandler());
        this.redisConnector.getRedisService().subscribe(this.proxyName, new WhitelistLoadInformationHandler());
        this.redisConnector.getRedisService().subscribe(this.proxyName, new WhitelistSynchronizeInformationHandler());
        this.redisConnector.getRedisService().subscribe("CLIENT", new MasterHeartbeatHandler());

        new ProxyConfigurationInformationRequestPacket(this.proxyName).sendToChannel("MASTER");
        new WhitelistLoadInformationRequestPacket(this.proxyName).sendToChannel("MASTER");
        new ProxyInformationRunnable().start();
    }

    public MasterConnectionHeartbeatService getMasterConnectionHeartbeatService() {
        return masterConnectionHeartbeatService;
    }

    public ProxyUserService getProxyUserService() {
        return proxyUserService;
    }

    public String getProxyName() {
        return proxyName;
    }

    public ProxyService getProxyService() {
        return proxyService;
    }

    public API getApi() {
        return api;
    }

    public RedisConnector getRedisConnector() {
        return redisConnector;
    }

    private void accept(Class<? extends Listener> listener) {
        try {
            this.getProxy().getPluginManager().registerListener(this, listener.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

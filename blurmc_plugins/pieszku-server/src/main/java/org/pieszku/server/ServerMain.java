package org.pieszku.server;

import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import org.pieszku.api.API;
import org.pieszku.api.data.ConfigurationData;
import org.pieszku.api.data.drop.CaseDropJson;
import org.pieszku.api.data.drop.StoneDropJson;
import org.pieszku.api.data.guild.GuildPermissionJson;
import org.pieszku.api.data.shop.ShopBuyJson;
import org.pieszku.api.data.shop.ShopSellJson;
import org.pieszku.api.data.shop.ShopVillagerJson;
import org.pieszku.api.proxy.ProxyService;
import org.pieszku.api.proxy.global.WhitelistServerType;
import org.pieszku.api.proxy.json.ProxyJson;
import org.pieszku.api.redis.RedisConnector;
import org.pieszku.api.sector.SectorService;
import org.pieszku.api.sector.json.SectorJson;
import org.pieszku.api.service.BanService;
import org.pieszku.api.service.GuildService;
import org.pieszku.api.service.KitService;
import org.pieszku.api.service.UserService;
import org.pieszku.server.data.Configuration;
import org.pieszku.server.handler.backup.BackupSynchronizeInformationHandler;
import org.pieszku.server.handler.ban.load.BanInformationLoadRequestHandler;
import org.pieszku.server.handler.ban.request.BanInformationRequestHandler;
import org.pieszku.server.handler.ban.sync.BanSynchronizeInformationHandler;
import org.pieszku.server.handler.client.ConfigurationLoadClientInformationRequestHandler;
import org.pieszku.server.handler.client.ConfigurationReloadClientHandler;
import org.pieszku.server.handler.guild.load.GuildLoadInformationRequestHandler;
import org.pieszku.server.handler.guild.sync.GuildInformationSynchronizeHandler;
import org.pieszku.server.handler.itemshop.ItemShopReceivedWebsiteHandler;
import org.pieszku.server.handler.itemshop.ItemShopWebsiteSynchronizeHandler;
import org.pieszku.server.handler.kit.load.KitInformationLoadRequestHandler;
import org.pieszku.server.handler.kit.sync.KitSynchronizeInformationHandler;
import org.pieszku.server.handler.mute.MuteInformationSynchronizeHandler;
import org.pieszku.server.handler.mute.MuteLoadInformationRequestHandler;
import org.pieszku.server.handler.proxy.request.ProxyConfigurationInformationRequestHandler;
import org.pieszku.server.handler.proxy.sync.ProxySynchronizeInformationHandler;
import org.pieszku.server.handler.sector.SectorInformationRequestHandler;
import org.pieszku.server.handler.sector.request.SectorConfigurationDiscordRequestHandler;
import org.pieszku.server.handler.sector.request.SectorConfigurationRequestHandler;
import org.pieszku.server.handler.sector.sync.SectorInformationSynchronizeHandler;
import org.pieszku.server.handler.user.UserPlayOutRequestHandler;
import org.pieszku.server.handler.user.load.UserInformationLoadRequestHandler;
import org.pieszku.server.handler.user.request.UserTransferInformationRequestHandler;
import org.pieszku.server.handler.user.sync.UserInformationSynchronizeHandler;
import org.pieszku.server.handler.whitelist.WhitelistLoadRequestInformationHandler;
import org.pieszku.server.handler.whitelist.WhitelistSynchronizeInformationHandler;
import org.pieszku.server.runnable.MasterHeartbeatRunnable;
import org.pieszku.server.runnable.ProxyInformationRunnable;
import org.pieszku.server.runnable.SectorInformationRunnable;

import java.util.Arrays;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.Logger;

public class ServerMain {


    private final SectorService sectorService;
    private final GuildService guildService;
    private static ServerMain instance;
    private final RedisConnector redisConnector;
    private SectorJson sectorJson;
    private ProxyJson proxyJson;
    private final UserService userService;
    private final BanService banService;
    private final KitService kitService;
    private ConfigurationData configurationData;
    private final ProxyService proxyService;
    private final API api;

    private StatefulRedisPubSubConnection<String, String> pubSubConnectionItemShop;

    public ServerMain(){
        this.api = new API(true);
        instance = this;
        this.sectorService = new SectorService();
        this.userService = this.api.getUserService();
        this.guildService = this.api.getGuildService();
        this.banService = this.api.getBanService();
        this.kitService = this.api.getKitService();
        this.proxyService = this.api.getProxyService();
        this.configurationData = new ConfigurationData();
        this.redisConnector = this.api.getRedisConnector();
        this.setupConfiguration();
        Logger.getAnonymousLogger().info("[MASTER-SERVER] Loading sectors...");
        this.loadSectors();
        this.loadProxies();
    }

    private void loadWhitelists() {
        this.proxyService.getProxyList().forEach(proxy -> {
            this.api.getWhitelistServerService().findOrCreate(proxy.getName(), WhitelistServerType.PROXY);
        });
        this.sectorService.getSectorList().forEach(sector -> {
            this.api.getWhitelistServerService().findOrCreate(sector.getName(), WhitelistServerType.SECTOR);
        });
        System.out.println("[MASTER-SERVER] Loaded: " + this.api.getWhitelistServerService().getWhitelistServersList().size() + " whitelist.");
    }
    private void loadProxies() {
        Arrays.stream(this.proxyJson.proxies).forEach(proxy -> {
            this.proxyService.getProxyList().add(proxy);
        });
        Logger.getAnonymousLogger().info("[MASTER-SERVER] Loaded: " + this.proxyService.getProxyList().size() + " proxies");
    }

    public void start(){
        Logger.getAnonymousLogger().info("[MASTER-SERVER] Connecting to the redis-server...");
        this.redisConnector.connect();


        System.out.println("[MASTER-SERVER] Registering handler itemshop-website...");
        this.pubSubConnectionItemShop = this.redisConnector.getRedisSystem().getRedisClient().connectPubSub();
        this.pubSubConnectionItemShop.sync().subscribe("blurmc");
        this.pubSubConnectionItemShop.addListener(new ItemShopReceivedWebsiteHandler());
        System.out.println("[MASTER-SERVER] Registering handler itemshop-website successfully");

        Logger.getAnonymousLogger().info("[MASTER-SERVER] Registering handlers...");
        this.redisConnector.getRedisService().subscribe("MASTER", new SectorInformationRequestHandler());
        this.redisConnector.getRedisService().subscribe("MASTER", new SectorInformationSynchronizeHandler());
        this.redisConnector.getRedisService().subscribe("MASTER", new SectorConfigurationRequestHandler());
        this.redisConnector.getRedisService().subscribe("MASTER", new UserTransferInformationRequestHandler());
        this.redisConnector.getRedisService().subscribe("MASTER", new UserInformationSynchronizeHandler());
        this.redisConnector.getRedisService().subscribe("MASTER", new SectorConfigurationDiscordRequestHandler());
        this.redisConnector.getRedisService().subscribe("MASTER", new UserPlayOutRequestHandler());
        this.redisConnector.getRedisService().subscribe("MASTER", new GuildLoadInformationRequestHandler());
        this.redisConnector.getRedisService().subscribe("MASTER", new GuildInformationSynchronizeHandler());
        this.redisConnector.getRedisService().subscribe("MASTER", new UserInformationLoadRequestHandler());
        this.redisConnector.getRedisService().subscribe("MASTER", new BanInformationLoadRequestHandler());
        this.redisConnector.getRedisService().subscribe("MASTER", new BanSynchronizeInformationHandler());
        this.redisConnector.getRedisService().subscribe("MASTER", new BanInformationRequestHandler());
        this.redisConnector.getRedisService().subscribe("MASTER", new KitInformationLoadRequestHandler());
        this.redisConnector.getRedisService().subscribe("MASTER", new KitSynchronizeInformationHandler());
        this.redisConnector.getRedisService().subscribe("MASTER", new ConfigurationLoadClientInformationRequestHandler());
        this.redisConnector.getRedisService().subscribe("MASTER", new ConfigurationReloadClientHandler());
        this.redisConnector.getRedisService().subscribe("MASTER", new ProxyConfigurationInformationRequestHandler());
        this.redisConnector.getRedisService().subscribe("MASTER", new ProxySynchronizeInformationHandler());
        this.redisConnector.getRedisService().subscribe("MASTER", new MuteLoadInformationRequestHandler());
        this.redisConnector.getRedisService().subscribe("MASTER", new MuteInformationSynchronizeHandler());
        this.redisConnector.getRedisService().subscribe("MASTER", new BackupSynchronizeInformationHandler());
        this.redisConnector.getRedisService().subscribe("MASTER", new WhitelistSynchronizeInformationHandler());
        this.redisConnector.getRedisService().subscribe("MASTER", new WhitelistLoadRequestInformationHandler());
        this.redisConnector.getRedisService().subscribe("MASTER", new ItemShopWebsiteSynchronizeHandler());

        Logger.getAnonymousLogger().info("[MASTER-SERVER] Running runnable...");
        new SectorInformationRunnable().start();
        new MasterHeartbeatRunnable().start();
        new ProxyInformationRunnable().start();
        this.loadWhitelists();
        LockSupport.park();
    }
    public static void main(String[] args){
        Logger.getAnonymousLogger().info("[MASTER-SERVER] Loading...");
        new ServerMain().start();
        Logger.getAnonymousLogger().info("[MASTER-SERVER] Enable");
    }

    public void loadSectors(){
        Arrays.stream(this.sectorJson.getSectors()).forEach(sector -> {
            this.sectorService.create(sector.getName(), sector.getSectorType(), sector.getLocationMinimum(), sector.getLocationMaximum(), sector.getMinSlots(), sector.getMaxSlots());
        });
        Logger.getAnonymousLogger().info("[MASTER-SERVER] Loaded: " + this.sectorService.getSectorList().size() + " sectors");
    }
    public void setupConfiguration() {
        Configuration<SectorJson> sectorJsonConfiguration = new Configuration<>("sectors", "config", SectorJson.class);
        sectorJsonConfiguration.init();
        this.sectorJson = sectorJsonConfiguration.getConfiguration();

        Configuration<ProxyJson> proxyJsonConfiguration = new Configuration<>("proxies", "proxy", ProxyJson.class);
        proxyJsonConfiguration.init();
        this.proxyJson = proxyJsonConfiguration.getConfiguration();

        Configuration<ShopSellJson> shopSellJsonConfiguration = new Configuration<>("client", "shopSell", ShopSellJson.class);
        shopSellJsonConfiguration.init();
        this.configurationData.setShopSellJson(shopSellJsonConfiguration.getConfiguration());

        Configuration<ShopBuyJson> shopBuyJsonConfiguration = new Configuration<>("client","shopBuy", ShopBuyJson.class);
        shopBuyJsonConfiguration.init();
        this.configurationData.setShopBuyJson(shopBuyJsonConfiguration.getConfiguration());

        Configuration<ShopVillagerJson> shopVillagerJsonConfiguration = new Configuration<>("client", "shopVillager", ShopVillagerJson.class);
        shopVillagerJsonConfiguration.init();
        this.configurationData.setShopVillagerJson(shopVillagerJsonConfiguration.getConfiguration());

        Configuration<CaseDropJson> caseDropJsonConfig =  new Configuration<>("client", "dropCase", CaseDropJson.class);
        caseDropJsonConfig.init();
       this.configurationData.setCaseDropJson(caseDropJsonConfig.getConfiguration());

       Configuration<GuildPermissionJson> permissionGuildJsonConfig =  new Configuration<>("client", "guildPermission", GuildPermissionJson.class);
        permissionGuildJsonConfig.init();
       this.configurationData.setGuildPermissionJson(permissionGuildJsonConfig.getConfiguration());

        Configuration<StoneDropJson> stoneDropJsonConfig =  new Configuration<>("sectors", "dropStone", StoneDropJson.class);
        stoneDropJsonConfig.init();
        this.configurationData.setStoneDropJson(stoneDropJsonConfig.getConfiguration());
    }

    public API getApi() {
        return api;
    }

    public ProxyJson getProxyJson() {
        return proxyJson;
    }

    public ProxyService getProxyService() {
        return proxyService;
    }

    public SectorService getSectorService() {
        return sectorService;
    }
    public static ServerMain getInstance() {
        return instance;
    }

    public SectorJson getSectorJson() {
        return sectorJson;
    }

    public UserService getUserService() {
        return userService;
    }

    public GuildService getGuildService() {
        return guildService;
    }

    public BanService getBanService() {
        return banService;
    }

    public KitService getKitService() {
        return kitService;
    }
    public ConfigurationData getConfigurationData() {
        return configurationData;
    }
    public void setConfigurationData(ConfigurationData configurationData) {
        this.configurationData = configurationData;
    }
}

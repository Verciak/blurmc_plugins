package org.pieszku.api;

import org.pieszku.api.cache.BazaarCache;
import org.pieszku.api.itemshop.ItemShopRepository;
import org.pieszku.api.itemshop.ItemShopService;
import org.pieszku.api.mongo.MongoService;
import org.pieszku.api.objects.repository.*;
import org.pieszku.api.proxy.ProxyService;
import org.pieszku.api.proxy.global.WhitelistServerRepository;
import org.pieszku.api.proxy.global.WhitelistServerService;
import org.pieszku.api.proxy.user.ProxyUserService;
import org.pieszku.api.proxy.user.repository.ProxyUserRepository;
import org.pieszku.api.redis.RedisConnector;
import org.pieszku.api.service.*;

public class API {


    private static API instance;
    private final RedisConnector redisConnector;
    private MongoService mongoService;
    private final UserService userService = new UserService();
    private final GuildService guildService= new GuildService();
    private final KitService kitService = new KitService();
    private final BanService banService = new BanService();
    private final UserRepository userRepository = new UserRepository();
    private final GuildRepository guildRepository = new GuildRepository();
    private final KitRepository kitRepository = new KitRepository();
    private final BanRepository banRepository = new BanRepository();
    private final ProxyService proxyService = new ProxyService();
    private final ProxyUserRepository proxyUserRepository = new ProxyUserRepository();
    private final ProxyUserService proxyUserService = new ProxyUserService();
    private final BazaarCache bazaarCache = new BazaarCache();
    private final BazaarRepository bazaarRepository = new BazaarRepository();
    private final MuteRepository muteRepository = new MuteRepository();
    private final MuteService muteService = new MuteService();
    private final BackupRepository backupRepository = new BackupRepository();
    private final BackupService backupService = new BackupService();
    private final WhitelistServerService whitelistServerService = new WhitelistServerService();
    private final WhitelistServerRepository whitelistServerRepository = new WhitelistServerRepository();
    private final ItemShopService itemShopService = new ItemShopService();
    private final ItemShopRepository itemShopRepository = new ItemShopRepository();


    public API(boolean database){
        instance = this;

        // this.redisConnector  = new RedisConnector("146.59.83.112", "password");
        this.redisConnector  = new RedisConnector("51.38.147.236", "");

        if(database){
            this.mongoService  = new MongoService();
            this.setupInformation();

        }
    }

    private void setupInformation() {
        this.proxyUserRepository.findAll().forEach(proxyUser -> this.proxyUserService.getUserMap().put(proxyUser.getNickName(), proxyUser));
        this.userRepository.findAll().forEach(user -> this.userService.getUserMap().put(user.getNickName(), user));
        this.guildRepository.findAll().forEach(guild -> this.guildService.getGuildList().add(guild));
        this.kitRepository.findAll().forEach(kit -> this.kitService.getKits().add(kit));
        this.banRepository.findAll().forEach(ban -> this.banService.getBanList().add(ban));
        this.bazaarRepository.findAll().forEach(bazaar -> this.bazaarCache.getBazaars().add(bazaar));
        this.muteRepository.findAll().forEach(mute -> this.muteService.getMuteList().add(mute));
        this.backupRepository.findAll().forEach(backup -> this.backupService.getBackups().add(backup));
        this.whitelistServerRepository.findAll().forEach(whitelistServer -> this.whitelistServerService.getWhitelistServersList().add(whitelistServer));
        this.itemShopRepository.findAll().forEach(itemShop -> this.itemShopService.getItemShops().add(itemShop));

    }

    public ItemShopRepository getItemShopRepository() {
        return itemShopRepository;
    }

    public ItemShopService getItemShopService() {
        return itemShopService;
    }

    public WhitelistServerService getWhitelistServerService() {
        return whitelistServerService;
    }

    public WhitelistServerRepository getWhitelistServerRepository() {
        return whitelistServerRepository;
    }

    public BackupRepository getBackupRepository() {
        return backupRepository;
    }

    public BackupService getBackupService() {
        return backupService;
    }

    public MuteRepository getMuteRepository() {
        return muteRepository;
    }

    public MuteService getMuteService() {
        return muteService;
    }

    public BazaarCache getBazaarCache() {
        return bazaarCache;
    }

    public BazaarRepository getBazaarRepository() {
        return bazaarRepository;
    }

    public ProxyService getProxyService() {
        return proxyService;
    }

    public GuildRepository getGuildRepository() {
        return guildRepository;
    }

    public BanRepository getBanRepository() {
        return banRepository;
    }

    public KitRepository getKitRepository() {
        return kitRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public KitService getKitService() {
        return kitService;
    }

    public ProxyUserRepository getProxyUserRepository() {
        return proxyUserRepository;
    }
    public ProxyUserService getProxyUserService() {
        return proxyUserService;
    }

    public BanService getBanService() {
        return banService;
    }

    public GuildService getGuildService() {
        return guildService;
    }

    public UserService getUserService() {
        return userService;
    }

    public MongoService getMongoService() {
        return mongoService;
    }

    public static API getInstance() {
        return instance;
    }

    public RedisConnector getRedisConnector() {
        return redisConnector;
    }
}

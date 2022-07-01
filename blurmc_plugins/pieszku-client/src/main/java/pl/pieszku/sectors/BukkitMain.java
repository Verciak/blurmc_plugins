package pl.pieszku.sectors;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.pieszku.api.API;
import org.pieszku.api.data.ConfigurationData;
import org.pieszku.api.redis.RedisConnector;
import org.pieszku.api.redis.packet.ban.load.BanInformationLoadRequestPacket;
import org.pieszku.api.redis.packet.client.load.ConfigurationLoadClientInformationRequestPacket;
import org.pieszku.api.redis.packet.guild.load.GuildLoadInformationRequestPacket;
import org.pieszku.api.redis.packet.kit.load.KitInformationLoadRequestPacket;
import org.pieszku.api.redis.packet.mute.load.MuteLoadInformationRequestPacket;
import org.pieszku.api.redis.packet.sector.request.SectorConfigurationRequestPacket;
import org.pieszku.api.redis.packet.user.load.UserInformationLoadRequestPacket;
import org.pieszku.api.redis.packet.whitelist.request.WhitelistLoadInformationRequestPacket;
import org.pieszku.api.sector.Sector;
import org.pieszku.api.sector.SectorService;
import org.pieszku.api.service.BanService;
import org.pieszku.api.service.GuildService;
import org.pieszku.api.service.KitService;
import org.pieszku.api.service.UserService;
import org.reflections.Reflections;
import pl.pieszku.sectors.cache.BazaarGUICache;
import pl.pieszku.sectors.cache.BukkitCache;
import pl.pieszku.sectors.commands.guild.GuildCommand;
import pl.pieszku.sectors.configuration.ConfigAPI;
import pl.pieszku.sectors.gui.MinecraftGUIHandler;
import pl.pieszku.sectors.helper.InventoryHelper;
import pl.pieszku.sectors.helper.ReflectionHelper;
import pl.pieszku.sectors.redis.backup.BackupSynchronizeInformationHandler;
import pl.pieszku.sectors.redis.ban.BanInformationHandler;
import pl.pieszku.sectors.redis.ban.load.BanInformationLoadHandler;
import pl.pieszku.sectors.redis.ban.sync.BanSynchronizeInformationHandler;
import pl.pieszku.sectors.redis.client.ChatSynchronizeHandler;
import pl.pieszku.sectors.redis.client.SendMessageHandler;
import pl.pieszku.sectors.redis.client.kick.KickPlayerHandler;
import pl.pieszku.sectors.redis.client.load.ConfigurationLoadClientInformationHandler;
import pl.pieszku.sectors.redis.client.teleport.TeleportPlayerInformationHandler;
import pl.pieszku.sectors.redis.client.teleport.TeleportPlayerRequestHandler;
import pl.pieszku.sectors.redis.guild.load.GuildLoadInformationHandler;
import pl.pieszku.sectors.redis.guild.sync.GuildInformationSynchronizeHandler;
import pl.pieszku.sectors.redis.kit.load.KitInformationLoadHandler;
import pl.pieszku.sectors.redis.kit.sync.KitSynchronizeInformationHandler;
import pl.pieszku.sectors.redis.master.MasterHeartbeatHandler;
import pl.pieszku.sectors.redis.mute.MuteInformationSynchronizeHandler;
import pl.pieszku.sectors.redis.mute.MuteLoadInformationHandler;
import pl.pieszku.sectors.redis.sector.SectorConfigurationHandler;
import pl.pieszku.sectors.redis.sector.sync.SectorInformationSynchronizeHandler;
import pl.pieszku.sectors.redis.user.UserPlayOutInformationHandler;
import pl.pieszku.sectors.redis.user.UserTransferInformationHandler;
import pl.pieszku.sectors.redis.user.load.UserInformationLoadHandler;
import pl.pieszku.sectors.redis.whitelist.WhitelistLoadInformationHandler;
import pl.pieszku.sectors.redis.whitelist.WhitelistSynchronizeInformationHandler;
import pl.pieszku.sectors.runnable.*;
import pl.pieszku.sectors.service.CommandService;
import pl.pieszku.sectors.service.MasterConnectionHeartbeatService;
import pl.pieszku.sectors.service.TeamService;
import pl.pieszku.sectors.utilities.SignEditorUtilities;
import pl.pieszku.sectors.utilities.TablistUtilities;

import java.util.Optional;

public class BukkitMain extends JavaPlugin implements PluginMessageListener {


    private API api;
    private RedisConnector redisConnector;
    private SectorService sectorService;
    private UserService userService;
    private String sectorName;
    private static BukkitMain instance;
    private CommandService commandService;
    private MasterConnectionHeartbeatService masterConnectionHeartbeatService;
    private GuildService guildService;
    private BanService banService;
    private KitService kitService;
    private BukkitCache bukkitCache;
    private ConfigurationData configurationData;
    private TablistUtilities tablistUtilities;
    private BazaarGUICache bazaarGUICache;
    private TeamService teamService;
    private ConfigAPI configAPI;
    private boolean chatStatus = true;

    public static BukkitMain getInstance() {
        return instance;
    }


    @Override
    public void onLoad() {
        instance = this;
        this.api = new API(false);
        this.sectorService = new SectorService();
        this.userService = this.api.getUserService();
        this.guildService = this.api.getGuildService();
        this.banService = this.api.getBanService();
        this.kitService = this.api.getKitService();
        this.bukkitCache = new BukkitCache();
        this.bazaarGUICache = new BazaarGUICache();
        this.tablistUtilities = new TablistUtilities();
        this.teamService = new TeamService();
        this.configurationData = new ConfigurationData();
        this.redisConnector = this.api.getRedisConnector();
    }

    @Override
    public void onEnable() {
        org.spigotmc.AsyncCatcher.enabled = false;
        new ReflectionHelper().initialize();

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
        this.commandService = new CommandService();
        this.masterConnectionHeartbeatService = new MasterConnectionHeartbeatService();

        this.redisConnector.connect();
        this.bazaarGUICache.create(1);

        this.configAPI = new ConfigAPI(this, "sectors");
        configAPI.load();
        configAPI.setInstantSave(true);

        this.sectorName = configAPI.getOrCreate("name", "spawn01");
        this.redisConnector.getRedisService().subscribe("CLIENT", new MasterHeartbeatHandler());
        this.redisConnector.getRedisService().subscribe(this.sectorName, new SectorConfigurationHandler());
        this.redisConnector.getRedisService().subscribe(this.sectorName, new SectorInformationSynchronizeHandler());
        this.redisConnector.getRedisService().subscribe(this.sectorName, new UserTransferInformationHandler());
        this.redisConnector.getRedisService().subscribe(this.sectorName, new UserPlayOutInformationHandler());
        this.redisConnector.getRedisService().subscribe(this.sectorName, new GuildLoadInformationHandler());
        this.redisConnector.getRedisService().subscribe(this.sectorName, new GuildInformationSynchronizeHandler());
        this.redisConnector.getRedisService().subscribe(this.sectorName, new UserInformationLoadHandler());
        this.redisConnector.getRedisService().subscribe(this.sectorName, new BanSynchronizeInformationHandler());
        this.redisConnector.getRedisService().subscribe(this.sectorName, new BanInformationLoadHandler());
        this.redisConnector.getRedisService().subscribe(this.sectorName, new BanInformationHandler());
        this.redisConnector.getRedisService().subscribe(this.sectorName, new SendMessageHandler());
        this.redisConnector.getRedisService().subscribe(this.sectorName, new KitInformationLoadHandler());
        this.redisConnector.getRedisService().subscribe(this.sectorName, new KitSynchronizeInformationHandler());
        this.redisConnector.getRedisService().subscribe(this.sectorName, new ConfigurationLoadClientInformationHandler());
        this.redisConnector.getRedisService().subscribe(this.sectorName, new ChatSynchronizeHandler());
        this.redisConnector.getRedisService().subscribe(this.sectorName, new MuteLoadInformationHandler());
        this.redisConnector.getRedisService().subscribe(this.sectorName, new MuteInformationSynchronizeHandler());
        this.redisConnector.getRedisService().subscribe(this.sectorName, new BackupSynchronizeInformationHandler());
        this.redisConnector.getRedisService().subscribe(this.sectorName, new WhitelistLoadInformationHandler());
        this.redisConnector.getRedisService().subscribe(this.sectorName, new WhitelistSynchronizeInformationHandler());
        this.redisConnector.getRedisService().subscribe("SECTORS", new TeleportPlayerInformationHandler());
        this.redisConnector.getRedisService().subscribe("SECTORS", new TeleportPlayerRequestHandler());
        this.redisConnector.getRedisService().subscribe("SECTORS", new KickPlayerHandler());


        new SectorConfigurationRequestPacket(this.sectorName).sendToChannel("MASTER");
        new SectorSynchronizeRunnable().start();
        new DepositPlayerInventoryRunnable().start();
        new MasterConnectionCheckTask().start();
        new AlphaTestInformationRunnable().start();
        new AutoMessageRunnable().start();
  //      new DiscoArmorRunnable().start();
        new VanishInformationRunnable().start();
        new ActionBarInformationRunnable().start();
        new UserInformationSynchronizeUpdateRunnable().start();
        new ArmorStandBossBarUpdateRunnable().start();

        this.setupInformation();

        this.registerCommand();
        this.registerHandler();
        new SignEditorUtilities().listen();

    }

    private void setupInformation() {
        new GuildLoadInformationRequestPacket(this.sectorName).sendToChannel("MASTER");
        new UserInformationLoadRequestPacket(this.sectorName).sendToChannel("MASTER");
        new BanInformationLoadRequestPacket(this.sectorName).sendToChannel("MASTER");
        new KitInformationLoadRequestPacket(this.sectorName).sendToChannel("MASTER");
        new MuteLoadInformationRequestPacket(this.sectorName).sendToChannel("MASTER");
        new WhitelistLoadInformationRequestPacket(this.sectorName).sendToChannel("MASTER");
        new ConfigurationLoadClientInformationRequestPacket(this.sectorName).sendToChannel("MASTER");
    }

    private void registerCommand() {
        this.commandService.load();
        this.commandService.loadGuilds(new GuildCommand());
    }

    private void registerHandler() {
        Bukkit.getPluginManager().registerEvents(new InventoryHelper(), this);
        Bukkit.getPluginManager().registerEvents(new MinecraftGUIHandler(), this);
        for (Class<? extends Listener> listener : new Reflections("pl.pieszku.sectors.handler").getSubTypesOf(Listener.class)) {
            try {
                Bukkit.getPluginManager().registerEvents(listener.newInstance(), this);
            } catch (InstantiationException | IllegalAccessException exception) {
                exception.printStackTrace();
            }
        }
    }

    public void connect(Player player, String sectorName) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(sectorName);
        player.sendPluginMessage(this, "BungeeCord", out.toByteArray());
    }

    public Optional<Sector> getCurrentSector(){
        return this.sectorService.findSectorByName("lobby");
    }

    public SectorService getSectorService() {
        return sectorService;
    }

    public MasterConnectionHeartbeatService getMasterConnectionHeartbeatService() {
        return masterConnectionHeartbeatService;
    }

    public BazaarGUICache getBazaarGUICache() {
        return bazaarGUICache;
    }

    public RedisConnector getRedisConnector() {
        return redisConnector;
    }

    public ConfigAPI getConfigAPI() {
        return configAPI;
    }

    public UserService getUserService() {
        return userService;
    }

    public String getSectorName() {
        return sectorName;
    }

    public API getApi() {
        return api;
    }

    public GuildService getGuildService() {
        return this.guildService;
    }

    public BanService getBanService() {
        return banService;
    }

    public KitService getKitService() {
        return kitService;
    }
    public BukkitCache getBukkitCache() {
        return bukkitCache;
    }

    public CommandService getCommandService() {
        return commandService;
    }

    public TablistUtilities getTablistUtilities() {
        return tablistUtilities;
    }

    public ConfigurationData getConfigurationData() {
        return configurationData;
    }
    public void setConfigurationData(ConfigurationData configurationData) {
        this.configurationData = configurationData;
    }

    public TeamService getTeamService() {
        return teamService;
    }

    public boolean isChatStatus() {
        return chatStatus;
    }

    public void setChatStatus(boolean chatStatus) {
        this.chatStatus = chatStatus;
    }

    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {

    }
}

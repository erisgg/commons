package gg.eris.commons.bukkit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.ReadConcern;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoDatabase;
import gg.eris.commons.bukkit.chat.ChatController;
import gg.eris.commons.bukkit.command.CommandManager;
import gg.eris.commons.bukkit.impl.chat.ChatControllerImpl;
import gg.eris.commons.bukkit.impl.chat.ChatControllerListener;
import gg.eris.commons.bukkit.impl.command.CommandManagerImpl;
import gg.eris.commons.bukkit.impl.item.ItemListener;
import gg.eris.commons.bukkit.impl.menu.MenuListener;
import gg.eris.commons.bukkit.impl.player.ErisPlayerManagerImpl;
import gg.eris.commons.bukkit.impl.scoreboard.ScoreboardControllerImpl;
import gg.eris.commons.bukkit.impl.scoreboard.ScoreboardListener;
import gg.eris.commons.bukkit.impl.tablist.TablistControllerImpl;
import gg.eris.commons.bukkit.impl.tablist.TablistListener;
import gg.eris.commons.bukkit.permission.PermissionRegistry;
import gg.eris.commons.bukkit.player.DefaultErisPlayerSerializer;
import gg.eris.commons.bukkit.player.ErisPlayerManager;
import gg.eris.commons.bukkit.player.OfflineDataManager;
import gg.eris.commons.bukkit.rank.RankRegistry;
import gg.eris.commons.bukkit.scoreboard.ScoreboardController;
import gg.eris.commons.bukkit.tablist.TablistController;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.core.database.MongoCredentials;
import gg.eris.commons.core.database.MongoDbProvider;
import gg.eris.commons.core.redis.RedisWrapper;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class ErisBukkitCommonsPlugin extends JavaPlugin implements ErisBukkitCommons,
    Listener {

  private static ErisBukkitCommonsPlugin INSTANCE;

  private MongoDatabase mongoDatabase;
  private RedisWrapper redisWrapper;

  private ScoreboardController scoreboardController;
  private ChatController chatController;
  private CommandManager commandManager;
  private TablistController tablistController;
  private PermissionRegistry permissionRegistry;
  private RankRegistry rankRegistry;
  private ObjectMapper objectMapper;

  private ErisPlayerManager erisPlayerManager;

  @Override
  public void onEnable() {
    INSTANCE = this;
    saveDefaultConfig();

    FileConfiguration config = getConfig();

    this.mongoDatabase = MongoDbProvider.newClient(
        new MongoCredentials(
            config.getString("database.username"),
            config.getString("database.password"),
            config.getString("database.database"),
            config.getString("database.hostname"),
            config.getInt("database.port")
        )
    ).withReadConcern(ReadConcern.MAJORITY)
        .withWriteConcern(WriteConcern.MAJORITY);

    this.redisWrapper = RedisWrapper.newWrapper(
        config.getString("redis.password"),
        config.getString("redis.hostname"),
        config.getInt("redis.port")
    );

    this.objectMapper = new ObjectMapper();
    this.commandManager = new CommandManagerImpl();
    this.chatController = new ChatControllerImpl(this);
    this.permissionRegistry = new PermissionRegistry();
    this.rankRegistry = new RankRegistry();
    this.erisPlayerManager = new ErisPlayerManagerImpl(this);
    this.tablistController = new TablistControllerImpl(this);
    this.scoreboardController = new ScoreboardControllerImpl(this, this.erisPlayerManager);

    PluginManager pluginManager = Bukkit.getPluginManager();
    pluginManager.registerEvents(new MenuListener(this), this);
    pluginManager.registerEvents(
        new ChatControllerListener(this.erisPlayerManager, this.chatController), this);
    pluginManager.registerEvents(
        new ScoreboardListener((ScoreboardControllerImpl) this.scoreboardController), this);
    pluginManager
        .registerEvents(new TablistListener((TablistControllerImpl) this.tablistController), this);
    pluginManager
        .registerEvents(new ItemListener(), this);

    // Registering messaging channel
    this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

    // Register service
    ServicesManager servicesManager = Bukkit.getServicesManager();
    servicesManager.register(ErisBukkitCommons.class, this, this, ServicePriority.Highest);

    // Setting the player provider if none has been set by any plugin
    Bukkit.getScheduler().runTask(this, () -> {
      if (!((ErisPlayerManagerImpl) this.erisPlayerManager).isPlayerSerializerSet()) {
        this.erisPlayerManager.setPlayerSerializer(new DefaultErisPlayerSerializer());
      }

      // Default chat format is:
      // rankColor rankPrefix nameColor name message
      if (this.chatController.getFormat() == null) {
        this.chatController.setFormat("{0}[{1}]</col> {2}{3}: <raw>{4}</raw></col>",
            (player, chatMessage) -> "<col=" + player.getPriorityRank().getColor().getId() + ">",
            (player, chatMessage) -> player.getPriorityRank().getRawDisplay(),
            (player, chatMessage) -> player.getPriorityRank().isWhiteChat() ?
                "<col=white>" : "<col=gray>",
            (player, chatMessage) -> player.getName(),
            (player, chatMessage) -> chatMessage);
      }
    });
  }

  @Override
  public void onDisable() {
    for (Player player : Bukkit.getOnlinePlayers()) {
      player.kickPlayer(CC.RED.bold() + "Server shutting down");
    }
  }

  @Override
  public MongoDatabase getMongoDatabase() {
    return this.mongoDatabase;
  }

  @Override
  public RedisWrapper getRedisWrapper() {
    return this.redisWrapper;
  }

  @Override
  public CommandManager getCommandManager() {
    return this.commandManager;
  }

  @Override
  public PermissionRegistry getPermissionRegistry() {
    return this.permissionRegistry;
  }

  @Override
  public RankRegistry getRankRegistry() {
    return this.rankRegistry;
  }

  @Override
  public ObjectMapper getObjectMapper() {
    return this.objectMapper;
  }

  @Override
  public ErisPlayerManager getErisPlayerManager() {
    return this.erisPlayerManager;
  }

  @Override
  public OfflineDataManager getOfflineDataManager() {
    return this.erisPlayerManager.getOfflineDataManager();
  }

  @Override
  public ScoreboardController getScoreboardController() {
    return this.scoreboardController;
  }

  @Override
  public ChatController getChatController() {
    return this.chatController;
  }

  @Override
  public TablistController getTablistController() {
    return this.tablistController;
  }

  /**
   * @return ugh. but needed for static registry accesses. tis what tis. avoid when possible! not
   * because it makes any difference but because i don't like it. and i don't like you. go away.
   * stop judging me. im going crazy. it's 02:01. the sweete scape by gwen stefani and akon is
   * playing. joe parsons is based. - alfie 25/7/21 fuck off >:(
   */
  @Deprecated
  public static ErisBukkitCommonsPlugin getInstance() {
    return INSTANCE;
  }

}

package gg.eris.commons.bukkit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.ReadConcern;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoDatabase;
import gg.eris.commons.bukkit.command.CommandManager;
import gg.eris.commons.bukkit.impl.command.CommandManagerImpl;
import gg.eris.commons.bukkit.impl.menu.MenuListener;
import gg.eris.commons.bukkit.impl.player.ErisPlayerManagerImpl;
import gg.eris.commons.bukkit.permission.PermissionRegistry;
import gg.eris.commons.bukkit.player.DefaultErisPlayerSerializer;
import gg.eris.commons.bukkit.player.ErisPlayerManager;
import gg.eris.commons.bukkit.player.ErisPlayerSerializer;
import gg.eris.commons.bukkit.rank.RankRegistry;
import gg.eris.commons.core.database.MongoCredentials;
import gg.eris.commons.core.database.MongoDbProvider;
import gg.eris.commons.core.redis.RedisWrapper;
import gg.eris.commons.core.util.Validate;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class ErisBukkitCommonsPlugin extends JavaPlugin implements ErisBukkitCommons,
    Listener {

  private MongoDatabase mongoDatabase;
  private RedisWrapper redisWrapper;

  private CommandManager commandManager;
  private PermissionRegistry permissionRegistry;
  private RankRegistry rankRegistry;
  private ObjectMapper objectMapper;

  private ErisPlayerManager erisPlayerManager;
  private ErisPlayerSerializer<?> erisPlayerProvider;
  private boolean erisPlayerProviderSet;

  @Override
  public void onEnable() {
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
        config.getString("redis.username"),
        config.getString("redis.password"),
        config.getString("redis.hostname"),
        config.getInt("redis.port")
    );

    this.commandManager = new CommandManagerImpl();
    this.permissionRegistry = new PermissionRegistry();
    this.rankRegistry = new RankRegistry();
    this.objectMapper = new ObjectMapper();
    this.erisPlayerManager = new ErisPlayerManagerImpl(this);
    this.erisPlayerProviderSet = false;

    PluginManager pluginManager = Bukkit.getPluginManager();
    pluginManager.registerEvents(new MenuListener(this), this);

    // Register service
    ServicesManager servicesManager = Bukkit.getServicesManager();
    servicesManager.register(ErisBukkitCommons.class, this, this, ServicePriority.Highest);

    // Setting the player provider if none has been set by any plugin
    Bukkit.getScheduler().runTask(this, () -> {
      if (!this.erisPlayerProviderSet) {
        setErisPlayerProvider(new DefaultErisPlayerSerializer());
      }
    });
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
  public ErisPlayerSerializer<?> getErisPlayerSerializer() {
    return this.erisPlayerProvider;
  }

  @Override
  public synchronized void setErisPlayerProvider(ErisPlayerSerializer<?> erisPlayerProvider) {
    Validate.isTrue(!this.erisPlayerProviderSet, "eris player provider has already been set");
    this.erisPlayerProvider = erisPlayerProvider;
    this.erisPlayerProviderSet = true;
    ((ErisPlayerManagerImpl) this.erisPlayerManager).setupCollection();
  }
}

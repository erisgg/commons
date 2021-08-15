package gg.eris.commons.bukkit.impl.player;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.google.common.collect.Maps;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.UpdateOptions;
import gg.eris.commons.bukkit.ErisBukkitCommonsPlugin;
import gg.eris.commons.bukkit.player.ErisPlayer;
import gg.eris.commons.bukkit.player.ErisPlayerManager;
import gg.eris.commons.bukkit.player.ErisPlayerSerializer;
import gg.eris.commons.bukkit.player.OfflineDataManager;
import gg.eris.commons.bukkit.player.nickname.PlayerNicknamePipeline;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.core.util.Validate;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class ErisPlayerManagerImpl implements ErisPlayerManager {

  private final static ObjectReader NODE_READER = new ObjectMapper().reader(JsonNode.class);

  private final ErisBukkitCommonsPlugin plugin;

  private final OfflineDataManager offlineDataManager;

  private ErisPlayerSerializer<?> playerSerializer;

  private MongoCollection<Document> playerCollection;

  public ErisPlayerManagerImpl(ErisBukkitCommonsPlugin plugin) {
    this.plugin = plugin;
    setupCollection();
    this.offlineDataManager = new OfflineDataManagerImpl(plugin);
    this.players = Maps.newConcurrentMap();
    Bukkit.getPluginManager().registerEvents(new ErisPlayerManagerListener(plugin, this), plugin);
  }

  private final Map<UUID, ErisPlayer> players;

  protected ErisPlayer loadPlayer(UUID uuid) {
    // Finding all player documents with the UUID
    Document document = this.playerCollection
        .find(Filters.eq("uuid", uuid.toString()))
        .first();

    JsonNode node = null;
    if (document != null) {
      try {
        node = NODE_READER.readTree(document.toJson());
      } catch (JsonProcessingException err) {
        err.printStackTrace();
      }
    }

    ErisPlayer player = null;
    if (node != null) {
      player = this.playerSerializer.deserializePlayer(node);
    }

    if (player != null) {
      this.players.put(uuid, player);
      return player;
    } else {
      return null;
    }
  }

  protected void unloadPlayer(UUID uuid) {
    ErisPlayer player = this.players.remove(uuid);
    if (player != null) {
      savePlayer(player);
    } else {
      Bukkit.getLogger().warning("Player with UUID " + uuid.toString()
          + " had no ErisPlayer loaded and has disconnected without any data save.");
    }
  }

  protected void savePlayer(ErisPlayer player) {
    JsonNode data = this.playerSerializer.serializePlayer(player);
    Document document = new Document("$set", Document.parse(data.toString()));
    this.playerCollection.updateOne(
        Filters.eq("uuid", player.getUniqueId().toString()),
        document,
        new UpdateOptions().upsert(true)
    );
    PlayerNicknamePipeline.saveNickname(player);
  }

  protected void createAndLoadNewPlayer(Player player) {
    ErisPlayer newPlayer;
    this.players.put(
        player.getUniqueId(),
        newPlayer = this.playerSerializer.newPlayer(player)
    );
    Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> savePlayer(newPlayer));
  }

  protected void setupCollection() {
    this.playerCollection = this.plugin.getMongoDatabase()
        .getCollection("players", Document.class);

    this.playerCollection.createIndex(Indexes.hashed("uuid"));
    this.playerCollection.createIndex(Indexes.hashed("name"));
    this.playerCollection.createIndex(Indexes.hashed("ip_address"));
  }

  protected void updateFromHandleOnJoin(Player player) {
    ErisPlayer erisPlayer = this.players.get(player.getUniqueId());
    if (erisPlayer == null) {
      player.kickPlayer(CC.GOLD.bold() + "(!) " + CC.GOLD + "Something went wrong. Please rejoin.");
      return;
    }
    erisPlayer.updateFromHandle();
  }

  @Override
  public <T extends ErisPlayer> T getPlayer(UUID uuid) {
    return (T) this.players.get(uuid);
  }

  @Override
  public <T extends ErisPlayer> Collection<T> getPlayers() {
    return this.players.values().stream().map(player -> (T) player)
        .collect(Collectors.toList());
  }

  @Override
  public <T extends ErisPlayer> ErisPlayerSerializer<T> getPlayerSerializer() {
    return (ErisPlayerSerializer<T>) this.playerSerializer;
  }

  @Override
  public OfflineDataManager getOfflineDataManager() {
    return this.offlineDataManager;
  }

  @Override
  public synchronized <T extends ErisPlayer> void setPlayerSerializer(
      ErisPlayerSerializer<T> serializer) {
    Validate.isTrue(!isPlayerSerializerSet(), "eris player provider has already been set");
    this.playerSerializer = serializer;
  }

  public boolean isPlayerSerializerSet() {
    return this.playerSerializer != null;
  }

}

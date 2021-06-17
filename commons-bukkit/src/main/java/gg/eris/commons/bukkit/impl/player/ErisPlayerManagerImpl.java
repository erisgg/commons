package gg.eris.commons.bukkit.impl.player;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Maps;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.UpdateOptions;
import gg.eris.commons.bukkit.ErisBukkitCommonsPlugin;
import gg.eris.commons.bukkit.player.ErisPlayer;
import gg.eris.commons.bukkit.player.ErisPlayerManager;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class ErisPlayerManagerImpl implements ErisPlayerManager {

  private final ErisBukkitCommonsPlugin plugin;

  private MongoCollection<ErisPlayer> playerCollection;

  public ErisPlayerManagerImpl(ErisBukkitCommonsPlugin plugin) {
    this.plugin = plugin;
    this.players = Maps.newConcurrentMap();
    Bukkit.getPluginManager().registerEvents(new ErisPlayerManagerListener(plugin, this), plugin);
  }

  private final Map<UUID, ErisPlayer> players;

  protected void loadPlayer(UUID uuid) {
    // Finding all player documents with the UUID
    ErisPlayer player = this.playerCollection
        .find(Filters.eq("uuid", uuid.toString())).first();

    if (player != null) {
      this.players.put(uuid, player);
    }
  }

  protected void unloadPlayer(UUID uuid) {
    ErisPlayer player = this.players.remove(uuid);
    if (player != null) {
      JsonNode data = this.plugin.getErisPlayerSerializer().toNode(player);
      Document document = Document.parse(data.toString());
      this.playerCollection.updateOne(
          Filters.eq("uuid", uuid.toString()),
          document,
          new UpdateOptions().upsert(true)
      );
    }
  }

  protected void createNewPlayer(Player player) {
    this.players.put(
        player.getUniqueId(),
        this.plugin.getErisPlayerSerializer().newPlayer(player)
    );
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

  public void setupCollection() {
    this.playerCollection = this.plugin.getMongoDatabase()
        .getCollection("players", ErisPlayer.class);

    this.playerCollection.createIndex(Indexes.hashed("uuid"),
        new IndexOptions().unique(true));
    this.playerCollection.createIndex(Indexes.hashed("name"));
  }

}

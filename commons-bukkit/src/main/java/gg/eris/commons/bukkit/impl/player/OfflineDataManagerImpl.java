package gg.eris.commons.bukkit.impl.player;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.UpdateOptions;
import gg.eris.commons.bukkit.ErisBukkitCommonsPlugin;
import gg.eris.commons.bukkit.player.ErisPlayer;
import gg.eris.commons.bukkit.player.OfflineDataManager;
import gg.eris.commons.bukkit.rank.Rank;
import gg.eris.commons.bukkit.rank.RankRegistry;
import gg.eris.commons.core.identifier.Identifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import org.bson.Document;

public final class OfflineDataManagerImpl implements OfflineDataManager {

  private static final UpdateOptions UPSERT = new UpdateOptions().upsert(true);
  private final static ObjectReader NODE_READER = new ObjectMapper().reader(JsonNode.class);

  private final MongoCollection<Document> playerCollection;
  private final RankRegistry rankRegistry;

  public OfflineDataManagerImpl(ErisBukkitCommonsPlugin plugin) {
    this.playerCollection = plugin.getMongoDatabase().getCollection("players", Document.class);
    this.rankRegistry = plugin.getRankRegistry();
  }

  @Override
  public UUID getUuid(String name) {
    Document document = this.playerCollection
        .find(Filters.eq("name_lower", name.toLowerCase(Locale.ROOT)))
        .sort(Sorts.descending("last_login"))
        .first();

    if (document == null) {
      return null;
    } else {
      return UUID.fromString(document.getString("uuid"));
    }
  }

  @Override
  public JsonNode getRaw(UUID uuid) {
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
    return node;
  }

  @Override
  public boolean addRank(UUID uuid, Rank rank) {
    return this.playerCollection.updateOne(
        Filters.eq("uuid", uuid.toString()),
        new Document("$addToSet", new Document().append("ranks", rank.getIdentifier().getValue())),
        UPSERT
    ).getModifiedCount() > 0;
  }

  @Override
  public boolean removeRank(UUID uuid, Rank rank) {
    return this.playerCollection.updateOne(
        Filters.eq("uuid", uuid.toString()),
        new Document("$pull", new Document().append("ranks", rank.getIdentifier().getValue())),
        UPSERT
    ).getModifiedCount() > 0;
  }

  @Override
  public boolean setRank(UUID uuid, Rank rank) {
    return this.playerCollection.updateOne(
        Filters.eq("uuid", uuid.toString()),
        new Document("$set", new Document()
            .append("ranks", List.of(rank.getIdentifier().getValue()))),
        UPSERT
    ).getModifiedCount() > 0;
  }

  @Override
  public List<Rank> getRanks(UUID uuid) {
    Document document = this.playerCollection.find(
        Filters.eq("uuid", uuid.toString())
    ).first();

    if (document == null) {
      return null;
    }

    List<String> rankList = document.getList("ranks", String.class, List.of());
    return rankList.stream()
        .map(this.rankRegistry::get)
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  @Override
  public boolean addPermission(UUID uuid, Identifier identifier) {
    return this.playerCollection.updateOne(
        Filters.eq("uuid", uuid.toString()),
        new Document("$addToSet", new Document("permissions", identifier.toString())),
        UPSERT
    ).getModifiedCount() > 0;
  }

  @Override
  public boolean removePermission(UUID uuid, Identifier identifier) {
    return this.playerCollection.updateOne(
        Filters.eq("uuid", uuid.toString()),
        new Document("$pull", new Document("permissions", identifier.toString())),
        UPSERT
    ).getModifiedCount() > 0;
  }

  public List<String> getRawPermissions(UUID uuid) {
    Document document = this.playerCollection.find(
        Filters.eq("uuid", uuid.toString())
    ).first();

    if (document == null) {
      return null;
    }

    return new ArrayList<>(document.getList("permissions", String.class, List.of()));
  }
}

package gg.eris.commons.bukkit.impl.player;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.UpdateOptions;
import gg.eris.commons.bukkit.ErisBukkitCommonsPlugin;
import gg.eris.commons.bukkit.permission.Permission;
import gg.eris.commons.bukkit.player.OfflineDataManager;
import gg.eris.commons.bukkit.rank.Rank;
import gg.eris.commons.bukkit.rank.RankRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import org.bson.Document;

public final class OfflineDataManagerImpl implements OfflineDataManager {

  private static final UpdateOptions UPSERT = new UpdateOptions().upsert(true);

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

    List<String> rankList = document.getList("ranks", String.class);
    return rankList.stream()
        .map(this.rankRegistry::get)
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  @Override
  public boolean addPermission(UUID uuid, Permission permission) {
    return this.playerCollection.updateOne(
        Filters.eq("uuid", uuid.toString()),
        new Document("$set", new Document()
            .append("$addToSet", new Document("permissions", permission.toString()))),
        UPSERT
    ).getModifiedCount() > 0;
  }

  @Override
  public boolean removePermission(UUID uuid, Permission permission) {
    return this.playerCollection.updateOne(
        Filters.eq("uuid", uuid.toString()),
        new Document("$set", new Document()
            .append("$pull", new Document("permissions", permission.toString()))),
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

    return new ArrayList<>(document.getList("permissions", String.class));
  }
}

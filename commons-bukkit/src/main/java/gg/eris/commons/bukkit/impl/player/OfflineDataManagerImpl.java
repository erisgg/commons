package gg.eris.commons.bukkit.impl.player;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import gg.eris.commons.bukkit.ErisBukkitCommonsPlugin;
import gg.eris.commons.bukkit.permission.Permission;
import gg.eris.commons.bukkit.player.OfflineDataManager;
import gg.eris.commons.bukkit.rank.Rank;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.bson.Document;

@RequiredArgsConstructor
public final class OfflineDataManagerImpl implements OfflineDataManager {

  private final ErisBukkitCommonsPlugin plugin;

  @Override
  public UUID getUuid(String name) {
    Document document = this.plugin.getMongoDatabase().getCollection("players", Document.class)
        .find(Filters.eq("name", name))
        .sort(Sorts.descending("last_login"))
        .first();

    if (document == null) {
      return null;
    } else {
      return UUID.fromString(document.getString("uuid"));
    }
  }

  @Override
  public void addRank(UUID uuid, Rank rank) {

  }

  @Override
  public void removeRank(UUID uuid, Rank rank) {

  }

  @Override
  public void addPermission(UUID uuid, Permission permission) {

  }

  @Override
  public void removePermission(UUID uuid, Permission permission) {

  }
}

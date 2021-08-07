package gg.eris.commons.bukkit.player;

import gg.eris.commons.bukkit.permission.Permission;
import gg.eris.commons.bukkit.rank.Rank;
import java.util.UUID;

public interface OfflineDataManager {

  UUID getUuid(String name);

  void addRank(UUID uuid, Rank rank);

  void removeRank(UUID uuid, Rank rank);

  void addPermission(UUID uuid, Permission permission);

  void removePermission(UUID uuid, Permission permission);

}

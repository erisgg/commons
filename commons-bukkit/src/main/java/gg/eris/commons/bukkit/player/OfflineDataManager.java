package gg.eris.commons.bukkit.player;

import gg.eris.commons.bukkit.permission.Permission;
import gg.eris.commons.bukkit.rank.Rank;
import java.util.List;
import java.util.UUID;

public interface OfflineDataManager {

  UUID getUuid(String name);

  boolean addRank(UUID uuid, Rank rank);

  boolean removeRank(UUID uuid, Rank rank);

  boolean setRank(UUID uuid, Rank rank);

  List<Rank> getRanks(UUID uuid);

  boolean addPermission(UUID uuid, Permission permission);

  boolean removePermission(UUID uuid, Permission permission);

  List<String> getRawPermissions(UUID uuid);

}

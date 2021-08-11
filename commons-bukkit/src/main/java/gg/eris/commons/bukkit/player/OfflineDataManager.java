package gg.eris.commons.bukkit.player;

import com.fasterxml.jackson.databind.JsonNode;
import gg.eris.commons.bukkit.rank.Rank;
import gg.eris.commons.core.identifier.Identifier;
import java.util.List;
import java.util.UUID;

public interface OfflineDataManager {

  UUID getUuid(String name);

  JsonNode getRaw(UUID uuid);

  boolean addRank(UUID uuid, Rank rank);

  boolean removeRank(UUID uuid, Rank rank);

  boolean setRank(UUID uuid, Rank rank);

  List<Rank> getRanks(UUID uuid);

  boolean addPermission(UUID uuid, Identifier identifier);

  boolean removePermission(UUID uuid, Identifier identifier);

  List<String> getRawPermissions(UUID uuid);

}

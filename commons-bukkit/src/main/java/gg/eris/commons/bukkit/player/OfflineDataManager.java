package gg.eris.commons.bukkit.player;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import gg.eris.commons.bukkit.player.punishment.Punishment;
import gg.eris.commons.bukkit.player.punishment.PunishmentProfile;
import gg.eris.commons.bukkit.rank.Rank;
import gg.eris.commons.core.identifier.Identifier;
import java.util.List;
import java.util.UUID;
import org.bson.Document;

public interface OfflineDataManager {

  UUID getUuid(String name);

  JsonNode getRaw(UUID uuid);

  List<JsonNode> performRawQuery(Document document) throws JsonProcessingException;

  boolean addRank(UUID uuid, Rank rank);

  boolean removeRank(UUID uuid, Rank rank);

  boolean setRank(UUID uuid, Rank rank);

  List<Rank> getRanks(UUID uuid);

  boolean addPermission(UUID uuid, Identifier identifier);

  boolean removePermission(UUID uuid, Identifier identifier);

  List<String> getRawPermissions(UUID uuid);

  boolean addPunishment(UUID uuid, Punishment punishment);

  boolean addUnmute(UUID uuid);

  boolean addUnban(UUID uuid);

  PunishmentProfile getPunishmentProfile(UUID uuid);

}

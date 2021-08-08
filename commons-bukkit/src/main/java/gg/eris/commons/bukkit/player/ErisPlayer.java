package gg.eris.commons.bukkit.player;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.collect.ImmutableList;
import gg.eris.commons.bukkit.ErisBukkitCommonsPlugin;
import gg.eris.commons.bukkit.permission.Permission;
import gg.eris.commons.bukkit.rank.Rank;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.commons.core.json.JsonUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * The Eris wrapper around the {@link Player} class. Can be extended to add and load additional
 * fields for one plugin per server. This can be changed by setting the {@link ErisPlayerSerializer}
 * in the {@link gg.eris.commons.bukkit.ErisBukkitCommons} service. Unmarked or fields annotated
 * with the appropriate Jackson Annotation annotations will be serialized. Transient fields will be
 * ignored. All of the serialization is done through the {@link gg.eris.commons.bukkit.ErisBukkitCommons}
 * {@link com.fasterxml.jackson.databind.ObjectMapper} and custom serializations and deserialization
 * can be registered there.
 */
public class ErisPlayer implements Serializable {

  protected final UUID uuid;

  @Getter
  protected String name;

  @Getter
  protected final List<String> nameHistory;

  @Getter
  protected final long firstLogin;

  @Getter
  protected long lastLogin;

  @Getter
  protected List<Rank> ranks;

  @Getter
  protected final List<Permission> permissions;

  public ErisPlayer(DefaultData data) {
    this.uuid = data.uuid;
    this.name = data.name;
    this.nameHistory = data.nameHistory;
    this.firstLogin = data.firstLogin;
    this.lastLogin = data.lastLogin;
    this.ranks = data.ranks;
    this.permissions = data.permissions;
    Collections.sort(this.ranks);
  }

  public final Player getHandle() {
    return Bukkit.getPlayer(this.uuid);
  }

  public final UUID getUniqueId() {
    return this.uuid;
  }

  public final void addRank(Rank rank) {
    this.ranks.add(rank);
    Collections.sort(this.ranks);
  }

  public final void removeRank(Rank rank) {
    this.ranks.remove(rank);
    Collections.sort(this.ranks);
  }

  public final void setRank(Rank rank) {
    this.ranks.clear();
    addRank(rank);
  }

  public final Rank getPriorityRank() {
    return this.ranks.get(0);
  }

  public final boolean isOnline() {
    Player player = getHandle();
    return player != null && player.isOnline();
  }

  public void updateFromHandle() {
    Player player = getHandle();
    this.lastLogin = System.currentTimeMillis();

    String lastName = this.nameHistory.get(this.nameHistory.size() - 1);
    if (!player.getName().equals(lastName)) {
      this.nameHistory.add(player.getName());
      this.name = player.getName();
    }
  }

  @Getter
  public static class DefaultData {

    private static final List<Rank> DEFAULT_RANK =
        List.of(ErisBukkitCommonsPlugin.getInstance().getRankRegistry().DEFAULT);

    private static final ObjectReader STRING_READER = new ObjectMapper()
        .readerFor(new TypeReference<List<String>>() {
        });

    private final UUID uuid;
    private final String name;
    private final List<String> nameHistory;
    private final long firstLogin;
    private final long lastLogin;
    private final List<Rank> ranks;
    private final List<Permission> permissions;

    private DefaultData(UUID uuid, String name, List<String> nameHistory, long firstLogin,
        long lastLogin, List<Rank> ranks, List<Permission> permissions) {
      this.uuid = uuid;
      this.name = name;
      this.nameHistory = ImmutableList.copyOf(nameHistory);
      this.firstLogin = firstLogin;
      this.lastLogin = lastLogin;
      this.ranks = ranks;
      this.permissions = ImmutableList.copyOf(permissions);
    }

    public static DefaultData fromNode(JsonNode node) {
      try {
        ArrayNode ranksNode = (ArrayNode) node.get("ranks");
        List<Rank> ranks;
        if (ranksNode == null) {
          ranks = new ArrayList<>(DEFAULT_RANK);
        } else {
          ranks = JsonUtil.fromStringArray(ranksNode).stream()
              .map(ErisBukkitCommonsPlugin.getInstance().getRankRegistry()::get)
              .collect(Collectors.toList());
        }

        ArrayNode permissionsNode = (ArrayNode) node.get("permissions");
        List<Permission> permissions;
        if (permissionsNode == null) {
          permissions = List.of();
        } else {
          permissions = JsonUtil.fromStringArray(permissionsNode).stream()
              .map(string -> ErisBukkitCommonsPlugin.getInstance().getPermissionRegistry()
                  .get(Identifier.fromString(string)))
              .collect(Collectors.toList());
        }

        return of(
            UUID.fromString(node.get("uuid").asText()),
            node.get("name").asText(),
            STRING_READER.readValue(node.get("name_history")),
            node.get("first_login").asLong(),
            node.get("last_login").asLong(),
            ranks,
            permissions
        );
      } catch (IOException err) {
        return null;
      }
    }

    public static DefaultData of(UUID uuid, String name, List<String> nameHistory, long firstLogin,
        long lastLogin, List<Rank> ranks, List<Permission> permissions) {
      return new DefaultData(
          uuid,
          name,
          nameHistory,
          firstLogin,
          lastLogin,
          ranks,
          permissions
      );
    }

    public static DefaultData newData(Player player) {
      long time = System.currentTimeMillis();
      return DefaultData.of(
          player.getUniqueId(),
          player.getName(),
          List.of(player.getName()),
          time,
          time,
          DEFAULT_RANK,
          List.of()
      );
    }

  }

}

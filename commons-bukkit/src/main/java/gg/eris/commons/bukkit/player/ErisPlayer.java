package gg.eris.commons.bukkit.player;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.google.common.collect.ImmutableList;
import gg.eris.commons.bukkit.ErisBukkitCommonsPlugin;
import gg.eris.commons.bukkit.permission.Permission;
import gg.eris.commons.bukkit.rank.Rank;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
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
  protected Rank rank;

  @Getter
  protected final List<Permission> permissions;

  public ErisPlayer(DefaultData data) {
    this.uuid = data.uuid;
    this.name = data.name;
    this.nameHistory = data.nameHistory;
    this.firstLogin = data.firstLogin;
    this.lastLogin = data.lastLogin;
    this.rank = data.rank;
    this.permissions = data.permissions;
  }

  public final Player getHandle() {
    return Bukkit.getPlayer(this.uuid);
  }

  public final UUID getUniqueId() {
    return this.uuid;
  }

  public void setRank(Rank rank) {
    this.rank = rank;
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

    private final static ObjectReader STRING_READER = new ObjectMapper()
        .readerFor(new TypeReference<List<String>>() {
        });

    private final UUID uuid;
    private final String name;
    private final List<String> nameHistory;
    private final long firstLogin;
    private final long lastLogin;
    private final Rank rank;
    private final List<Permission> permissions;

    private DefaultData(UUID uuid, String name, List<String> nameHistory, long firstLogin,
        long lastLogin, Rank rank, List<Permission> permissions) {
      this.uuid = uuid;
      this.name = name;
      this.nameHistory = ImmutableList.copyOf(nameHistory);
      this.firstLogin = firstLogin;
      this.lastLogin = lastLogin;
      this.rank = rank;
      this.permissions = ImmutableList.copyOf(permissions);
    }

    public static DefaultData fromNode(JsonNode node) {
      try {
        return of(
            UUID.fromString(node.get("uuid").asText()),
            node.get("name").asText(),
            STRING_READER.readValue(node.get("name_history")),
            node.get("first_login").asLong(),
            node.get("last_login").asLong(),
            ErisBukkitCommonsPlugin.getInstance().getRankRegistry().get(node.get("rank").asText()),
            List.of()
        );
      } catch (IOException err) {
        return null;
      }
    }

    public static DefaultData of(UUID uuid, String name, List<String> nameHistory, long firstLogin,
        long lastLogin, Rank rank, List<Permission> permissions) {
      return new DefaultData(
          uuid,
          name,
          nameHistory,
          firstLogin,
          lastLogin,
          rank,
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
          ErisBukkitCommonsPlugin.getInstance().getRankRegistry().DEFAULT,
          List.of()
      );
    }

  }

}

package gg.eris.commons.bukkit.player;

import com.google.common.collect.ImmutableList;
import gg.eris.commons.bukkit.permission.Permission;
import gg.eris.commons.bukkit.rank.Rank;
import java.io.Serializable;
import java.util.ArrayList;
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
  protected final String name;

  @Getter
  protected final List<String> nameHistory;

  @Getter
  protected final long firstLogin;

  @Getter
  protected final long lastLogin;

  @Getter
  protected Rank rank;

  @Getter
  protected final List<Permission> permissions;

  public ErisPlayer(UUID uuid, String name, List<String> nameHistory, long firstLogin,
      long lastLogin, Rank rank, List<Permission> permissions) {
    this.uuid = uuid;
    this.name = name;
    this.nameHistory = ImmutableList.copyOf(nameHistory);
    this.firstLogin = firstLogin;
    this.lastLogin = lastLogin;
    this.rank = rank;
    this.permissions = new ArrayList<>(permissions);
  }

  public final Player getHandle() {
    return Bukkit.getPlayer(this.uuid);
  }

  public final UUID getUniqueId() {
    return this.uuid;
  }

  public final boolean isOnline() {
    Player player = getHandle();
    return player != null && player.isOnline();
  }

}

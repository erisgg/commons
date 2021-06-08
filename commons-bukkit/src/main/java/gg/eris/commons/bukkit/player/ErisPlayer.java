package gg.eris.commons.bukkit.player;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableSet;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * The Eris wrapper around the {@link Player} class. Can be extended to add and load additional
 * fields for one plugin per server. This can be changed by setting the
 * {@link ErisPlayerClassProvider} in the {@link gg.eris.commons.bukkit.ErisBukkitCommons} service.
 * Unmarked or fields annotated with the appropriate Jackson Annotation annotations will be
 * serialized. Transient fields will be ignored. All of the serialization is done through the
 * {@link gg.eris.commons.bukkit.ErisBukkitCommons}
 * {@link com.fasterxml.jackson.databind.ObjectMapper} and custom serializations and
 * deserialization can be registered there.
 */
public class ErisPlayer implements Serializable {

  @JsonProperty
  protected final UUID uuid;

  @Getter
  @JsonProperty
  protected final String name;

  @Getter
  @JsonProperty
  protected final Set<String> knownAliases;

  @Getter
  @JsonProperty
  protected final long firstLogin;

  @Getter
  @JsonProperty
  protected final long lastLogin;

  @Getter
  @JsonProperty
  protected final long lastLogout;

  public ErisPlayer(UUID uuid, String name, Set<String> knownAliases, long firstLogin,
      long lastLogin, long lastLogout) {
    this.uuid = uuid;
    this.name = name;
    this.knownAliases = ImmutableSet.copyOf(knownAliases);
    this.firstLogin = firstLogin;
    this.lastLogin = lastLogin;
    this.lastLogout = lastLogout;
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

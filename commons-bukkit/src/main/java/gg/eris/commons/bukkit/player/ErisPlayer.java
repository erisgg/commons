package gg.eris.commons.bukkit.player;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public abstract class ErisPlayer {

  @Getter
  protected final UUID uuid;

  @Getter
  protected final String name;

  @Getter
  protected final Set<String> knownAliases;

  @Getter
  protected final long firstLogin;

  @Getter
  protected final long lastLogin;

  @Getter
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

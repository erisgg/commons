package gg.eris.commons.bukkit.player;

import java.util.UUID;
import org.bukkit.entity.Player;

/**
 * Holds all the {@link ErisPlayer} instances.
 */
public interface ErisPlayerManager {

  /**
   * Gets an {@link ErisPlayer}
   *
   * @param uuid is the UUID of the player to get
   * @param <T> is the type of {@link ErisPlayer}
   * @return the {@link ErisPlayer} instance, null if not
   */
  <T extends ErisPlayer> T getPlayer(UUID uuid);

  default <T extends ErisPlayer> T getPlayer(Player player) {
    return getPlayer(player.getUniqueId());
  }

}

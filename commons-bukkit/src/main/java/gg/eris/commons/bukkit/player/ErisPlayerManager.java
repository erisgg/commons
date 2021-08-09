package gg.eris.commons.bukkit.player;

import java.util.Collection;
import java.util.UUID;
import org.bukkit.entity.Player;

/**
 * Holds all the {@link ErisPlayer} instances.
 */
public interface ErisPlayerManager {

  /**
   * @param <T> is the type of {@link ErisPlayer}
   * @return the {@link ErisPlayerSerializer} instance being used
   */
  <T extends ErisPlayer> ErisPlayerSerializer<T> getPlayerSerializer();

  /**
   * @param serializer is the {@link ErisPlayerSerializer} to set
   * @param <T>        is the type of {@link ErisPlayer}
   */
  <T extends ErisPlayer> void setPlayerSerializer(ErisPlayerSerializer<T> serializer);

  /**
   * Gets an {@link ErisPlayer}
   *
   * @param uuid is the UUID of the player to get
   * @param <T>  is the type of {@link ErisPlayer}
   * @return the {@link ErisPlayer} instance, null if not
   */
  <T extends ErisPlayer> T getPlayer(UUID uuid);

  /**
   * Gets all {@link ErisPlayer} on the server
   *
   * @param <T> is the type of {@link ErisPlayer}
   * @return an snapshot of the players at the time of the call
   */
  <T extends ErisPlayer> Collection<T> getPlayers();

  default <T extends ErisPlayer> T getPlayer(Player player) {
    return getPlayer(player.getUniqueId());
  }

  OfflineDataManager getOfflineDataManager();

}

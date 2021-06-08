package gg.eris.commons.bukkit;

import com.fasterxml.jackson.databind.ObjectMapper;
import gg.eris.commons.bukkit.command.CommandManager;
import gg.eris.commons.bukkit.permission.PermissionRegistry;
import gg.eris.commons.bukkit.player.ErisPlayerManager;
import gg.eris.commons.bukkit.player.ErisPlayerClassProvider;
import gg.eris.commons.bukkit.rank.RankRegistry;

/**
 *
 */
public interface ErisBukkitCommons {

  /**
   * Returns the server command manager instance
   *
   * @return the server's {@link CommandManager} instance
   */
  CommandManager getCommandManager();

  /**
   * Returns the server {@link PermissionRegistry}
   *
   * @return the server's {@link PermissionRegistry}
   */
  PermissionRegistry getPermissionRegistry();

  /**
   * Returns the server's {@link RankRegistry}
   *
   * @return the server's {@link RankRegistry}
   */
  RankRegistry getRankRegistry();

  /**
   * Returns the server's {@link ObjectMapper}
   *
   * @return the server's {@link ObjectMapper}
   */
  ObjectMapper getObjectMapper();

  /**
   * Returns the server's {@link ErisPlayerManager}
   *
   * @return the server's {@link ErisPlayerManager}
   */
  ErisPlayerManager getErisPlayerManager();

  /**
   * Returns the server's {@link ErisPlayerClassProvider}
   *
   * @return the server's {@link ErisPlayerClassProvider}
   */
  ErisPlayerClassProvider<?> getErisPlayerProvider();

  /**
   * Sets the server's {@link ErisPlayerClassProvider}. This can only be done once and will error if
   * attempted multiple times. This defines the type that all
   * {@link gg.eris.commons.bukkit.player.ErisPlayer} will be de-serialized and stored as in the
   * {@link ErisPlayerManager}.
   *
   * @param erisPlayerProvider is the {@link ErisPlayerClassProvider} to set
   */
  void setErisPlayerProvider(ErisPlayerClassProvider<?> erisPlayerProvider);

}

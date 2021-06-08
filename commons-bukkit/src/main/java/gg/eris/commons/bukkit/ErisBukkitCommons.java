package gg.eris.commons.bukkit;

import gg.eris.commons.bukkit.command.CommandManager;
import gg.eris.commons.bukkit.permission.PermissionRegistry;
import gg.eris.commons.bukkit.rank.RankRegistry;
import gg.eris.commons.core.redis.RedisWrapper;

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
   * Returns the server {@link RankRegistry}
   *
   * @return the server's {@link RankRegistry}
   */
  RankRegistry getRankRegistry();

}

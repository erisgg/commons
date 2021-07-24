package gg.eris.commons.bukkit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoDatabase;
import gg.eris.commons.bukkit.command.CommandManager;
import gg.eris.commons.bukkit.permission.PermissionRegistry;
import gg.eris.commons.bukkit.player.ErisPlayerManager;
import gg.eris.commons.bukkit.player.ErisPlayerSerializer;
import gg.eris.commons.bukkit.rank.RankRegistry;
import gg.eris.commons.bukkit.scoreboard.ScoreboardController;
import gg.eris.commons.core.redis.RedisWrapper;

/**
 * The commons service for all Eris plugins to use.
 */
public interface ErisBukkitCommons {

  /**
   * Returns the server {@link MongoDatabase} instance
   *
   * @return the server's {@link MongoDatabase} instance
   */
  MongoDatabase getMongoDatabase();

  /**
   * Returns the server {@link RedisWrapper} instance
   *
   * @return the server's {@link RedisWrapper} instance
   */
  RedisWrapper getRedisWrapper();

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
   * Returns the server's {@link ScoreboardController}
   *
   * @return the server's {@link ScoreboardController}
   */
  ScoreboardController getScoreboardController();

}

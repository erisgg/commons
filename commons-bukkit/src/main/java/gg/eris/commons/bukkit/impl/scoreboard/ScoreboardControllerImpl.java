package gg.eris.commons.bukkit.impl.scoreboard;

import com.google.common.collect.Maps;
import gg.eris.commons.bukkit.ErisBukkitCommonsPlugin;
import gg.eris.commons.bukkit.scoreboard.Scoreboard;
import gg.eris.commons.bukkit.scoreboard.ScoreboardController;
import gg.eris.commons.core.identifier.Identifier;
import java.util.Map;
import org.bukkit.Bukkit;

public final class ScoreboardControllerImpl implements ScoreboardController {

  private final Map<Identifier, Scoreboard> scoreboards;

  public ScoreboardControllerImpl(ErisBukkitCommonsPlugin plugin) {
    this.scoreboards = Maps.newHashMap();

    Bukkit.getScheduler().runTaskTimer(plugin, () -> {
      for (Scoreboard scoreboard : scoreboards.values()) {

      }
    }, 0L, 1L);
  }

  @Override
  public Scoreboard createScoreboard(Identifier identifier) {
    Scoreboard scoreboard = new ScoreboardImpl(this, identifier);
    if (!this.scoreboards.containsKey(identifier)) {
      this.scoreboards.put(identifier, scoreboard);
      return scoreboard;
    }
    return null;
  }

  @Override
  public Scoreboard getScoreboard(Identifier identifier) {
    return this.scoreboards.get(identifier);
  }

  @Override
  public Scoreboard removeScoreboard(Identifier identifier) {
    return this.scoreboards.remove(identifier);
  }
}

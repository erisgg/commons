package gg.eris.commons.bukkit.impl.scoreboard;

import com.google.common.collect.Maps;
import gg.eris.commons.bukkit.ErisBukkitCommonsPlugin;
import gg.eris.commons.bukkit.scoreboard.Scoreboard;
import gg.eris.commons.bukkit.scoreboard.ScoreboardController;
import gg.eris.commons.core.identifier.Identifier;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

public final class ScoreboardControllerImpl implements ScoreboardController {

  private final Map<Identifier, ScoreboardImpl> scoreboards;

  public ScoreboardControllerImpl(ErisBukkitCommonsPlugin plugin) {
    this.scoreboards = Maps.newHashMap();

    Bukkit.getScheduler().runTaskTimer(plugin, () -> {
      for (ScoreboardImpl scoreboard : scoreboards.values()) {
        org.bukkit.scoreboard.Scoreboard handle = scoreboard.getHandle();
        Objective objective = handle.getObjective(DisplaySlot.SIDEBAR);

        for (ScoreboardEntry entry : scoreboard.getRemovedEntries()) {
          handle.resetScores(entry.getName());
        }

        for (ScoreboardEntry entry : scoreboard.getEntries().values()) {
          String value = entry.getValueSupplier().get();
          if (value.length() > 16) {
            String substring = value.substring(0, 16);
            entry.getTeam().setPrefix(entry.getValueSupplier().get());
            value = ChatColor.getLastColors(substring) + value.substring(16);
            entry.getTeam().setSuffix(value);
          } else {
            entry.getTeam().setPrefix(entry.getValueSupplier().get());
          }

          objective.getScore(entry.getName()).setScore(entry.getIndex());
        }

        if (scoreboard.hasNameChanged()) {
          handle.getObjective(DisplaySlot.SIDEBAR).setDisplayName(scoreboard.getDisplayName());
        }

        if (!scoreboard.getAddedPlayers().isEmpty()) {
          for (UUID uuid : scoreboard.getAddedPlayers()) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
              player.setScoreboard(handle);
            }
          }
        }

        if (!scoreboard.getRemovedPlayers().isEmpty()) {
          for (UUID uuid : scoreboard.getRemovedPlayers()) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
              if (player.getScoreboard() == handle) {
                player.setScoreboard(null);
              }
            }
          }
        }

        scoreboard.clean();
      }
    }, 0L, 1L);
  }

  @Override
  public Scoreboard createScoreboard(Identifier identifier, String displayName) {
    if (!this.scoreboards.containsKey(identifier)) {
      ScoreboardImpl scoreboard = new ScoreboardImpl(identifier, displayName);
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

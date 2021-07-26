package gg.eris.commons.bukkit.impl.scoreboard;

import com.google.common.collect.Maps;
import gg.eris.commons.bukkit.ErisBukkitCommonsPlugin;
import gg.eris.commons.bukkit.player.ErisPlayerManager;
import gg.eris.commons.bukkit.scoreboard.CommonsScoreboard;
import gg.eris.commons.bukkit.scoreboard.ScoreboardController;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.commons.core.util.Validate;
import java.util.Map;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

public final class ScoreboardControllerImpl implements ScoreboardController {

  @Getter(AccessLevel.PROTECTED)
  private final Map<Identifier, CommonsScoreboardImpl> scoreboards;
  @Getter(AccessLevel.PROTECTED)
  private final Map<UUID, Scoreboard> handles;
  @Getter(AccessLevel.PROTECTED)
  private final Map<UUID, CommonsScoreboardImpl> playerScoreboards;

  public ScoreboardControllerImpl(ErisBukkitCommonsPlugin plugin,
      ErisPlayerManager erisPlayerManager) {
    this.scoreboards = Maps.newHashMap();
    this.handles = Maps.newHashMap();
    this.playerScoreboards = Maps.newHashMap();

    new ScoreboardTask(this, erisPlayerManager).runTaskTimer(plugin, 0L, 1L);
  }

  @Override
  public CommonsScoreboard newScoreboard(Identifier identifier) {
    Validate.isTrue(!this.scoreboards.containsKey(identifier),
        "scoreboard with identifier='" + identifier.toString() + "' already exists");
    return new CommonsScoreboardImpl(identifier, this);
  }

  @Override
  public CommonsScoreboard getScoreboard(Identifier identifier) {
    return this.scoreboards.get(identifier);
  }

  @Override
  public void removeScoreboard(Identifier identifier) {
    CommonsScoreboard scoreboard = this.scoreboards.remove(identifier);
    if (scoreboard != null) {
      return; // TODO: cleanup
    }
  }

  protected void onJoin(Player player) {
    Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    player.setScoreboard(scoreboard);
    this.handles.put(player.getUniqueId(), scoreboard);
  }

  protected void onQuit(Player player) {
    this.handles.remove(player.getUniqueId());
    CommonsScoreboardImpl scoreboard = this.playerScoreboards.remove(player.getUniqueId());
    if (scoreboard != null) {
      scoreboard.removePlayer(player);
    }
  }

  protected void setScoreboard(Player player, CommonsScoreboardImpl scoreboard) {
    this.playerScoreboards.put(player.getUniqueId(), scoreboard);
  }

}

package gg.eris.commons.bukkit.scoreboard;

import gg.eris.commons.bukkit.impl.scoreboard.CommonsScoreboardImpl;
import gg.eris.commons.bukkit.player.ErisPlayer;
import gg.eris.commons.core.identifier.Identifier;
import java.util.function.BiFunction;
import org.bukkit.entity.Player;

public interface ScoreboardController {

  CommonsScoreboard newScoreboard(Identifier identifier);
  CommonsScoreboard getScoreboard(Identifier identifier);
  void removeScoreboard(Identifier identifier);

}

package gg.eris.commons.bukkit.scoreboard;

import gg.eris.commons.core.identifier.Identifier;

public interface ScoreboardController {

  CommonsScoreboard newScoreboard(Identifier identifier);

  CommonsScoreboard getScoreboard(Identifier identifier);

  void removeScoreboard(Identifier identifier);

  void removeScoreboard(CommonsScoreboard scoreboard);

}

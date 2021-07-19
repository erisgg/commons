package gg.eris.commons.bukkit.scoreboard;

import gg.eris.commons.core.identifier.Identifier;
import org.bukkit.scoreboard.Score;

public interface ScoreboardController {

  Scoreboard createScoreboard(Identifier identifier);

  Scoreboard getScoreboard(Identifier identifier);

  Scoreboard removeScoreboard(Identifier identifier);

}

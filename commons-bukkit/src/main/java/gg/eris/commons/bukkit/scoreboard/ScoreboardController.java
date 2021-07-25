package gg.eris.commons.bukkit.scoreboard;

import gg.eris.commons.core.identifier.Identifier;

public interface ScoreboardController {

  Scoreboard createScoreboard(Identifier identifier, String displayName);

  Scoreboard getScoreboard(Identifier identifier);

  Scoreboard removeScoreboard(Identifier identifier);

  Scoreboard removeScoreboard(Scoreboard scoreboard);

}

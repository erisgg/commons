package gg.eris.commons.bukkit.util;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public final class SidebarBuilder {

  private final Scoreboard scoreboard;
  private final Objective objective;

  private int nextLine;

  public SidebarBuilder(String title) {
    scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

    objective = scoreboard.registerNewObjective(title, "title");
    objective.setDisplaySlot(DisplaySlot.SIDEBAR);
  }

  public SidebarBuilder withEmptyLine() {
    // This ensures that given multiple blank scoreboard lines they don't have the same value.
    String empty = "";
    for (int i = 0; i < nextLine; i++) {
      empty += CC.RESET;
    }

    objective.getScore(empty).setScore(nextLine++);

    return this;
  }

  public SidebarBuilder withLine(String lineContents) {
    objective.getScore(lineContents).setScore(nextLine++);

    return this;
  }

  public Scoreboard build() {
    return scoreboard;
  }
}

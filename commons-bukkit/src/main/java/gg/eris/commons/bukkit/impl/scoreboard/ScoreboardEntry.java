package gg.eris.commons.bukkit.impl.scoreboard;

import java.util.UUID;
import java.util.function.Supplier;
import lombok.Getter;
import org.bukkit.scoreboard.Team;

@Getter
public class ScoreboardEntry {

  private final int index;
  private final Supplier<String> valueSupplier;
  private final Team team;
  private final String name;


  public ScoreboardEntry(ScoreboardImpl scoreboard, int index, Supplier<String> valueSupplier) {
    this.index = index;
    this.valueSupplier = valueSupplier;
    this.name = UUID.randomUUID().toString().replace("-", "").substring(0, 6);

    org.bukkit.scoreboard.Scoreboard handle = scoreboard.getHandle();
    this.team = handle.registerNewTeam(this.name);
    this.team.addEntry(this.name);

  }

}

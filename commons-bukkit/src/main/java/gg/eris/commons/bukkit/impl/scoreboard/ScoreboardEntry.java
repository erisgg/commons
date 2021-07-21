package gg.eris.commons.bukkit.impl.scoreboard;

import gg.eris.commons.core.util.RandomUtil;
import java.util.function.Supplier;
import lombok.Getter;
import org.bukkit.ChatColor;
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
    this.name =
        ChatColor.values()[RandomUtil.randomInt(0, ChatColor.values().length)] + "" + ChatColor
            .values()[RandomUtil.randomInt(0, ChatColor.values().length)];

    org.bukkit.scoreboard.Scoreboard handle = scoreboard.getHandle();
    this.team = handle.registerNewTeam(this.name);
    this.team.addEntry(this.name);

  }

}

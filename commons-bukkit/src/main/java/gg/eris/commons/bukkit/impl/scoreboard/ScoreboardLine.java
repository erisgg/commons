package gg.eris.commons.bukkit.impl.scoreboard;

import gg.eris.commons.bukkit.player.ErisPlayer;
import gg.eris.commons.core.util.Pair;
import java.util.function.BiFunction;
import lombok.Getter;
import org.bukkit.ChatColor;

@Getter
public final class ScoreboardLine {

  private final int index;
  private final String teamName;
  private final Pair<ChatColor, ChatColor> colorPair;
  private final BiFunction<ErisPlayer, Long, String> valueFunction;
  private final int updateTicks;

  private ScoreboardLine(int index, Pair<ChatColor, ChatColor> colorPair,
      BiFunction<ErisPlayer, Long, String> valueFunction, int updateTicks) {
    this.index = index;
    this.colorPair = colorPair;
    this.valueFunction = valueFunction;
    this.updateTicks = updateTicks;
    this.teamName =  index + getColorPair();
  }

  public static ScoreboardLine of(int index, Pair<ChatColor, ChatColor> colorPair,
      BiFunction<ErisPlayer, Long, String> valueFunction,
      int updateTicks) {
    return new ScoreboardLine(index, colorPair, valueFunction, updateTicks);
  }

  public String getColorPair() {
    return this.colorPair.getKey().toString() + this.colorPair.getValue();
  }



}

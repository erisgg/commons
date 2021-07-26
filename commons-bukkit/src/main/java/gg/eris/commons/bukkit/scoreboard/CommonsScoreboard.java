package gg.eris.commons.bukkit.scoreboard;

import gg.eris.commons.bukkit.player.ErisPlayer;
import java.util.function.BiFunction;
import org.bukkit.entity.Player;

public interface CommonsScoreboard {

  void addLine(BiFunction<ErisPlayer, Long, String> line);

  void addLine(BiFunction<ErisPlayer, Long, String> line, int updateTicks);

  void addPlayer(Player player);

  void removePlayer(Player player);

  boolean isPlayer(Player player);

}

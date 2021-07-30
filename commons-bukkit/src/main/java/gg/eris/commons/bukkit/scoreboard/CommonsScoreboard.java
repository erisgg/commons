package gg.eris.commons.bukkit.scoreboard;

import gg.eris.commons.bukkit.player.ErisPlayer;
import gg.eris.commons.core.identifier.Identifiable;
import java.util.Collection;
import java.util.UUID;
import java.util.function.BiFunction;
import org.bukkit.entity.Player;

public interface CommonsScoreboard extends Identifiable {

  void addLine(String line);

  void addLine(BiFunction<ErisPlayer, Long, String> line);

  void addLine(BiFunction<ErisPlayer, Long, String> line, int updateTicks);

  void setTitle(BiFunction<ErisPlayer, Long, String> title);

  void addPlayer(Player player);

  void removePlayer(Player player);

  boolean isPlayer(Player player);

  Collection<UUID> getPlayers();

  void removeAllPlayers();

}

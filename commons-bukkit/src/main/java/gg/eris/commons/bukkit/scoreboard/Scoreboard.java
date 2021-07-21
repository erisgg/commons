package gg.eris.commons.bukkit.scoreboard;

import gg.eris.commons.core.identifier.Identifiable;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;
import org.bukkit.entity.Player;

public interface Scoreboard extends Identifiable {

  int SCOREBOARD_LIMIT = 15;

  void addPlayerToScoreboard(Player player);

  void removePlayerFromScoreboard(Player player);

  void addLine(String line);

  void addLine(Supplier<String> line);

  void setLine(int index, String line);

  void setLine(int index, Supplier<String> line);

  void removeLine(int index);

  void setDisplayName(String displayName);

  String getDisplayName();

  Set<UUID> getPlayers();

}

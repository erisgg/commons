package gg.eris.commons.bukkit.scoreboard;

import gg.eris.commons.core.identifier.Identifiable;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.bukkit.entity.Player;

public interface Scoreboard extends Identifiable {

  void addPlayerToScoreboard(Player player);

  void removePlayerFromScoreboard(Player player);

  void addLine(String line);

  void removeLine(int line);

  List<String> getLines();

  Set<UUID> getPlayers();

}

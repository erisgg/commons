package gg.eris.commons.bukkit.impl.scoreboard;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import gg.eris.commons.bukkit.scoreboard.Scoreboard;
import gg.eris.commons.bukkit.scoreboard.ScoreboardController;
import gg.eris.commons.core.identifier.Identifier;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.bukkit.entity.Player;

public final class ScoreboardImpl implements Scoreboard {

  private final ScoreboardController scoreboardController;
  private final Identifier identifier;
  private final Set<UUID> players;
  private final List<String> lines;

  public ScoreboardImpl(ScoreboardController scoreboardController, Identifier identifier) {
    this.scoreboardController = scoreboardController;
    this.identifier = identifier;
    this.players = Sets.newHashSet();
    this.lines = Lists.newArrayList();
  }

  @Override
  public void addPlayerToScoreboard(Player player) {
    this.players.add(player.getUniqueId());
  }

  @Override
  public void removePlayerFromScoreboard(Player player) {
    this.players.remove(player.getUniqueId());
  }

  @Override
  public void addLine(String line) {
    this.lines.add(line);
  }

  @Override
  public void removeLine(int line) {
    this.lines.remove(line);
  }

  @Override
  public List<String> getLines() {
    return this.lines;
  }

  @Override
  public Set<UUID> getPlayers() {
    return ImmutableSet.copyOf(this.players);
  }

  @Override
  public Identifier getIdentifier() {
    return this.identifier;
  }

}

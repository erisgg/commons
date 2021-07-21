package gg.eris.commons.bukkit.impl.scoreboard;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import gg.eris.commons.bukkit.scoreboard.Scoreboard;
import gg.eris.commons.core.identifier.Identifier;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

public final class ScoreboardImpl implements Scoreboard {

  private final Identifier identifier;
  private final Set<UUID> players;
  @Getter
  private final Int2ObjectMap<ScoreboardEntry> entries;

  private String displayName;

  @Getter
  private final org.bukkit.scoreboard.Scoreboard handle;

  @Getter
  private final Set<UUID> removedPlayers;
  @Getter
  private final Set<UUID> addedPlayers;
  @Getter
  private final Set<ScoreboardEntry> removedEntries;
  private boolean nameChanged;

  public ScoreboardImpl(Identifier identifier, String displayName) {
    this.identifier = identifier;
    this.players = Sets.newHashSet();
    this.entries = new Int2ObjectArrayMap<>();
    this.displayName = displayName;

    this.handle = Bukkit.getScoreboardManager().getNewScoreboard();
    Objective objective = this.handle.registerNewObjective(identifier.toString(), "dummy");
    objective.setDisplayName(displayName);
    objective.setDisplaySlot(DisplaySlot.SIDEBAR);

    this.removedPlayers = Sets.newHashSet();
    this.addedPlayers = Sets.newHashSet();
    this.removedEntries = Sets.newHashSet();
    this.nameChanged = false;
  }

  @Override
  public void addPlayerToScoreboard(Player player) {
    this.players.add(player.getUniqueId());
    this.addedPlayers.add(player.getUniqueId());
  }

  @Override
  public void removePlayerFromScoreboard(Player player) {
    this.players.remove(player.getUniqueId());
    this.removedPlayers.add(player.getUniqueId());
  }

  @Override
  public void addLine(String line) {
    setLine(this.entries.size(), line);
  }

  @Override
  public void addLine(Supplier<String> line) {
    setLine(this.entries.size(), line);
  }

  public void setLine(int index, String line) {
    setLine(index, () -> line);
  }

  public void setLine(int index, Supplier<String> line) {
    this.entries.put(index, new ScoreboardEntry(this, SCOREBOARD_LIMIT - index, line));
  }

  @Override
  public void removeLine(int index) {
    ScoreboardEntry entry = this.entries.remove(SCOREBOARD_LIMIT - index);
    if (entry != null) {
      this.removedEntries.add(entry);
    }
  }

  @Override
  public Set<UUID> getPlayers() {
    return ImmutableSet.copyOf(this.players);
  }

  @Override
  public Identifier getIdentifier() {
    return this.identifier;
  }

  @Override
  public void setDisplayName(String displayName) {
    this.nameChanged = true;
    this.displayName = displayName;
  }

  @Override
  public String getDisplayName() {
    return this.displayName;
  }

  public boolean hasNameChanged() {
    return this.nameChanged;
  }

  public void clean() {
    this.removedPlayers.clear();
    this.addedPlayers.clear();
    this.removedEntries.clear();
    this.nameChanged = false;
  }

}

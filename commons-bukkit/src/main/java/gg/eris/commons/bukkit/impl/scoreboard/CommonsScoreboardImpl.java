package gg.eris.commons.bukkit.impl.scoreboard;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import gg.eris.commons.bukkit.player.ErisPlayer;
import gg.eris.commons.bukkit.scoreboard.CommonsScoreboard;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.commons.core.util.Pair;
import gg.eris.commons.core.util.Validate;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public final class CommonsScoreboardImpl implements CommonsScoreboard {

  private static final List<Pair<ChatColor, ChatColor>> COLOR_PAIRS;

  static {
    List<Pair<ChatColor, ChatColor>> pairs = Lists.newArrayList();

    for (ChatColor color : ChatColor.values()) {
      for (ChatColor color2 : ChatColor.values()) {
        pairs.add(Pair.of(color, color2));
      }
    }

    COLOR_PAIRS = pairs;
  }

  @Getter
  private final Identifier identifier;
  private final ScoreboardControllerImpl scoreboardController;
  private final Int2ObjectMap<ScoreboardLine> lines;
  private final Map<UUID, Long> players;
  private final String internalName;
  private final List<Pair<ChatColor, ChatColor>> pairs;

  private BiFunction<ErisPlayer, Long, String> title;

  private int highestIndex;

  public CommonsScoreboardImpl(Identifier identifier,
      ScoreboardControllerImpl scoreboardController) {
    this.identifier = identifier;

    String objectiveName = identifier.toString().replace(":", "-");
    if (objectiveName.length() > 16) {
      objectiveName = objectiveName.substring(0, 16);
    }
    this.internalName = objectiveName;

    this.scoreboardController = scoreboardController;
    this.lines = new Int2ObjectArrayMap<>();
    this.players = Maps.newHashMap();
    this.pairs = new ArrayList<>(COLOR_PAIRS);
    this.title = (p, i) -> null;
    this.highestIndex = 0;
  }

  public void addLine(ScoreboardLine line) {
    this.lines.put(line.getIndex(), line);
    if (line.getIndex() > this.highestIndex) {
      this.highestIndex = line.getIndex();
    }
  }

  @Override
  public void addLine(BiFunction<ErisPlayer, Long, String> line) {
    addLine(line, 0);
  }

  @Override
  public void addLine(BiFunction<ErisPlayer, Long, String> line, int updateTicks) {
    addLine(ScoreboardLine.of(this.highestIndex + 1, getPair(), line, updateTicks));
  }

  @Override
  public void addLine(String line) {
    addLine((e, t) -> line);
  }

  @Override
  public void addPlayer(Player player) {
    this.scoreboardController.setScoreboard(player, this);
    this.players.put(player.getUniqueId(), 0L);
  }

  @Override
  public void removePlayer(Player player) {
    Validate.isTrue(this.players.containsKey(player.getUniqueId()),
        "player is not a member of scoreboard");
    this.scoreboardController.setScoreboard(player, null);
    this.players.remove(player.getUniqueId());
  }

  @Override
  public boolean isPlayer(Player player) {
    return this.players.get(player.getUniqueId()) != null;
  }

  @Override
  public void setTitle(BiFunction<ErisPlayer, Long, String> title) {
    this.title = title;
  }

  protected void apply(ErisPlayer player, Scoreboard scoreboard) {
    Objective objective = scoreboard.getObjective(DisplaySlot.SIDEBAR);

    long tick = this.players.get(player.getUniqueId()) + 1;
    this.players.put(player.getUniqueId(), tick);

    if (objective != null && !objective.getName().equals(this.internalName)) {
      objective.unregister();
      newObjective(player, scoreboard);
    } else {
      if (objective == null) {
        newObjective(player, scoreboard);
      } else {
        updateObjective(player, scoreboard, tick);
      }
    }
  }

  private void newObjective(ErisPlayer player, Scoreboard scoreboard) {
    Objective objective = scoreboard.registerNewObjective(this.internalName, "dummy");
    objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    objective.setDisplayName(this.title.apply(player, 0L));

    for (ScoreboardLine line : this.lines.values()) {
      int index = this.highestIndex - line.getIndex();

      Team team = scoreboard.registerNewTeam(line.getTeamName());
      team.addEntry(line.getColorPair());

      String value = line.getValueFunction().apply(player, 0L);

      if (value.length() <= 16) {
        team.setPrefix(value);
        team.setSuffix("");
      } else {
        team.setPrefix(value.substring(0, 16));
        team.setSuffix(value.substring(16));
      }

      objective.getScore(line.getColorPair()).setScore(index);
    }

  }

  private void updateObjective(ErisPlayer player, Scoreboard scoreboard, long tick) {
    Objective objective = scoreboard.getObjective(DisplaySlot.SIDEBAR);
    objective.setDisplayName(this.title.apply(player, tick));

    for (ScoreboardLine line : this.lines.values()) {
      Team team = scoreboard.getTeam(line.getTeamName());

      if (line.getUpdateTicks() != 0 && tick % line.getUpdateTicks() == 0) {
        String value = line.getValueFunction().apply(player, tick);

        if (value.length() <= 16) {
          team.setPrefix(value);
          team.setSuffix("");
        } else {
          team.setPrefix(value.substring(0, 16));
          team.setSuffix(value.substring(16));
        }
      }
    }
  }

  private Pair<ChatColor, ChatColor> getPair() {
    return this.pairs.remove(0);
  }

}

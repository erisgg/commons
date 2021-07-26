package gg.eris.commons.bukkit.impl.tablist;

import gg.eris.commons.bukkit.player.ErisPlayer;
import gg.eris.commons.bukkit.player.ErisPlayerManager;
import gg.eris.commons.bukkit.rank.Rank;
import gg.eris.commons.bukkit.rank.RankRegistry;
import gg.eris.commons.bukkit.tablist.TablistController;
import gg.eris.commons.bukkit.text.TextController;
import java.util.function.Function;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

public final class TablistControllerImpl implements TablistController {

  private final ErisPlayerManager erisPlayerManager;
  private final RankRegistry rankRegistry;
  private String header;
  private String footer;
  private Function<ErisPlayer, String> displayNameFunction;

  public TablistControllerImpl(ErisPlayerManager erisPlayerManager, RankRegistry rankRegistry) {
    this.erisPlayerManager = erisPlayerManager;
    this.rankRegistry = rankRegistry;
  }

  @Override
  public void setDisplayNameFunction(Function<ErisPlayer, String> displayNameFunction) {
    this.displayNameFunction = displayNameFunction;
  }

  @Override
  public void setHeader(String header) {
    this.header = header;
    updateAll();
  }

  @Override
  public void setFooter(String footer) {
    this.footer = footer;
    updateAll();
  }

  private void updateAll() {
    for (Player player : Bukkit.getOnlinePlayers()) {
      update(player);
    }
  }

  public void onJoin(Player player) {
    update(player);
    initializeTeams(player);
    for (Player other : Bukkit.getOnlinePlayers()) {
      if (other != player) {
        addPlayer(other, player);
      }
    }
  }

  public void onQuit(Player player) {
    for (Player other : Bukkit.getOnlinePlayers()) {
      if (other != player) {
        removePlayer(other, player);
      }
    }
  }

  public void initializeTeams(Player player) {
    Scoreboard scoreboard = player.getScoreboard();

    for (Rank rank : this.rankRegistry.values()) {
      scoreboard.registerNewTeam("" + rank.getPriority());
    }

    for (Player handle : Bukkit.getOnlinePlayers()) {
      addPlayer(player, handle);
    }
  }

  public void addPlayer(Player player, Player addendum) {
    ErisPlayer erisPlayer = this.erisPlayerManager.getPlayer(addendum);
    player.getScoreboard().getTeam("" + erisPlayer.getRank().getPriority())
        .addEntry(addendum.getName());
  }

  public void removePlayer(Player player, Player removal) {
    ErisPlayer erisPlayer = this.erisPlayerManager.getPlayer(removal);
    player.getScoreboard().getTeam("" + erisPlayer.getRank().getPriority())
        .removeEntry(removal.getName());
  }

  public void update(Player player) {
    TablistUtil.sendHeaderFooter(
        player,
        TextController.parse(this.header),
        TextController.parse(this.footer)
    );

    player.setPlayerListName(displayNameFunction.apply(this.erisPlayerManager.getPlayer(player)));
  }

}

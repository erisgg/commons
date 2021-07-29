package gg.eris.commons.bukkit.impl.scoreboard;

import gg.eris.commons.bukkit.player.ErisPlayerManager;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

@RequiredArgsConstructor
public final class ScoreboardTask extends BukkitRunnable {

  private final ScoreboardControllerImpl controller;
  private final ErisPlayerManager erisPlayerManager;

  @Override
  public void run() {
    for (Map.Entry<UUID, CommonsScoreboardImpl> entry : this.controller.getPlayerScoreboards()
        .entrySet()) {
      Player player = Bukkit.getPlayer(entry.getKey());
      if (player == null) {
        continue;
      }

      Scoreboard handle = this.controller.getHandles().get(player.getUniqueId());

      System.out.println("updating for player " + player.getName());

      entry.getValue().apply(
          this.erisPlayerManager.getPlayer(player),
          handle
      );
    }

  }

}

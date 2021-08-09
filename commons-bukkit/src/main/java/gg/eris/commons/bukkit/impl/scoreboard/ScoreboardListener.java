package gg.eris.commons.bukkit.impl.scoreboard;

import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public final class ScoreboardListener implements Listener {

  private final ScoreboardControllerImpl scoreboardController;

  @EventHandler(priority = EventPriority.LOW)
  public void onPlayerJoin(PlayerJoinEvent event) {
    this.scoreboardController.onJoin(event.getPlayer());
  }

  @EventHandler(priority = EventPriority.LOW)
  public void onPlayerQuit(PlayerQuitEvent event) {
    this.scoreboardController.onQuit(event.getPlayer());
  }


}

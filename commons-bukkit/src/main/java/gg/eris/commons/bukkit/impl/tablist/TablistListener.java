package gg.eris.commons.bukkit.impl.tablist;

import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class TablistListener implements Listener {

  private final TablistControllerImpl tablistController;

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    this.tablistController.onJoin(event.getPlayer());
  }

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event) {
    this.tablistController.onQuit(event.getPlayer());
  }

}

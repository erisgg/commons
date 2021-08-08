package gg.eris.commons.bukkit.impl.tablist;

import gg.eris.commons.bukkit.ErisBukkitCommonsPlugin;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class TablistListener implements Listener {

  private final ErisBukkitCommonsPlugin plugin;
  private final TablistControllerImpl tablistController;

  @EventHandler(priority = EventPriority.LOW)
  public void onPlayerJoin(PlayerJoinEvent event) {
    Bukkit.getScheduler().runTaskLater(this.plugin,
        () -> this.tablistController.onJoin(event.getPlayer()), 3L);
  }

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event) {
    this.tablistController.onQuit(event.getPlayer());
  }

}

package gg.eris.commons.bukkit.impl.player;

import gg.eris.commons.bukkit.ErisBukkitCommonsPlugin;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public final class ErisPlayerManagerListener implements Listener {

  private final ErisBukkitCommonsPlugin plugin;
  private final ErisPlayerManagerImpl playerManagerImpl;

  @EventHandler(priority = EventPriority.LOWEST)
  public void onAsyncPlayerJoin(AsyncPlayerPreLoginEvent event) {
    // Load data
    this.playerManagerImpl.loadPlayer(event.getUniqueId());
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerQuit(PlayerQuitEvent event) {
    Bukkit.getScheduler().runTaskAsynchronously(this.plugin,
        () -> this.playerManagerImpl.unloadPlayer(event.getPlayer().getUniqueId()));
  }

}

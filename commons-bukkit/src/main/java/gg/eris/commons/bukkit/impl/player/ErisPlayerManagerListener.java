package gg.eris.commons.bukkit.impl.player;

import gg.eris.commons.bukkit.ErisBukkitCommons;
import gg.eris.commons.bukkit.player.ErisPlayerManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

@RequiredArgsConstructor
public final class ErisPlayerManagerListener implements Listener {

  private final ErisPlayerManagerImpl playerManagerImpl;

  @EventHandler(priority = EventPriority.LOWEST)
  public void onAsyncPlayerJoin(AsyncPlayerPreLoginEvent event) {
    // Load data
    playerManagerImpl.loadPlayer(event.getUniqueId());
  }

}

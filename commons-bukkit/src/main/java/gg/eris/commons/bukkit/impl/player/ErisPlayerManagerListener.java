package gg.eris.commons.bukkit.impl.player;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import gg.eris.commons.bukkit.ErisBukkitCommonsPlugin;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class ErisPlayerManagerListener implements Listener {

  private final ErisBukkitCommonsPlugin plugin;
  private final ErisPlayerManagerImpl playerManagerImpl;

  private final Cache<UUID, Boolean> addQueue;

  public ErisPlayerManagerListener(ErisBukkitCommonsPlugin plugin,
      ErisPlayerManagerImpl playerManagerImpl) {
    this.plugin = plugin;
    this.playerManagerImpl = playerManagerImpl;
    this.addQueue = CacheBuilder.newBuilder().expireAfterWrite(30, TimeUnit.SECONDS).build();
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onAsyncPlayerJoin(AsyncPlayerPreLoginEvent event) {
    boolean playerAdded = this.playerManagerImpl.loadPlayer(event.getUniqueId());
    if (!playerAdded) {
      this.addQueue.put(event.getUniqueId(), true);
    }
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerJoin(PlayerJoinEvent event) {
    Boolean newPlayer = this.addQueue.getIfPresent(event.getPlayer().getUniqueId());
    if (newPlayer != null) {
      this.addQueue.invalidate(event.getPlayer().getUniqueId());
      this.playerManagerImpl.createNewPlayer(event.getPlayer());
    }

    this.playerManagerImpl.updateFromHandleOnJoin(event.getPlayer());
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerQuit(PlayerQuitEvent event) {
    Bukkit.getScheduler().runTaskAsynchronously(this.plugin,
        () -> this.playerManagerImpl.unloadPlayer(event.getPlayer().getUniqueId()));
  }

}

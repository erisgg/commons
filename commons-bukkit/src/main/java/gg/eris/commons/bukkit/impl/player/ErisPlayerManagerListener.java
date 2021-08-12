package gg.eris.commons.bukkit.impl.player;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import gg.eris.commons.bukkit.ErisBukkitCommonsPlugin;
import gg.eris.commons.bukkit.player.ErisPlayer;
import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.core.util.Text;
import gg.eris.commons.core.util.Time;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
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
    ErisPlayer player = this.playerManagerImpl.loadPlayer(event.getUniqueId());
    if (player == null) {
      this.addQueue.put(event.getUniqueId(), true);
    } else {
      long banDuration = player.getPunishmentProfile().getBanDuration();
      if (banDuration != 0) {
        if (banDuration == -1) {
          event.setLoginResult(Result.KICK_BANNED);
          event.setKickMessage(
              CC.GOLD.bold() + "(!) " + CC.GOLD + "You are banned " + CC.YELLOW + "indefinitely"
                  + CC.GOLD + ".");
        } else {
          event.setLoginResult(Result.KICK_BANNED);
          event.setKickMessage(Text.replaceVariables(
              CC.GOLD.bold() + "(!) " + CC.GOLD
                  + "You are banned. Your ban will be over in " + CC.YELLOW + "{0}" + CC.GOLD + ".",
              Time.toLongDisplayTime(banDuration, TimeUnit.MILLISECONDS)
          ));
        }
      }
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

    ErisPlayer player = this.playerManagerImpl.getPlayer(event.getPlayer());
    if (player == null) {
      event.getPlayer().kickPlayer(CC.GOLD.bold() + "(!) " + CC.GOLD + "Something went wrong. Please rejoin.");
      return;
    }
    if (player.getNicknameProfile().isNicked()) {
      TextController.send(
          player,
          TextType.INFORMATION,
          "You are currently <h>nicked</h>."
      );
    }
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerQuit(PlayerQuitEvent event) {
    Bukkit.getScheduler().runTaskAsynchronously(this.plugin,
        () -> this.playerManagerImpl.unloadPlayer(event.getPlayer().getUniqueId()));
  }

}

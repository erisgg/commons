package gg.eris.commons.bukkit.impl.lock;

import gg.eris.commons.bukkit.util.CC;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public final class ServerLockListener implements Listener {

  private boolean lock = true;

  public void start() {
    this.lock = false;
  }

  @EventHandler
  public void onPlayerLogin(PlayerLoginEvent event) {
    if (this.lock) {
      event.setKickMessage(CC.RED.bold() + "Server is still starting. Please rejoin!");
      event.setResult(Result.ALLOWED);
    }
  }

}

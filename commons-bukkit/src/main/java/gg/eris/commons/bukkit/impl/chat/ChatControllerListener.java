package gg.eris.commons.bukkit.impl.chat;

import gg.eris.commons.bukkit.ErisBukkitCommonsPlugin;
import gg.eris.commons.bukkit.chat.ChatController;
import gg.eris.commons.bukkit.player.ErisPlayerManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@RequiredArgsConstructor
public final class ChatControllerListener implements Listener {

  private final ErisPlayerManager erisPlayerManager;
  private final ChatController chatController;

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onPlayerChat(AsyncPlayerChatEvent event) {
    event.setCancelled(true);
    this.chatController.say(this.erisPlayerManager.getPlayer(event.getPlayer()), event.getMessage());
  }

}

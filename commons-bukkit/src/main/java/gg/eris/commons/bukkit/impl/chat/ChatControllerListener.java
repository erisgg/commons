package gg.eris.commons.bukkit.impl.chat;

import gg.eris.commons.bukkit.chat.ChatController;
import gg.eris.commons.bukkit.player.ErisPlayer;
import gg.eris.commons.bukkit.player.ErisPlayerManager;
import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.core.util.Time;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
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

    ErisPlayer player = this.erisPlayerManager.getPlayer(event.getPlayer());
    long muteDuration = player.getPunishmentProfile().getMuteDuration();

    if (muteDuration > 0L) {
      TextController.send(
          player,
          TextType.ERROR,
          "You are currently <h>muted</h>. Your mute will expire in <h>{0}</h>.",
          Time.toLongDisplayTime(muteDuration, TimeUnit.MILLISECONDS)
      );
    } else {
      this.chatController.say(player, event.getMessage());
    }

  }

}

package gg.eris.commons.bukkit.impl.chat;

import com.google.common.collect.Lists;
import gg.eris.commons.bukkit.ErisBukkitCommonsPlugin;
import gg.eris.commons.bukkit.chat.ChatController;
import gg.eris.commons.bukkit.chat.ChatPlaceholder;
import gg.eris.commons.bukkit.player.ErisPlayer;
import gg.eris.commons.bukkit.text.ClickEvent;
import gg.eris.commons.bukkit.text.HoverEvent;
import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextMessage;
import gg.eris.commons.core.util.Text;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;

@RequiredArgsConstructor
public final class ChatControllerImpl implements ChatController {

  private final ErisBukkitCommonsPlugin plugin;

  private String format;
  private final List<ChatPlaceholder> placeholders;
  private final Int2ObjectMap<ClickEvent> clickEvents;
  private final Int2ObjectMap<HoverEvent> hoverEvents;

  public ChatControllerImpl(ErisBukkitCommonsPlugin plugin) {
    this.plugin = plugin;
    this.placeholders = Lists.newArrayList();
    this.clickEvents = new Int2ObjectArrayMap<>();
    this.hoverEvents = new Int2ObjectArrayMap<>();
  }

  @Override
  public void setFormat(String format, ChatPlaceholder... placeholders) {
    this.format = format;
    this.placeholders.clear();
    this.placeholders.addAll(Arrays.asList(placeholders));
  }

  @Override
  public void setClickEvents(Int2ObjectMap<ClickEvent> clickEvents) {
    this.clickEvents.clear();
    this.clickEvents.putAll(clickEvents);
  }

  @Override
  public void setHoverEvents(Int2ObjectMap<HoverEvent> hoverEvents) {
    this.hoverEvents.clear();
    this.hoverEvents.putAll(hoverEvents);
  }

  @Override
  public void say(ErisPlayer player, String chatMessage) {
    // Keep the calculation out of the task so it can potentially be run async
    TextMessage message = getMessage(player, chatMessage);
    Bukkit.getScheduler().runTask(this.plugin, () -> TextController.broadcastToServer(message));
  }

  @Override
  public TextMessage getMessage(ErisPlayer player, String chatMessage) {
    String replaced = Text.replaceVariables(this.format, (Object[]) this.placeholders
        .stream()
        .map(placeholder -> placeholder.get(player, chatMessage))
        .toArray(String[]::new));
    return TextController.parse(replaced);
  }

  @Override
  public String getFormat() {
    return this.format;
  }

}

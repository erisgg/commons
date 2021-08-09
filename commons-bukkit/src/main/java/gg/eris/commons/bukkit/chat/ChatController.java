package gg.eris.commons.bukkit.chat;

import gg.eris.commons.bukkit.player.ErisPlayer;
import gg.eris.commons.bukkit.text.ClickEvent;
import gg.eris.commons.bukkit.text.HoverEvent;
import gg.eris.commons.bukkit.text.TextMessage;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

public interface ChatController {

  /**
   * Sets the chat format
   *
   * @param format       is the chat format ({n} are placeholders, n >= 0)
   * @param placeholders are the placeholders
   */
  void setFormat(String format, ChatPlaceholder... placeholders);

  /**
   * Sets the message's click events
   *
   * @param clickEvents are the click events to set
   */
  void setClickEvents(Int2ObjectMap<ClickEvent> clickEvents);

  /**
   * Sets the message's hover events
   *
   * @param hoverEvents are the hover events to set
   */
  void setHoverEvents(Int2ObjectMap<HoverEvent> hoverEvents);

  /**
   * Causes a player to say a message
   *
   * @param player  is the player to say the message
   * @param message is the message for them to say
   */
  void say(ErisPlayer player, String message);

  /**
   * Gets a message
   *
   * @param player  is the player who would be saying the message
   * @param message is the message they would be saying
   * @return the {@link TextMessage}
   */
  TextMessage getMessage(ErisPlayer player, String message);

  /**
   * Returns the chat format
   *
   * @return the chat format
   */
  String getFormat();

}

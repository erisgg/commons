package gg.eris.commons.bukkit.text;

import gg.eris.commons.bukkit.impl.text.ComponentParser;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.entity.Player;

public final class TextController {

  public static final char ARROW = '\u00BB';
  private static final TextMessage ERIS_MESSAGE =
      TextMessage.of(TextComponent.builder("Eris " + ARROW + " ").color(TextColor.YELLOW).build());

  public static void send(Player player, TextType textType, Int2ObjectMap<ClickEvent> clickEvents,
      Int2ObjectMap<HoverEvent> hoverEvents, String message, Object... variables) {
    TextMessage textMessage = parse(textType, clickEvents, hoverEvents, message, variables);
    player.spigot().sendMessage(ComponentSerializer.parse(textMessage.getJsonMessage()));
  }

  public static void send(Player player, TextType textType, String message, Object... variables) {
    send(player,textType, Int2ObjectMaps.emptyMap(), Int2ObjectMaps.emptyMap(), message, variables);
  }

  public static TextMessage parse(TextType textType, Int2ObjectMap<ClickEvent> clickEvents,
      Int2ObjectMap<HoverEvent> hoverEvents, String message, Object... variables) {
    return TextMessage.join(ERIS_MESSAGE, ComponentParser.parse(textType, clickEvents,
        hoverEvents, message, variables));
  }

  public static TextMessage parse(TextType textType, String message, Object... variables) {
    return parse(textType, Int2ObjectMaps.emptyMap(), Int2ObjectMaps.emptyMap(), message,
        variables);
  }

  public static TextMessage parse(String message, Object... variables) {
    return parse(Int2ObjectMaps.emptyMap(), Int2ObjectMaps.emptyMap(), message, variables);
  }

  public static TextMessage parse(Int2ObjectMap<HoverEvent> hoverEvents,
      Int2ObjectMap<ClickEvent> clickEvents, String message, Object... variables) {
    return ComponentParser.parse(null, clickEvents, hoverEvents, message, variables);
  }

}
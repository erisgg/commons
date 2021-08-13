package gg.eris.commons.bukkit.text;

import gg.eris.commons.bukkit.ErisBukkitCommonsPlugin;
import gg.eris.commons.bukkit.impl.text.ComponentParser;
import gg.eris.commons.bukkit.player.ErisPlayer;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class TextController {

  public static final char ARROW = '\u00BB';
  private static final TextMessage ERIS_MESSAGE =
      TextMessage.of(TextComponent.builder("Eris " + ARROW + " ").color(TextColor.YELLOW).build());

  public static void send(ErisPlayer player, TextMessage textMessage) {
    send(player.getHandle(), textMessage);
  }

  public static void send(CommandSender sender, TextMessage textMessage) {
    Bukkit.getScheduler().runTask(ErisBukkitCommonsPlugin.getInstance(),
        () -> sender.sendMessage(ComponentSerializer.parse(textMessage.getJsonMessage())));
  }

  public static void broadcastToServer(TextMessage textMessage) {
    Bukkit.getScheduler().runTask(ErisBukkitCommonsPlugin.getInstance(), () -> {
      BaseComponent[] component = textMessage.getBaseComponent();
      for (Player player : Bukkit.getOnlinePlayers()) {
        player.spigot().sendMessage(component);
      }
      Bukkit.getConsoleSender().sendMessage(component);
    });

  }

  public static void broadcastToServer(TextType textType, Int2ObjectMap<ClickEvent> clickEvents,
      Int2ObjectMap<HoverEvent> hoverEvents, String message, Object... variables) {
    TextMessage textMessage = parse(textType, clickEvents, hoverEvents, message, variables);
    broadcastToServer(textMessage);
  }

  public static void broadcastToServer(TextType textType, String message, Object... variables) {
    TextMessage textMessage = parse(textType, Int2ObjectMaps.emptyMap(), Int2ObjectMaps.emptyMap(),
        message, variables);
    broadcastToServer(textMessage);
  }

  public static void send(ErisPlayer sender, TextType textType,
      Int2ObjectMap<ClickEvent> clickEvents,
      Int2ObjectMap<HoverEvent> hoverEvents, String message, Object... variables) {
    send(sender.getHandle(), textType, clickEvents, hoverEvents, message, variables);
  }

  public static void send(CommandSender sender, TextType textType,
      Int2ObjectMap<ClickEvent> clickEvents,
      Int2ObjectMap<HoverEvent> hoverEvents, String message, Object... variables) {
    TextMessage textMessage;

    if (sender instanceof Player) {
      textMessage = parse(textType, clickEvents, hoverEvents, message, variables);
    } else {
      textMessage = strip(textType, message, variables);
    }

    send(sender, textMessage);
  }

  public static void send(ErisPlayer player, TextType textType, String message,
      Object... variables) {
    send(player.getHandle(), textType, message, variables);
  }

  public static void send(CommandSender sender, TextType textType, String message,
      Object... variables) {
    send(sender, textType, Int2ObjectMaps.emptyMap(), Int2ObjectMaps.emptyMap(), message,
        variables);
  }

  public static void send(ErisPlayer player, Int2ObjectMap<ClickEvent> clickEvents,
      Int2ObjectMap<HoverEvent> hoverEvents, String message, Object... variables) {
    send(player.getHandle(), null, clickEvents, hoverEvents, message, variables);
  }

  public static void send(CommandSender sender, Int2ObjectMap<ClickEvent> clickEvents,
      Int2ObjectMap<HoverEvent> hoverEvents, String message, Object... variables) {
    send(sender, null, clickEvents, hoverEvents, message,
        variables);
  }

  public static void send(ErisPlayer player, String message, Object... variables) {
    send(player.getHandle(), message, variables);
  }

  public static void send(CommandSender sender, String message, Object... variables) {
    send(sender, null, Int2ObjectMaps.emptyMap(), Int2ObjectMaps.emptyMap(), message,
        variables);
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

  public static TextMessage strip(TextType textType, String message, Object... variables) {
    return TextMessage.join(ERIS_MESSAGE, ComponentParser.strip(textType, message, variables));
  }

}
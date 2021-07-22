package gg.eris.commons.bukkit.text;

import gg.eris.commons.bukkit.impl.text.ComponentParser;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import java.util.List;
import org.bukkit.entity.Player;

public final class TextController {

  public static final char ARROW = '\u00BB';
  private static final TextComponent ERIS_COMPONENT =
      TextComponent.builder("Eris " + ARROW + " ").color(TextColor.YELLOW).build();

  public static void send(Player player, TextType textType, String message, Object... variables) {
    TextMessage textMessage = ComponentParser.parse(textType, message, null, null, variables);
    player.spigot().sendMessage(textMessage.getBaseComponent());
  }


}
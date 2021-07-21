package gg.eris.commons.bukkit.text;

import com.google.common.collect.Lists;
import gg.eris.commons.core.util.Pair;
import gg.eris.commons.core.util.Text;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.md_5.bungee.chat.TextComponentSerializer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import org.bukkit.entity.Player;

public final class TextController {

  private static final Pattern EVENT_PATTERN = Pattern.compile("<event=[0-9]+>(.+?)</event>");
  public static final char ARROW = '\u00BB';
  private static final TextComponent ERIS_COMPONENT =
      TextComponent.builder("Eris " + ARROW + " ").color(TextColor.YELLOW).build();

  public static void send(Player player, TextType textType, String message, Object... variables) {
    List<TextComponent> parsedComponents = parseComponents(textType, message, variables);
    TextComponent[] parsedComponentsArray = parsedComponents.toArray(new TextComponent[0]);

    TextComponent[] components = new TextComponent[parsedComponents.size() + 1];
    components[0] = ERIS_COMPONENT;
    System.arraycopy(parsedComponentsArray, 0, components, 1, parsedComponentsArray.length);

    TextMessage textMessage = TextMessage.of(
        components
    );

    player.spigot().sendMessage(textMessage.getBaseComponent());
  }


  /*
   * This is my $$highlighted <event=0>text but$$</event> this <event=1>$$carries over</event>$$.
   */

  private static List<TextComponent> parseComponents(TextType textType, String message,
      Object... variables) {
    message = Text.replaceVariables(message, variables);

    List<TextComponent> components = Lists.newArrayList();

    return components;
  }

}

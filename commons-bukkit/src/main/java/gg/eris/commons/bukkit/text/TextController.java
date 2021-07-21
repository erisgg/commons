package gg.eris.commons.bukkit.text;

import com.google.common.collect.Maps;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.core.util.Text;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.ComponentBuilder.FormatRetention;
import net.md_5.bungee.api.chat.HoverEvent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@UtilityClass
public final class TextController {

  private static final Pattern EVENT_PATTERN = Pattern.compile("<event=[0-9]+>(.+?)</event>");
  public static final char ARROW = '\u00BB';

  public static void send(TextController.Builder builder, Player... players) {
    BaseComponent[] built = builder.build();
    for (Player player : players) {
      player.spigot().sendMessage(built);
    }
  }

  public static void send(Builder builder, Collection<? extends Player> players) {
    BaseComponent[] built = builder.build();
    for (Player player : players) {
      player.spigot().sendMessage(built);
    }
  }

  public static void broadcast(Builder builder) {
    send(builder, Bukkit.getOnlinePlayers());
  }

  public static TextController.Builder builder(String rawText, TextType textType,
      Object... variables) {
    return new Builder(rawText, textType, variables);
  }

  public static String highlight(TextType type, String message) {
    return highlight(type.getBaseColor(), type.getAccentColor(), message);
  }

  public static String highlight(ChatColor base, ChatColor accent, String message) {
    String highlight;
    while ((highlight = StringUtils.substringBetween(message, "$$")) != null) {
      message = message.replace("$$" + highlight + "$$", accent + highlight + base);
    }
    return base + message;
  }

  public static class Builder {

    private final String rawText;
    private final TextType textType;
    private final Object[] variables;

    private final Map<Integer, ClickEvent> clickEventMap;
    private final Map<Integer, HoverEvent> hoverEventMap;

    private Builder(String rawText, TextType textType, Object... variables) {
      this.rawText = rawText;
      this.textType = textType;
      this.variables = variables;
      this.clickEventMap = Maps.newHashMap();
      this.hoverEventMap = Maps.newHashMap();
    }

    public Builder withClickEvent(int index, ClickEvent clickEvent) {
      this.clickEventMap.put(index, clickEvent);
      return this;
    }

    public Builder withHoverEvent(int index, HoverEvent hoverEvent) {
      this.hoverEventMap.put(index, hoverEvent);
      return this;
    }

    private BaseComponent[] build() {
      ComponentBuilder builder = new ComponentBuilder("");

      String formattedRaw = CC.YELLOW + "Eris " + CC.BOLD + ARROW + " " + TextController.highlight(
          this.textType,
          Text.replaceVariables(this.rawText, this.variables)
      );

      Matcher matcher = EVENT_PATTERN.matcher(formattedRaw);

      int lastIndex = 0;

      while (matcher.find()) {
        int start = matcher.start();
        int end = matcher.end();

        builder.append(formattedRaw.substring(lastIndex, start), FormatRetention.FORMATTING);
        builder.append(matcher.group(1), FormatRetention.FORMATTING);

        String flag = formattedRaw.substring(start, end);

        int index = Integer.parseInt(flag.substring(flag.indexOf('=') + 1, flag.indexOf('>')));

        if (this.clickEventMap.containsKey(index)) {
          builder.event(this.clickEventMap.get(index));
        } else if (hoverEventMap.containsKey(index)) {
          builder.event(this.hoverEventMap.get(index));
        }

        lastIndex = end;
      }

      builder.append(formattedRaw.substring(lastIndex), FormatRetention.FORMATTING);

      return builder.create();
    }
  }
}

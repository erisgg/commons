package gg.eris.commons.bukkit.text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.ComponentBuilder.FormatRetention;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public final class TextUtil {

  private static final Pattern EVENT_PATTERN = Pattern.compile("<event=[0-9]+>(.+?)</event>");

  public static TextUtil.Builder builder(String rawText) {
    return new Builder(rawText);
  }

  public static class Builder {

    private final String rawText;

    private final Map<Integer, ClickEvent> clickEventMap;

    private final Map<Integer, HoverEvent> hoverEventMap;

    private final List<Integer> occupiedIds;

    private ChatColor color;

    private Builder(String rawText) {
      this.rawText = rawText;

      clickEventMap = new HashMap<>();
      hoverEventMap = new HashMap<>();

      occupiedIds = new ArrayList<>();
    }

    public Builder withChatColor(ChatColor color) {
      this.color = color;
      return this;
    }

    public Builder withClickEventAt(int index, ClickEvent clickEvent) {
      if (!occupiedIds.contains(index)) {
        clickEventMap.put(index, clickEvent);

        occupiedIds.add(index);
      }

      return this;
    }

    public Builder withHoverEventAt(int index, HoverEvent hoverEvent) {
      if (!occupiedIds.contains(index)) {
        hoverEventMap.put(index, hoverEvent);

        occupiedIds.add(index);
      }

      return this;
    }

    public BaseComponent[] build() {
      ComponentBuilder builder = new ComponentBuilder("");

      builder.color(color);

      Matcher matcher = EVENT_PATTERN.matcher(rawText);

      int lastIndex = 0;

      while (matcher.find()) {
        int start = matcher.start();
        int end = matcher.end();

        builder.append(rawText.substring(lastIndex, start), FormatRetention.FORMATTING);
        builder.append(matcher.group(1), FormatRetention.FORMATTING);

        String flag = rawText.substring(start, end);

        int index = Integer.parseInt(flag.substring(flag.indexOf('=') + 1, flag.indexOf('>')));

        if (clickEventMap.containsKey(index)) {
          builder.event(clickEventMap.get(index));
        }
        else if (hoverEventMap.containsKey(index)) {
          builder.event(hoverEventMap.get(index));
        }

        lastIndex = end;
      }

      builder.append(rawText.substring(lastIndex), FormatRetention.FORMATTING);

      return builder.create();
    }
  }
}

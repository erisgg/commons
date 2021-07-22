package gg.eris.commons.bukkit.impl.text;

import com.google.common.collect.Lists;
import gg.eris.commons.bukkit.text.ClickEvent;
import gg.eris.commons.bukkit.text.HoverEvent;
import gg.eris.commons.bukkit.text.TextComponent;
import gg.eris.commons.bukkit.text.TextMessage;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.core.util.Text;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ComponentParser {

  private static final Pattern TAG_PATTERN = Pattern.compile("<(\\S*?)[^>]*>.*?</\\1>|<.*?/>");

  public static TextMessage parse(TextType textType, String message,
      Int2ObjectMap<HoverEvent> hoverEvents, Int2ObjectMap<ClickEvent> clickEvents,
      Object... variables) {
    message = Text.replaceVariables(message, variables);

    List<TextComponent> components = Lists.newArrayList();

    Matcher matcher = TAG_PATTERN.matcher(message);

    while (matcher.find()) {
      int start = matcher.start();
      int end = matcher.end();
      String match = matcher.group();

      int closingIndex = match.indexOf('>');
      int tagLength = closingIndex - start + 2;

      String tag = match.substring(start + 1, closingIndex);
      String contents = match.substring(closingIndex + 1, end - tagLength);

    }

    return TextMessage.of(components.toArray(new TextComponent[0]));
  }

}


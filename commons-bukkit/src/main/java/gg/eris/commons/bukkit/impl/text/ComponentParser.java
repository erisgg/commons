package gg.eris.commons.bukkit.impl.text;

import com.google.common.collect.Lists;
import gg.eris.commons.bukkit.text.ClickEvent;
import gg.eris.commons.bukkit.text.HoverEvent;
import gg.eris.commons.bukkit.text.TextColor;
import gg.eris.commons.bukkit.text.TextComponent;
import gg.eris.commons.bukkit.text.TextMessage;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.core.util.Text;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ComponentParser {

  private static final Pattern TAG_PATTERN = Pattern.compile("<.+?>");

  public static TextMessage parse(TextType textType, Int2ObjectMap<ClickEvent> clickEvents,
      Int2ObjectMap<HoverEvent> hoverEvents, String message, Object... variables) {
    if (message == null || message.isEmpty()) {
      return TextMessage.empty();
    }

    message = Text.replaceVariables(message, variables);

    List<TextComponent> components = Lists.newArrayList();

    Matcher matcher = TAG_PATTERN.matcher(message);

    int bold = 0;
    int italic = 0;
    int underlined = 0;
    int strikethrough = 0;
    int obfuscated = 0;
    int highlighted = 0;
    boolean raw = false;
    Stack<Integer> eventStack = new Stack<>();
    Stack<TextColor> colorStack = new Stack<>();

    int lastIndex = 0;
    boolean lastMatch = false;
    boolean matched;
    // don't change
    while ((matched = matcher.find()) || !lastMatch) {
      int matchStart;
      if (!matched) {
        lastMatch = true;
        matchStart = message.length();
      } else {
        matchStart = matcher.start();
      }

      String previousString = message.substring(lastIndex, matchStart);
      if (!previousString.isEmpty()) {
        TextComponent.Builder builder = TextComponent.builder(previousString);

        if (bold > 0) {
          builder.bold();
        }

        if (italic > 0) {
          builder.italic();
        }

        if (underlined > 0) {
          builder.underlined();
        }

        if (strikethrough > 0) {
          builder.strikethrough();
        }

        if (obfuscated > 0) {
          builder.obfuscated();
        }

        if (highlighted > 0) {
          if (textType == null) {
            throw new IllegalArgumentException("Cannot highlight message '" + message
                + "' as text type is not provided");
          }
          builder.color(textType.getAccentColor());
        } else {
          if (colorStack.isEmpty()) {
            builder.color(textType == null ? null : textType.getBaseColor());
          } else {
            builder.color(colorStack.peek());
          }
        }

        if (!eventStack.isEmpty()) {
          int id = eventStack.peek();
          ClickEvent clickEvent = clickEvents.get(id);
          HoverEvent hoverEvent = hoverEvents.get(id);

          if (clickEvent == null && hoverEvent == null) {
            throw new IllegalArgumentException(
                "Invalid event id found in message '" + message + "'");
          }

          builder.clickEvent(clickEvent);
          builder.hoverEvent(hoverEvent);
        }

        components.add(builder.build());
      }

      if (lastMatch) {
        break;
      }

      String match = matcher.group();
      String tag = match.substring(1, match.length() - 1);
      boolean closing = tag.startsWith("/");

      if (TextTag.isRaw(tag)) {
        if (closing && raw) {
          raw = false;
        } else {
          raw = true;
        }
      }

      if (!raw) {
        if (TextTag.isBold(tag)) {
          if (closing) {
            bold--;
          } else {
            bold++;
          }
        } else if (TextTag.isItalic(tag)) {
          if (closing) {
            italic--;
          } else {
            italic++;
          }
        } else if (TextTag.isUnderlined(tag)) {
          if (closing) {
            underlined--;
          } else {
            underlined++;
          }
        } else if (TextTag.isStrikethrough(tag)) {
          if (closing) {
            strikethrough--;
          } else {
            strikethrough++;
          }
        } else if (TextTag.isObfuscated(tag)) {
          if (closing) {
            obfuscated--;
          } else {
            obfuscated++;
          }
        } else if (TextTag.isHighlight(tag)) {
          if (closing) {
            highlighted--;
          } else {
            highlighted++;
          }
        } else if (TextTag.isEvent(tag)) {
          if (closing) {
            eventStack.pop();
          } else {
            String eventValue = TextTag.EVENT.getValue(tag);
            try {
              if (eventValue == null) {
                throw new IllegalArgumentException("Message has invalid tag event 'null' at index "
                    + matcher.start() + " in message '" + message + "'");
              }
              int value = Integer.parseInt(eventValue);
              eventStack.push(value);
            } catch (NumberFormatException err) {
              throw new IllegalArgumentException("Message has invalid tag event '" + eventValue
                  + "' at index " + matcher.start() + " in message '" + message + "'");
            }
          }
        } else if (TextTag.isColor(tag)) {
          if (closing) {
            colorStack.pop();
          } else {
            String colorValue = TextTag.COLOR.getValue(tag);
            TextColor color = TextColor.getColor(colorValue);
            if (color == null) {
              throw new IllegalArgumentException(
                  "Message has invalid tag color '" + colorValue + "' at index " + matcher.start()
                      + " in message '" + message + "'");
            }
            colorStack.push(color);
          }
        } else {
          throw new IllegalArgumentException(
              "Message has invalid tag '" + tag + "' at index " + matcher.start() + " in message '"
                  + message + "'");
        }
      }

      lastIndex = matcher.end();
    }

    return TextMessage.of(components.toArray(new TextComponent[0]));
  }

}


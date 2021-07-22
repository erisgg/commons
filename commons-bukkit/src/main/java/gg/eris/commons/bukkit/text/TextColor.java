package gg.eris.commons.bukkit.text;

import com.google.common.collect.ImmutableMap;
import gg.eris.commons.core.util.Validate;
import java.util.Locale;
import java.util.Map;
import lombok.Getter;

public final class TextColor {

  public static final TextColor BLACK = new TextColor("black");
  public static final TextColor DARK_BLUE = new TextColor("dark_blue");
  public static final TextColor DARK_GREEN = new TextColor("dark_green");
  public static final TextColor DARK_AQUA = new TextColor("dark_aqua");
  public static final TextColor DARK_RED = new TextColor("dark_red");
  public static final TextColor DARK_PURPLE = new TextColor("dark_purple");
  public static final TextColor GRAY = new TextColor("gray");
  public static final TextColor DARK_GRAY = new TextColor("dark_gray");
  public static final TextColor BLUE = new TextColor("blue");
  public static final TextColor GREEN = new TextColor("green");
  public static final TextColor AQUA = new TextColor("aqua");
  public static final TextColor RED = new TextColor("red");
  public static final TextColor LIGHT_PURPLE = new TextColor("light_purple");
  public static final TextColor YELLOW = new TextColor("yellow");
  public static final TextColor WHITE = new TextColor("white");
  public static final TextColor RESET = new TextColor("reset");

  private static final Map<String, TextColor> colorMap;

  static {
    colorMap = ImmutableMap.<String, TextColor>builder()
        .put(BLACK.getId(), BLACK)
        .put(DARK_BLUE.getId(), DARK_BLUE)
        .put(DARK_GREEN.getId(), DARK_GREEN)
        .put(DARK_AQUA.getId(), DARK_AQUA)
        .put(DARK_RED.getId(), DARK_RED)
        .put(DARK_PURPLE.getId(), DARK_PURPLE)
        .put(GRAY.getId(), GRAY)
        .put(DARK_GRAY.getId(), DARK_GRAY)
        .put(BLUE.getId(), BLUE)
        .put(GREEN.getId(), GREEN)
        .put(AQUA.getId(), AQUA)
        .put(RED.getId(), RED)
        .put(LIGHT_PURPLE.getId(), LIGHT_PURPLE)
        .put(YELLOW.getId(), YELLOW)
        .put(WHITE.getId(), WHITE)
        .put(RESET.getId(), RESET)
        .build();
  }

  @Getter
  private final String id;

  private TextColor(String id) {
    this.id = id;
  }

  public static TextColor getColor(String color) {
    if (color == null) {
      return null;
    }

    color = color.toLowerCase(Locale.ROOT);
    return colorMap.get(color);
  }

}

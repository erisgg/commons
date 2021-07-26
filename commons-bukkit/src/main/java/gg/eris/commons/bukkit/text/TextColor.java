package gg.eris.commons.bukkit.text;

import com.google.common.collect.ImmutableMap;
import gg.eris.commons.bukkit.util.CC;
import java.util.Locale;
import java.util.Map;
import lombok.Getter;

public final class TextColor {

  public static final TextColor BLACK = new TextColor("black", CC.BLACK);
  public static final TextColor DARK_BLUE = new TextColor("dark_blue", CC.DARK_BLUE);
  public static final TextColor DARK_GREEN = new TextColor("dark_green", CC.DARK_GREEN);
  public static final TextColor DARK_AQUA = new TextColor("dark_aqua", CC.DARK_AQUA);
  public static final TextColor DARK_RED = new TextColor("dark_red", CC.DARK_RED);
  public static final TextColor DARK_PURPLE = new TextColor("dark_purple", CC.DARK_PURPLE);
  public static final TextColor GOLD = new TextColor("gold", CC.GOLD);
  public static final TextColor GRAY = new TextColor("gray", CC.GRAY);
  public static final TextColor DARK_GRAY = new TextColor("dark_gray", CC.DARK_GRAY);
  public static final TextColor BLUE = new TextColor("blue", CC.BLUE);
  public static final TextColor GREEN = new TextColor("green", CC.GREEN);
  public static final TextColor AQUA = new TextColor("aqua", CC.AQUA);
  public static final TextColor RED = new TextColor("red", CC.RED);
  public static final TextColor LIGHT_PURPLE = new TextColor("light_purple", CC.LIGHT_PURPLE);
  public static final TextColor YELLOW = new TextColor("yellow", CC.YELLOW);
  public static final TextColor WHITE = new TextColor("white", CC.WHITE);
  public static final TextColor RESET = new TextColor("reset", CC.RESET);

  private static final Map<String, TextColor> colorMap;

  static {
    colorMap = ImmutableMap.<String, TextColor>builder()
        .put(BLACK.getId(), BLACK)
        .put(DARK_BLUE.getId(), DARK_BLUE)
        .put(DARK_GREEN.getId(), DARK_GREEN)
        .put(DARK_AQUA.getId(), DARK_AQUA)
        .put(DARK_RED.getId(), DARK_RED)
        .put(DARK_PURPLE.getId(), DARK_PURPLE)
        .put(GOLD.getId(), GOLD)
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

  @Getter
  private final CC color;

  private TextColor(String id, CC color) {
    this.id = id;
    this.color = color;
  }

  public static TextColor getColor(String color) {
    if (color == null) {
      return null;
    }

    color = color.toLowerCase(Locale.ROOT);
    return colorMap.get(color);
  }

}

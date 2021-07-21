package gg.eris.commons.bukkit.text;

import java.util.Objects;
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

  @Getter
  private final String id;

  private TextColor(String id) {
    this.id = id;
  }

}

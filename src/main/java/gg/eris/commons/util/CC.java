package gg.eris.commons.util;

import net.md_5.bungee.api.ChatColor;

public final class CC {

  public static final CC BOLD = of(ChatColor.BOLD);
  public static final CC UNDERLINE = of(ChatColor.UNDERLINE);
  public static final CC ITALIC = of(ChatColor.ITALIC);
  public static final CC STRIKETHROUGH = of(ChatColor.STRIKETHROUGH);
  public static final CC RESET = of(ChatColor.RESET);

  public static final CC BLACK = of(ChatColor.BLACK);
  public static final CC DARK_BLUE = of(ChatColor.DARK_BLUE);
  public static final CC DARK_GREEN = of(ChatColor.DARK_GREEN);
  public static final CC DARK_AQUA = of(ChatColor.DARK_AQUA);
  public static final CC DARK_RED = of(ChatColor.DARK_RED);
  public static final CC DARK_PURPLE = of(ChatColor.DARK_PURPLE);
  public static final CC GOLD = of(ChatColor.GOLD);
  public static final CC GRAY = of(ChatColor.GRAY);
  public static final CC DARK_GRAY = of(ChatColor.DARK_GRAY);
  public static final CC BLUE = of(ChatColor.BLUE);
  public static final CC GREEN = of(ChatColor.GREEN);
  public static final CC AQUA = of(ChatColor.AQUA);
  public static final CC RED = of(ChatColor.RED);
  public static final CC LIGHT_PURPLE = of(ChatColor.LIGHT_PURPLE);
  public static final CC YELLOW = of(ChatColor.YELLOW);
  public static final CC WHITE = of(ChatColor.WHITE);


  private final ChatColor color;
  private final String string;

  private CC(ChatColor color) {
    this.color = color;
    this.string = color.toString();
  }

  private CC(ChatColor color, String string) {
    this.color = color;
    this.string = string;
  }


  public static CC of(ChatColor color) {
    return new CC(color);
  }

  public CC bold() {
    return new CC(this.color, string + CC.BOLD);
  }

  public CC underline() {
    return new CC(this.color, string + CC.UNDERLINE);
  }

  public CC strikethrough() {
    return new CC(this.color, string + CC.STRIKETHROUGH);
  }

  public CC italic() {
    return new CC(this.color, string + CC.ITALIC);
  }

  public CC reset() {
    return new CC(this.color, string + CC.RESET);
  }

  public String prefix() {
    return bold() + "(!) " + CC.RESET + this.string;
  }

  public String toString() {
    return string;
  }

  public static String strip(String message) {
    return ChatColor.stripColor(message);
  }

  public static String color(String message) {
    return ChatColor.translateAlternateColorCodes('&', message);
  }

}

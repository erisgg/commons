package gg.eris.commons.bukkit.text;

import lombok.Getter;

@Getter
public enum TextType {

  SUCCESS(TextColor.GREEN, TextColor.DARK_GREEN),
  ERROR(TextColor.RED, TextColor.DARK_RED),
  INFORMATION(TextColor.GOLD, TextColor.YELLOW),
  PROMPT(TextColor.LIGHT_PURPLE, TextColor.DARK_PURPLE);

  private final TextColor baseColor;
  private final TextColor accentColor;

  TextType(TextColor baseColor, TextColor accentColor) {
    this.baseColor = baseColor;
    this.accentColor = accentColor;
  }
}

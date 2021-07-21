package gg.eris.commons.bukkit.text;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;

@Getter
public enum TextType {

  SUCCESS(TextColor.GREEN, TextColor.DARK_GREEN),
  ERROR(TextColor.RED, TextColor.DARK_RED),
  INFORMATION(TextColor.AQUA, TextColor.DARK_AQUA),
  PROMPT(TextColor.LIGHT_PURPLE, TextColor.DARK_PURPLE);

  private final TextColor baseColor;
  private final TextColor accentColor;

  TextType(TextColor baseColor, TextColor accentColor) {
    this.baseColor = baseColor;
    this.accentColor = accentColor;
  }
}

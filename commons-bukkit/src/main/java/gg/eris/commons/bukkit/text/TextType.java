package gg.eris.commons.bukkit.text;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;

@Getter
public enum TextType {

  SUCCESS(ChatColor.GREEN, ChatColor.DARK_GREEN),
  ERROR(ChatColor.RED, ChatColor.DARK_RED),
  INFORMATION(ChatColor.AQUA, ChatColor.DARK_AQUA),
  PROMPT(ChatColor.LIGHT_PURPLE, ChatColor.DARK_PURPLE);

  private final ChatColor baseColor;
  private final ChatColor accentColor;

  TextType(ChatColor baseColor, ChatColor accentColor) {
    this.baseColor = baseColor;
    this.accentColor = accentColor;
  }
}

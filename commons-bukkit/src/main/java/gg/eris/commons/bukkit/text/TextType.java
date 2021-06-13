package gg.eris.commons.bukkit.text;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;

@Getter
public enum TextType {

  SUCCESS(ChatColor.WHITE, ChatColor.GREEN),
  ERROR(ChatColor.RED, ChatColor.DARK_RED),
  INFORMATION(ChatColor.BLUE, ChatColor.AQUA),
  PROMPT(ChatColor.YELLOW, ChatColor.GOLD);

  private final ChatColor baseColor;
  private final ChatColor accentColor;

  TextType(ChatColor baseColor, ChatColor accentColor) {
    this.baseColor = baseColor;
    this.accentColor = accentColor;
  }
}

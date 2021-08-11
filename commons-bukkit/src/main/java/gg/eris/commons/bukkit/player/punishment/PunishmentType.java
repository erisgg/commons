package gg.eris.commons.bukkit.player.punishment;

import java.util.Locale;
import lombok.Getter;

public enum PunishmentType {

  CHAT("chat"),
  IN_GAME("in_game");

  @Getter
  private final String jsonValue;

  PunishmentType(String jsonValue) {
    this.jsonValue = jsonValue;
  }

  public static PunishmentType fromJsonValue(String jsonValue) {
    if (CHAT.jsonValue.equals(jsonValue)) {
      return CHAT;
    } else if (IN_GAME.jsonValue.equals(jsonValue)) {
      return IN_GAME;
    } else {
      return null;
    }
  }

  public static PunishmentType fromLabel(String label) {
    switch (label.toLowerCase(Locale.ROOT)) {
      case "chat":
      case "c":
        return CHAT;
      case "ingame":
      case "i":
      case "game":
      case "in_game":
        return IN_GAME;
      default:
        return null;
    }
  }
}

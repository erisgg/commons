package gg.eris.commons.bukkit.chat;

import gg.eris.commons.bukkit.player.ErisPlayer;
import gg.eris.commons.bukkit.text.TextComponent;

public interface ChatPlaceholder {

  String get(ErisPlayer player, String chatMessage);

}

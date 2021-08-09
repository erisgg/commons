package gg.eris.commons.bukkit.chat;

import gg.eris.commons.bukkit.player.ErisPlayer;

public interface ChatPlaceholder {

  String get(ErisPlayer player, String chatMessage);

}

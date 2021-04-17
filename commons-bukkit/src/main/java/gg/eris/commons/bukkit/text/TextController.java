package gg.eris.commons.bukkit.text;

import org.bukkit.command.CommandSender;

/**
 * The {@link TextController} handles all text interactions with the player. It is responsible
 * for easily creating, formatting and standardizing messages sent to the user
 */
public interface TextController {

  // come on oli get a move on
  default void sendMessage(CommandSender sender, String message) {
    sender.sendMessage(message);
  }

}

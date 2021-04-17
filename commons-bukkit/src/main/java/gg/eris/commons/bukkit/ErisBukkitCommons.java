package gg.eris.commons.bukkit;

import gg.eris.commons.bukkit.command.CommandManager;
import gg.eris.commons.bukkit.text.TextController;

public interface ErisBukkitCommons {

  /**
   * Returns the server command manager instance
   *
   * @return the server's {@link CommandManager} instance
   */
  CommandManager getCommandManager();

  /**
   * Returns the server text controller instance
   *
   * @return the server's {@link TextController} instance
   */
  TextController getTextController();
}

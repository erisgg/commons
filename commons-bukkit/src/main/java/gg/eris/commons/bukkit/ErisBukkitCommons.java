package gg.eris.commons.bukkit;

import gg.eris.commons.bukkit.command.CommandManager;

public interface ErisBukkitCommons {

  /**
   * Returns the server command manager instance
   *
   * @return the server's {@link CommandManager} instance
   */
  CommandManager getCommandManager();
}

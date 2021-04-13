package gg.eris.commons.bukkit;

import gg.eris.commons.bukkit.command.CommandManager;
import gg.eris.commons.bukkit.impl.command.CommandManagerImpl;
import gg.eris.commons.bukkit.impl.menu.MenuListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class ErisBukkitCommonsPlugin extends JavaPlugin implements ErisBukkitCommons {

  private CommandManager commandManager;

  @Override
  public void onEnable() {
    this.commandManager = new CommandManagerImpl();

    PluginManager pluginManager = Bukkit.getPluginManager();
    pluginManager.registerEvents(new MenuListener(this), this);
  }

  @Override
  public CommandManager getCommandManager() {
    return this.commandManager;
  }

}

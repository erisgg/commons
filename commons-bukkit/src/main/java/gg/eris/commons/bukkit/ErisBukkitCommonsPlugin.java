package gg.eris.commons.bukkit;

import cloud.commandframework.CommandManager;
import cloud.commandframework.bukkit.BukkitCommandManager;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import gg.eris.commons.bukkit.impl.menu.MenuListener;
import java.util.function.Function;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class ErisBukkitCommonsPlugin extends JavaPlugin implements ErisBukkitCommons {

  private CommandManager<CommandSender> commandManager;

  @Override
  public void onEnable() {
    saveDefaultConfig();

    try {
      this.commandManager = new BukkitCommandManager<>(this,
          CommandExecutionCoordinator.simpleCoordinator(), Function.identity(), Function.identity()
      );
    } catch (Exception err) {
      err.printStackTrace();
    }


    PluginManager pluginManager = Bukkit.getPluginManager();
    pluginManager.registerEvents(new MenuListener(this), this);
  }

  @Override
  public CommandManager<CommandSender> getCommandManager() {
    return this.commandManager;
  }
}

package gg.eris.commons.bukkit;

import gg.eris.commons.bukkit.command.CommandManager;
import gg.eris.commons.bukkit.impl.command.CommandManagerImpl;
import gg.eris.commons.bukkit.impl.menu.MenuListener;
import gg.eris.commons.bukkit.impl.text.TextControllerImpl;
import gg.eris.commons.bukkit.text.TextController;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class ErisBukkitCommonsPlugin extends JavaPlugin implements ErisBukkitCommons,
    Listener {

  private CommandManager commandManager;
  private TextController textController;

  @Override
  public void onEnable() {
    this.commandManager = new CommandManagerImpl();
    this.textController = new TextControllerImpl();

    PluginManager pluginManager = Bukkit.getPluginManager();
    pluginManager.registerEvents(new MenuListener(this), this);

    // Register service
    ServicesManager servicesManager = Bukkit.getServicesManager();
    servicesManager.register(ErisBukkitCommons.class, this, this, ServicePriority.Highest);
  }


  @Override
  public CommandManager getCommandManager() {
    return this.commandManager;
  }

  @Override
  public TextController getTextController() {
    return this.textController;
  }
}

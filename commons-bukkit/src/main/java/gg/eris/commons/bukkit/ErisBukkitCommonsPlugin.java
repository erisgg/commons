package gg.eris.commons.bukkit;

import gg.eris.commons.bukkit.command.CommandManager;
import gg.eris.commons.bukkit.impl.command.CommandManagerImpl;
import gg.eris.commons.bukkit.impl.menu.MenuListener;
import gg.eris.commons.bukkit.impl.permission.PermissionRegistryImpl;
import gg.eris.commons.bukkit.impl.rank.RankRegistryImpl;
import gg.eris.commons.bukkit.permission.PermissionRegistry;
import gg.eris.commons.bukkit.rank.RankRegistry;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class ErisBukkitCommonsPlugin extends JavaPlugin implements ErisBukkitCommons,
    Listener {

  private CommandManager commandManager;
  private PermissionRegistry permissionRegistry;
  private RankRegistry rankRegistry;

  @Override
  public void onEnable() {
    this.commandManager = new CommandManagerImpl();
    this.permissionRegistry = new PermissionRegistryImpl();
    this.rankRegistry = new RankRegistryImpl();

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
  public PermissionRegistry getPermissionRegistry() {
    return this.permissionRegistry;
  }

  @Override
  public RankRegistry getRankRegistry() {
    return this.rankRegistry;
  }

}

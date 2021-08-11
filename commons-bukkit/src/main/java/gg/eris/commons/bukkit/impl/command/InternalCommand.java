package gg.eris.commons.bukkit.impl.command;

import gg.eris.commons.bukkit.ErisBukkitCommonsPlugin;
import java.util.ArrayList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;

final class InternalCommand extends Command implements PluginIdentifiableCommand {

  private final ErisBukkitCommonsPlugin plugin;
  private final gg.eris.commons.bukkit.command.Command command;

  public InternalCommand(ErisBukkitCommonsPlugin plugin,
      gg.eris.commons.bukkit.command.Command command) {
    super(command.getName(), command.getDescription(), "undefined",
        new ArrayList<>(command.getAliases()));
    this.plugin = plugin;
    this.command = command;
  }

  @Override
  public boolean execute(CommandSender sender, String label, String[] args) {
    command.handle(sender, label, args);
    return true;
  }

  @Override
  public Plugin getPlugin() {
    return this.plugin;
  }
}

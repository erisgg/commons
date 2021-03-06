package gg.eris.commons.bukkit.impl.command;

import com.google.common.collect.Maps;
import gg.eris.commons.bukkit.ErisBukkitCommonsPlugin;
import gg.eris.commons.bukkit.command.Command;
import gg.eris.commons.bukkit.command.Command.Builder;
import gg.eris.commons.bukkit.command.CommandManager;
import gg.eris.commons.bukkit.util.CommandUtil;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.commons.core.util.Validate;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

public final class CommandManagerImpl implements CommandManager {

  private static final String FALLBACK_PREFIX = "eris";

  private final ErisBukkitCommonsPlugin plugin;
  private final CommandMap commandMap;
  private final Map<String, Command> commands;

  public CommandManagerImpl(ErisBukkitCommonsPlugin plugin) {
    this.plugin = plugin;
    this.commandMap = CommandUtil.getCommandMap();
    this.commands = Maps.newHashMap();
  }

  @Override
  public void registerCommand(Builder builder) {
    Command command = builder.build();
    Validate.isTrue(!this.commands.containsKey(command.getName()),
        "command name " + command.getName() + " is already registered");
    for (String alias : command.getAliases()) {
      Validate.isTrue(!this.commands.containsKey(alias),
          "command name " + alias + "is already registered");
    }
    this.commands.put(command.getName(), command);
    for (String alias : command.getAliases()) {
      this.commands.put(alias, command);
    }

    boolean result = this.commandMap.register(command.getName(), FALLBACK_PREFIX,
        new InternalCommand(plugin, command));
    Bukkit.getLogger().info("Registered command with name " + command.getName() + " (success=" + result + ")");
  }

  @Override
  public Builder newCommandBuilder(String name, String description,
      String defaultErrorMessage, Identifier permission,
      String... aliases) {
    return new Command.Builder(
        name,
        description,
        defaultErrorMessage,
        permission,
        Set.of(aliases)
    );
  }

  @Override
  public Command getCommand(String alias) {
    return this.commands.get(alias.toLowerCase(Locale.ROOT));
  }
}

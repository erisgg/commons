package gg.eris.commons.bukkit.impl.command;

import com.google.common.collect.Maps;
import gg.eris.commons.bukkit.command.Command;
import gg.eris.commons.bukkit.command.Command.Builder;
import gg.eris.commons.bukkit.command.CommandManager;
import gg.eris.commons.bukkit.command.PermissionRegistry;
import gg.eris.commons.core.Validate;
import java.util.Map;
import java.util.Set;

public final class CommandManagerImpl implements CommandManager {

  private final Map<String, Command> commands;

  public CommandManagerImpl() {
    this.commands = Maps.newHashMap();
  }

  @Override
  public PermissionRegistry getPermissionRegistry() {
    return null;
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
  }

  @Override
  public Builder newCommandBuilder(String name, String description, String permission,
      String... aliases) {
    return new Command.Builder(
        name,
        description,
        permission,
        Set.of(aliases)
    );
  }
}

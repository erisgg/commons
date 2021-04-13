package gg.eris.commons.bukkit.impl.command;

import com.google.common.collect.Maps;
import gg.eris.commons.bukkit.command.Command;
import gg.eris.commons.bukkit.command.Command.Builder;
import gg.eris.commons.bukkit.command.CommandManager;
import java.util.Map;
import java.util.Set;

public final class CommandManagerImpl implements CommandManager {

  private final Map<String, Command> commands;

  public CommandManagerImpl() {
    this.commands = Maps.newHashMap();
  }

  @Override
  public void registerCommand(Command command) {

  }

  @Override
  public Builder builder(String name, String description, String permission, String... aliases) {
    return new Command.Builder(
        name,
        description,
        permission,
        Set.of(aliases)
    );
  }
}

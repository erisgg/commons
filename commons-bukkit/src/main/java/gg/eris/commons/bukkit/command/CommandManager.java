package gg.eris.commons.bukkit.command;

import java.util.Map;

/**
 * The {@link CommandManager}. Use this to register commands, and view registered commands
 */
public interface CommandManager {

  /**
   * Registers a command from an {@link Command.Builder}
   *
   * @param builder is the {@link Command.Builder} instance
   */
  void registerCommand(Command.Builder builder);

  /**
   * Returns a new {@link Command.Builder} instance
   *
   * @param name is the name of the command
   * @param description is the command description
   * @param permission is the command permission
   * @param aliases is the command alias
   * @return a new {@link Command.Builder} instance
   */
  Command.Builder newCommandBuilder(String name, String description, String permission,
      String... aliases);

  /**
   * Returns a {@link Command} instance from a command
   *
   * @param alias is the alias to look for
   * @return the {@link Command} instance if preset
   */
  Command getCommand(String alias);
}

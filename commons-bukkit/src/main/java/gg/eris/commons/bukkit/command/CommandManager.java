package gg.eris.commons.bukkit.command;

public interface CommandManager {

  PermissionRegistry getPermissionRegistry();

  void registerCommand(Command.Builder builder);

  Command.Builder newCommandBuilder(String name, String description, String permission,
      String... aliases);


}

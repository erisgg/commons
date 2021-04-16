package gg.eris.commons.bukkit.command;

import java.util.Set;

public interface CommandManager {

  PermissionRegistry getPermissionRegistry();

  void registerCommand(Command.Builder builder);

  Command.Builder builder(String name, String description, String permission, String... aliases);



}

package gg.eris.commons.bukkit.command;

import java.util.Set;

public interface CommandManager {

  void registerCommand(Command command);

  Command.Builder builder(String name, String description, String permission, String... aliases);

}

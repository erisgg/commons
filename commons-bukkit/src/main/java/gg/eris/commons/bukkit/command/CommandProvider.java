package gg.eris.commons.bukkit.command;

public interface CommandProvider {

  Command.Builder getCommand(CommandManager manager);

}

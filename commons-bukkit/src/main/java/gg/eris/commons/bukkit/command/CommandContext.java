package gg.eris.commons.bukkit.command;

import gg.eris.commons.bukkit.impl.command.SubCommandMatchResult;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.command.CommandSender;

@Getter
public class CommandContext {

  private final Command command;
  private final SubCommand subCommand;
  private final CommandSender commandSender;
  private final String label;
  private final String[] rawArgs;
  @Getter(AccessLevel.NONE)
  private final Map<String, Object> mappedArgs;

  private CommandContext(Command command, SubCommand subCommand,
      CommandSender commandSender, String label, String[] rawArgs,
      Map<String, Object> mappedArgs) {
    this.command = command;
    this.subCommand = subCommand;
    this.commandSender = commandSender;
    this.label = label;
    this.rawArgs = rawArgs;
    this.mappedArgs = mappedArgs;
  }

  public <T> T getArgument(String label) {
    return (T) mappedArgs.get(label);
  }

  public boolean isSuccess() {
    return this.subCommand != null;
  }

  public static CommandContext success(CommandSender sender, Command command,
      SubCommandMatchResult subCommandMatchResult, String label, String[] args) {
    return new CommandContext(
        command,
        subCommandMatchResult.getSubCommand(),
        sender,
        label,
        args,
        subCommandMatchResult.getMappedArgs()
    );
  }

  public static CommandContext failure(CommandSender sender, Command command, String label,
      String[] args) {
    return new CommandContext(
        command,
        null,
        sender,
        label,
        args,
        Map.of()
    );
  }

}

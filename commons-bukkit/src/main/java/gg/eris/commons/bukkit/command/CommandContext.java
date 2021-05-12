package gg.eris.commons.bukkit.command;

import gg.eris.commons.bukkit.impl.command.SubCommandMatchResult;
import gg.eris.commons.core.util.Validate;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Getter
public final class CommandContext {

  private final Command command;
  private final SubCommand subCommand;
  private final CommandSender commandSender;
  private final boolean isPlayerExecute;
  private final String label;
  private final String[] rawArgs;
  @Getter(AccessLevel.NONE)
  private final Map<String, Object> mappedArgs;

  private CommandContext(Command command, SubCommand subCommand, CommandSender commandSender,
      String label, String[] rawArgs, Map<String, Object> mappedArgs) {
    this.command = command;
    this.subCommand = subCommand;
    this.commandSender = commandSender;
    this.isPlayerExecute = commandSender instanceof Player;
    this.label = label;
    this.rawArgs = rawArgs;
    this.mappedArgs = mappedArgs;
  }

  /**
   * Returns an argument from the parameters
   *
   * @param id is the argument id
   * @param <T> is the type of the argument
   * @return the argument
   */
  public <T> T getArgument(String id) {
    Validate.isTrue(mappedArgs.containsKey(id), "argument id of " + id + " does not exist");
    return (T) mappedArgs.get(id);
  }

  /**
   * Returns whether the command was successful
   *
   * @return whether the command has an executor (arguments matched)
   */
  public boolean isSuccess() {
    return this.subCommand != null;
  }

  /**
   * Returns the {@link CommandSender} as a player
   *
   * @return the {@link CommandSender} as a player if it is a player, null if not
   */
  public Player getSenderAsPlayer() {
    Validate.isTrue(this.isPlayerExecute, "command sender is not a player");
    return (Player) this.commandSender;
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

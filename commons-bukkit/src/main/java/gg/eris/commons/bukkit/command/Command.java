package gg.eris.commons.bukkit.command;

import com.google.common.collect.Sets;
import gg.eris.commons.bukkit.impl.command.SubCommandMatchResult;
import gg.eris.commons.core.Validate;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import lombok.Getter;
import org.bukkit.command.CommandSender;

@Getter
public final class Command {

  private final String name;
  private final Set<String> aliases;
  private final String description;
  private final boolean playerOnly;
  private final Set<SubCommand> subCommands;
  private final SubCommand defaultSubCommand;

  public Command(String name, Set<String> aliases, String description, boolean playerOnly,
      Set<SubCommand.Builder> subCommands, String permission,
      Consumer<CommandContext> defaultHandler) {
    this.name = name.toLowerCase(Locale.ROOT);
    this.aliases = aliases.stream()
        .map(string -> string.toLowerCase(Locale.ROOT))
        .collect(Collectors.toUnmodifiableSet());
    this.description = description;
    this.playerOnly = playerOnly;
    this.defaultSubCommand = new SubCommand(
        this,
        defaultHandler,
        List.of(),
        this.playerOnly,
        permission
    );
    this.subCommands = subCommands.stream()
        .map(builder -> builder.build(this))
        .collect(Collectors.toUnmodifiableSet());
  }

  /**
   * Returns the base permission
   *
   * @return the permission for the default subcommand (the base permission)
   */
  public Permission getPermission() {
    return this.defaultSubCommand.getPermission();
  }

  /**
   * Handles a command execution
   *
   * @param sender is the command sender
   * @param label is the command label
   * @param args are the raw arguments
   */
  public void handle(CommandSender sender, String label, String[] args) {
    SubCommandMatchResult matchResult = null;
    for (SubCommand subCommand : this.subCommands) {
      matchResult = subCommand.getMatchResult(args);
      if (!matchResult.isEmpty()) {
        break;
      }
    }

    CommandContext context;
    if (matchResult == null) {
      if (args.length == 0) {
        context = CommandContext.success(sender, this,
            SubCommandMatchResult.match(this.defaultSubCommand, Map.of()), label, args);
      } else {
        context = CommandContext.failure(sender, this, label, args);
      }
    } else {
      context = CommandContext.success(sender, this, matchResult, label, args);
    }

    execute(context);
  }

  private void execute(CommandContext context) {
    if (context.isSuccess()) {
      SubCommand subCommand = context.getSubCommand();
      if (!subCommand.getPermission().hasPermission(context.getCommandSender())) {
        // TODO: No permission message using TextController...
        return;
      }

      subCommand.execute(context);
    } else {
      // TODO: Help message using TextController
    }
  }


  /**
   * Builder class for an {@link Command}
   */
  public static class Builder {

    private boolean built;
    private final String name;
    private final String description;
    private final String permission;
    private final Set<String> aliases;
    private final Set<SubCommand.Builder> subCommands;

    private Consumer<CommandContext> defaultHandler;
    private boolean playerOnly;

    /**
     * Creates a new {@link Command.Builder} instacne. Should not be used outside of Commons -
     * use the {@link CommandManager}
     *
     * @param name is the name of the command
     * @param description is the command description
     * @param permission is the base permission (which is automatically prefixed with eris.)
     * @param aliases are the command aliases
     */
    public Builder(String name, String description, String permission, Set<String> aliases) {
      Validate.notEmpty(name, "name cannot be null or empty");
      Validate.notEmpty(description, "description cannot be null or empty");
      Validate.notEmpty(permission, "permission cannot be null or empty");
      for (String alias : aliases) {
        Validate.notEmpty(alias, "aliases cannot be null or empty");
      }

      this.built = false;
      this.name = name;
      this.description = description;
      this.permission = permission;
      this.aliases = aliases;
      this.subCommands = Sets.newHashSet();
      this.playerOnly = false;
    }

    /**
     * Creates a new subcommand builder for this command
     *
     * @return the {@link Command.Builder} instance
     */
    public SubCommand.Builder withSubCommand() {
      SubCommand.Builder builder = new SubCommand.Builder(this);
      this.subCommands.add(builder);
      return builder;
    }

    /**
     * Sets the no args handler (default sub command)
     *
     * @param defaultHandler is the handler
     * @return the {@link Command.Builder} instance
     */
    public Builder noArgsHandler(Consumer<CommandContext> defaultHandler) {
      return noArgsHandler(defaultHandler, false);
    }

    /**
     * Sets the no args handler and whether it is player only
     *
     * @param defaultHandler is the handler (default sub command)
     * @param playerOnly is whether the command is player only
     * @return the {@link Command.Builder} instance
     */
    public Builder noArgsHandler(Consumer<CommandContext> defaultHandler, boolean playerOnly) {
      this.defaultHandler = defaultHandler;
      this.playerOnly = playerOnly;
      return this;
    }


    /**
     * Builds the command. Should not be used outside of Commons - build with the {@link CommandManager}
     *
     * @return the built {@link Command}
     */
    public synchronized Command build() {
      Validate.isTrue(!this.built, "command has already been built");
      Validate.isTrue(this.defaultHandler != null, "default handler cannot be null");
      this.built = true;
      return new Command(
          this.name,
          this.aliases,
          this.description,
          this.playerOnly,
          this.subCommands,
          this.permission,
          this.defaultHandler
      );
    }
  }


}

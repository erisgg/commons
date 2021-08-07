package gg.eris.commons.bukkit.command;

import com.google.common.collect.Sets;
import gg.eris.commons.bukkit.ErisBukkitCommonsPlugin;
import gg.eris.commons.bukkit.impl.command.SubCommandMatchResult;
import gg.eris.commons.bukkit.permission.PermissionRegistry;
import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextMessage;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.commons.core.util.Validate;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Getter
public final class Command {

  private static final PermissionRegistry PERMISSION_REGISTRY
      = ErisBukkitCommonsPlugin.getInstance().getPermissionRegistry();

  private final String name;
  private final Set<String> aliases;
  private final String description;
  private final TextMessage usage;
  private final Identifier permission;
  private final boolean playerOnly;
  private final Set<SubCommand> subCommands;
  private final SubCommand defaultSubCommand;

  public Command(String name, Set<String> aliases, String description,
      TextMessage usage, boolean playerOnly, Set<SubCommand.Builder> subCommands,
      Identifier permission, Consumer<CommandContext> defaultHandler) {
    this.name = name.toLowerCase(Locale.ROOT);
    this.aliases = aliases.stream()
        .map(string -> string.toLowerCase(Locale.ROOT))
        .collect(Collectors.toUnmodifiableSet());
    this.description = description;
    this.playerOnly = playerOnly;
    this.usage = usage;
    this.permission = permission;
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
  public Identifier getPermission() {
    return this.permission;
  }

  /**
   * Handles a command execution
   *
   * @param sender is the command sender
   * @param label  is the command label
   * @param args   are the raw arguments
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
    if (matchResult == null || matchResult.isEmpty()) {
      if (args.length == 0 && this.defaultSubCommand.getCallback() != null) {
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
      if (!Command.PERMISSION_REGISTRY.get(this.getPermission())
          .hasPermission(context.getCommandSender())
          || (subCommand.getPermission() != null && Command.PERMISSION_REGISTRY.get(subCommand.getPermission())
          .hasPermission(context.getCommandSender()))) {
        TextController.send(context.getCommandSender(), TextType.ERROR, "No permission.");
      } else if (subCommand.isPlayerOnly() && !(context.getCommandSender() instanceof Player)) {
        TextController.send(
            context.getCommandSender(),
            TextType.ERROR,
            "That command is <h>player only</h>"
        );
      } else {
        subCommand.execute(context);
      }
    } else {
      TextController.send(context.getCommandSender(), this.usage);
    }
  }

  /**
   * Builder class for an {@link Command}
   */
  public static class Builder {

    private boolean built;
    private final String name;
    private final String description;
    private final String usage;
    private final Identifier permission;
    private final Set<String> aliases;
    private final Set<SubCommand.Builder> subCommands;

    private Consumer<CommandContext> defaultHandler;
    private boolean playerOnly;

    /**
     * Creates a new {@link Command.Builder} instacne. Should not be used outside of Commons - use
     * the {@link CommandManager}
     *
     * @param name        is the name of the command
     * @param description is the command description
     * @param usage       is the default error message for no suitable subcmd
     * @param permission  is the permission identifier
     * @param aliases     are the command aliases
     */
    public Builder(String name, String description, String usage,
        Identifier permission, Set<String> aliases) {
      Validate.notEmpty(name, "name cannot be null or empty");
      Validate.notNull(usage, "error cannot be null");
      Validate.notEmpty(description, "description cannot be null or empty");
      Validate.notNull(permission, "permission cannot be null or empty");
      for (String alias : aliases) {
        Validate.notEmpty(alias, "aliases cannot be null or empty");
      }
      this.built = false;
      this.name = name;
      this.description = description;
      this.usage = usage;
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
     * @param playerOnly     is whether the command is player only
     * @return the {@link Command.Builder} instance
     */
    public Builder noArgsHandler(Consumer<CommandContext> defaultHandler, boolean playerOnly) {
      this.defaultHandler = defaultHandler;
      this.playerOnly = playerOnly;
      return this;
    }


    /**
     * Builds the command. Should not be used outside of Commons - build with the {@link
     * CommandManager}
     *
     * @return the built {@link Command}
     */
    public synchronized Command build() {
      Validate.isTrue(!this.built, "command has already been built");
      this.built = true;
      return new Command(
          this.name,
          this.aliases,
          this.description,
          TextController.parse(
              TextType.ERROR,
              "Invalid usage. Use <h><raw>/" + this.usage + "</raw></h>."
          ),
          this.playerOnly,
          this.subCommands,
          this.permission,
          this.defaultHandler
      );
    }
  }


}

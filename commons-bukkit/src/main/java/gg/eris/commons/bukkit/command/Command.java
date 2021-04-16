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
  private final Set<SubCommand> subCommands;
  private final SubCommand defaultSubCommand;

  public Command(String name, Set<String> aliases, String description,
      Set<SubCommand.Builder> subCommands,
      String permission, Consumer<CommandContext> defaultHandler) {
    this.name = name.toLowerCase(Locale.ROOT);
    this.aliases = aliases.stream().map(string -> string.toLowerCase(Locale.ROOT)).collect(
        Collectors.toUnmodifiableSet());
    this.description = description;
    this.subCommands = subCommands.stream().map(builder -> builder.build(this))
        .collect(Collectors.toUnmodifiableSet());
    this.defaultSubCommand = new SubCommand(this, defaultHandler, List.of(), permission);
  }

  public Permission getPermission() {
    return this.defaultSubCommand.getPermission();
  }

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
      context.getSubCommand().execute(context);
    } else {
      // TODO: Help message using TextController
    }
  }


  public static class Builder {

    private boolean built;
    private final String name;
    private final String description;
    private final String permission;
    private final Set<String> aliases;
    private final Set<SubCommand.Builder> subCommands;

    private Consumer<CommandContext> defaultHandler;

    public Builder(String name, String description, String permission, Set<String> aliases) {
      Validate.isTrue(name != null, "name cannot be null");
      Validate.isTrue(description != null, "description cannot be null");
      Validate.isTrue(permission != null, "permission cannot be null");
      Validate.isTrue(aliases != null, "aliases cannot be null");
      this.built = false;
      this.name = name;
      this.description = description;
      this.permission = permission;
      this.aliases = aliases;
      this.subCommands = Sets.newHashSet();
    }

    public SubCommand.Builder withSubCommand() {
      SubCommand.Builder builder = new SubCommand.Builder(this);
      this.subCommands.add(builder);
      return builder;
    }

    public Builder noArgsHandler(Consumer<CommandContext> defaultHandler) {
      this.defaultHandler = defaultHandler;
      return this;
    }

    public synchronized Command build() {
      Validate.isTrue(!this.built, "command has already been built");
      Validate.isTrue(this.defaultHandler != null, "default handler cannot be null");
      this.built = true;
      return new Command(
          this.name,
          this.aliases,
          this.description,
          this.subCommands,
          this.permission,
          this.defaultHandler
      );
    }
  }


}

package gg.eris.commons.bukkit.command;

import gg.eris.commons.bukkit.impl.command.SubCommandMatchResult;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;

@RequiredArgsConstructor
public final class Command {

  private final String name;
  private final Set<String> aliases;
  private final String description;
  private final String basePermission;
  private final Set<SubCommand> subCommands;
  private final SubCommand mainExecutor;

  public void handle(CommandSender sender, Command command, String label, String[] args) {
    SubCommandMatchResult matchResult = null;
    for (SubCommand subCommand : this.subCommands) {
      matchResult = subCommand.getMatchResult(args);
      if (!matchResult.isEmpty()) {
        break;
      }
    }

    CommandContext context;
    if (matchResult == null) {
      context = CommandContext.failure(sender, this, label, args);
    } else {
      context = CommandContext.success(sender, this, matchResult, label, args);
    }

    execute(context);
  }

  private void execute(CommandContext context) {
    context.getSubCommand().execute(context);
  }

  public static class Builder {

    private final String name;
    private final String description;
    private final String permission;
    private final Set<String> aliases;

    public Builder(String name, String description, String permission, Set<String> aliases) {
      this.name = name;
      this.description = description;
      this.permission = permission;
      this.aliases = aliases;
    }

  }
}

package gg.eris.commons.bukkit.command;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import gg.eris.commons.bukkit.command.argument.Argument;
import gg.eris.commons.bukkit.impl.command.ArgumentInstance;
import gg.eris.commons.bukkit.impl.command.SubCommandMatchResult;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.commons.core.util.Validate;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import lombok.Getter;


public final class SubCommand {

  @Getter
  private final Command parent;
  private final Consumer<CommandContext> callback;
  private final List<ArgumentInstance> arguments;
  private final boolean playerOnly;
  @Getter
  private final Identifier permission;
  private final ArgumentInstance vararg;
  private final int minVarargCount;

  public SubCommand(Command parent, Consumer<CommandContext> callback,
      List<ArgumentInstance> arguments, boolean playerOnly, Identifier permission) {
    this.parent = parent;
    this.callback = callback;
    this.arguments = arguments;
    this.playerOnly = playerOnly;
    this.permission = permission == null ? parent.getPermission() : permission;
    this.vararg = this.arguments.size() > 0 ?
        (this.arguments.get(this.arguments.size() - 1).isVararg() ?
            this.arguments.get(this.arguments.size() - 1) : null) : null;
    this.minVarargCount = this.arguments.size() > 0 ? Math
        .max(0, this.arguments.get(this.arguments.size() - 1).getMinVarargCount()) : 0;
  }

  public SubCommandMatchResult getMatchResult(String[] rawArguments) {
    if (rawArguments.length < this.arguments.size() + minVarargCount) {
      return SubCommandMatchResult.noMatch();
    }

    Map<String, Object> contextMap = Maps.newHashMap();

    for (int i = 0; i < rawArguments.length; i++) {
      String rawArgument = rawArguments[i];

      ArgumentInstance argumentInstance = i < arguments.size() ? arguments.get(i) : vararg;
      Argument<?> argument = argumentInstance.getArgument();

      Object value = argument.convert(rawArgument);

      if (!argument.matches(value)) {
        return SubCommandMatchResult.noMatch();
      } else {
        contextMap.put(argumentInstance.getArgument().getArgumentId(), value);
      }
    }

    return SubCommandMatchResult.match(this, contextMap);
  }

  public void execute(CommandContext context) {
    callback.accept(context);
  }

  public boolean isSimilar(SubCommand other) {
    if (this.arguments.size() != other.arguments.size()) {
      return false;
    }

    for (int i = 0; i < this.arguments.size(); i++) {
      ArgumentInstance selfArg = this.arguments.get(i);
      ArgumentInstance otherArg = other.arguments.get(i);
      if (!selfArg.isSimilar(otherArg)) {
        return false;
      }
    }

    return true;

  }

  public static class Builder {

    private final Command.Builder parentBuilder;
    private final List<ArgumentInstance> arguments;

    private Consumer<CommandContext> handler;
    private Identifier permission;
    private boolean playerOnly;
    private boolean finalized;

    public Builder(Command.Builder parentBuilder) {
      this.parentBuilder = parentBuilder;
      this.arguments = Lists.newArrayList();
      this.finalized = false;
    }

    public Builder argument(Argument<?> argument) {
      Validate.isTrue(!this.finalized, "builder is already finalized");
      Validate.isTrue(
          this.arguments.size() == 0 || !this.arguments.get(this.arguments.size() - 1).isVararg(),
          "subcommand cannot have any more arguments"
      );
      for (ArgumentInstance instance : arguments) {
        Validate.isTrue(!instance.getArgument().getArgumentId().equals(argument.getArgumentId()),
            "argument ID already used");
      }

      this.arguments.add(new ArgumentInstance(
          argument,
          arguments.size()
      ));

      return this;
    }

    public Builder variableArgument(Argument<?> argument) {
      return variableArgument(argument, 0);
    }

    public Builder variableArgument(Argument<?> argument, int minVarargCount) {
      Validate.isTrue(
          this.arguments.size() == 0 || !this.arguments.get(this.arguments.size() - 1).isVararg(),
          "subcommand cannot have any more arguments");
      Validate.isTrue(!this.finalized, "builder is already finalized");
      this.arguments.add(new ArgumentInstance(
          argument,
          arguments.size(),
          minVarargCount
      ));

      return this;
    }

    public Builder requiresPermission(Identifier permission) {
      Validate.isTrue(!this.finalized, "builder is already finalized");
      this.permission = permission;
      return this;
    }

    public Builder handler(Consumer<CommandContext> handler) {
      Validate.isTrue(!this.finalized, "builder is already finalized");
      this.handler = handler;
      return this;
    }

    public Builder asPlayerOnly() {
      this.playerOnly = true;
      return this;
    }

    public Command.Builder finished() {
      Validate.isTrue(this.handler != null, "handler cannot be null");
      Validate.isTrue(!this.finalized, "builder is already finalized");
      this.finalized = true;
      return this.parentBuilder;
    }

    protected SubCommand build(Command command) {
      Validate.isTrue(this.handler != null, "handler cannot be null");
      Validate.isTrue(this.finalized, "builder is not finalized");
      return new SubCommand(
          command,
          this.handler,
          ImmutableList.copyOf(this.arguments),
          this.playerOnly,
          this.permission
      );
    }

  }

}
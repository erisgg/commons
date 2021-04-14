package gg.eris.commons.bukkit.command;

import com.google.common.collect.Maps;
import gg.eris.commons.bukkit.command.argument.Argument;
import gg.eris.commons.bukkit.impl.command.ArgumentInstance;
import gg.eris.commons.bukkit.impl.command.SubCommandMatchResult;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class SubCommand {

  private final Consumer<CommandContext> callback;
  private final List<ArgumentInstance> arguments;
  private final ArgumentInstance vararg;
  private final int minVarargCount;

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
        contextMap.put(argumentInstance.getLabel(), value);
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

}
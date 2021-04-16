package gg.eris.commons.bukkit.impl.command;

import gg.eris.commons.bukkit.command.argument.Argument;
import lombok.Getter;

/**
 * Holds additional information about a given argument
 */
@Getter
public final class ArgumentInstance {

  /**
   * A non vararg {@link ArgumentInstance}
   *
   * @param argument is the argument instance
   * @param index    is the index
   */
  public ArgumentInstance(Argument<?> argument, int index) {
    this.argument = argument;
    this.index = index;
    this.minVarargCount = Integer.MIN_VALUE;
  }

  /**
   * @param argument       is the argument
   * @param index          is the index
   * @param minVarargCount is the amount of required varargs
   */
  public ArgumentInstance(Argument<?> argument, int index, int minVarargCount) {
    this.argument = argument;
    this.index = index;
    this.minVarargCount = minVarargCount;
  }

  private final Argument<?> argument;
  private final int index;
  private final int minVarargCount;

  public boolean isVararg() {
    return this.minVarargCount >= 0;
  }

  public boolean isSimilar(ArgumentInstance other) {
    return this.index == other.index && this.argument.isSimilar(other.argument);
  }

}

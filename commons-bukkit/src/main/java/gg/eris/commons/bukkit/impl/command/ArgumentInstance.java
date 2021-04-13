package gg.eris.commons.bukkit.impl.command;

import gg.eris.commons.bukkit.command.argument.Argument;
import lombok.Getter;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * Holds additional information about a given argument
 */
@Getter
public final class ArgumentInstance {

  /**
   * A non vararg {@link ArgumentInstance}
   *
   * @param label    is the label
   * @param index    is the index
   * @param argument is the argument instance
   */
  public ArgumentInstance(String label, int index, Argument<?> argument) {
    this.label = label;
    this.index = index;
    this.argument = argument;
    this.minVarargCount = Integer.MIN_VALUE;
  }

  /**
   * @param label          is the label
   * @param index          is the index
   * @param argument       is the argument
   * @param minVarargCount is the amount of required varargs
   */
  public ArgumentInstance(String label, int index,
      Argument<?> argument, int minVarargCount) {

    this.label = label;
    this.index = index;
    this.argument = argument;
    this.minVarargCount = minVarargCount;
  }

  private final String label;
  private final int index;
  private final Argument<?> argument;
  private final int minVarargCount;

  public boolean isVararg() {
    return this.minVarargCount >= 0;
  }


}

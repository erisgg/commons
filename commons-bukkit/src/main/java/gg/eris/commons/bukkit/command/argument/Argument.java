package gg.eris.commons.bukkit.command.argument;

import java.util.function.Function;
import lombok.Getter;

public abstract class Argument<T> {

  @Getter
  private final String argumentId;
  private final Function<T, Boolean> matcher;

  public Argument(String argumentId, Function<T, Boolean> matcher) {
    this.argumentId = argumentId;
    this.matcher = matcher;
  }

}

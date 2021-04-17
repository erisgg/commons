package gg.eris.commons.bukkit.command.argument;

import java.util.function.Function;
import lombok.Getter;

public abstract class Argument<T> {

  @Getter
  private final String argumentId;
  private final Function<String, T> converter;
  private final Function<T, Boolean> matcher;
  private final Function<String, Boolean> softMatcher;
  private final Class<T> genericClass;

  public Argument(String argumentId, Class<T> genericClass, Function<String, T> converter,
      Function<T, Boolean> matcher, Function<String, Boolean> softMatcher) {
    this.argumentId = argumentId;
    this.genericClass = genericClass;
    this.converter = converter;
    this.matcher = matcher;
    this.softMatcher = softMatcher;
  }

  public T convert(String value) {
    return this.converter.apply(value);
  }

  public boolean matches(Object input) {
    if (!genericClass.isInstance(input)) {
      return false;
    }
    return matcher.apply(genericClass.cast(input));
  }

  public boolean softMatches(String value) {
    return softMatcher.apply(value);
  }

  public boolean isSimilar(Argument<?> other) {
    return this.getClass().isInstance(other);
  }

}

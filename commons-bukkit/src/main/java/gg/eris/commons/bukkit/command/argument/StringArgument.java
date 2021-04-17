package gg.eris.commons.bukkit.command.argument;

public final class StringArgument extends Argument<String> {

  private StringArgument(String argumentId) {
    super(argumentId,
        String.class,
        value -> value,
        value -> true,
        value -> true
    );
  }

  public static StringArgument of(String argumentId) {
    return new StringArgument(argumentId);
  }

  @Override
  public boolean isSimilar(Argument<?> other) {
    return super.isSimilar(other) || other instanceof PlayerArgument || other instanceof LiteralArgument;
  }
}

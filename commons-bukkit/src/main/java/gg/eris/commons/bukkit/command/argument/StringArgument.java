package gg.eris.commons.bukkit.command.argument;

public class StringArgument extends Argument<String> {

  private StringArgument(String argumentId) {
    super(argumentId, (value) -> true);
  }

  public static StringArgument of(String argumentId) {
    return new StringArgument(argumentId);
  }
  
}

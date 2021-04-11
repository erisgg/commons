package gg.eris.commons.bukkit.command.argument;

import java.util.function.Function;
import org.bukkit.entity.Player;

public class PlayerArgument extends Argument<Player> {

  private PlayerArgument(String argumentId) {
    super(argumentId, (value) -> value != null && value.isValid() && value.isOnline());
  }

  public static PlayerArgument of(String argumentId) {
    return new PlayerArgument(argumentId);
  }
}

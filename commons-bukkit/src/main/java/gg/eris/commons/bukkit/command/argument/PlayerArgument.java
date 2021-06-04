package gg.eris.commons.bukkit.command.argument;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class PlayerArgument extends Argument<Player> {

  private PlayerArgument(String argumentId) {
    super(
        argumentId,
        Player.class,
        Bukkit::getPlayer,
        value -> value != null && value.isValid() && value.isOnline(),
        value -> true
    );
  }

  public static PlayerArgument of(String argumentId) {
    return new PlayerArgument(argumentId);
  }

  @Override
  public boolean isSimilar(Argument<?> other) {
    return super.isSimilar(other) || other instanceof StringArgument
        || other instanceof LiteralArgument;
  }
}

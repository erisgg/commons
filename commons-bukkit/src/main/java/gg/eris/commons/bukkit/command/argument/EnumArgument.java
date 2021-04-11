package gg.eris.commons.bukkit.command.argument;

import java.util.function.Function;
import net.minecraft.server.v1_8_R3.EntityInsentient.EnumEntityPositionType;

public class EnumArgument<E extends Enum<E>> extends Argument<E> {

  public EnumArgument(String argumentId, Function<Enum<E>, Boolean> matcher) {
    super(argumentId, matcher);
  }
}

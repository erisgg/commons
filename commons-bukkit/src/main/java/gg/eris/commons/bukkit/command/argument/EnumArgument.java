package gg.eris.commons.bukkit.command.argument;

import java.util.function.Function;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.minecraft.server.v1_8_R3.ChatHoverable;
import net.minecraft.server.v1_8_R3.EntityInsentient.EnumEntityPositionType;

public class EnumArgument<E extends Enum<E>> extends Argument<E> {

  private EnumArgument(String argumentId, Class<E> enumClass) {
    super(argumentId, value -> true);
  }
}

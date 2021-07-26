package gg.eris.commons.bukkit.impl.tablist;

import gg.eris.commons.bukkit.text.TextMessage;
import gg.eris.commons.bukkit.util.PlayerUtil;
import java.lang.reflect.Field;
import lombok.experimental.UtilityClass;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.entity.Player;

@UtilityClass
public class TablistUtil {

  private static final Field PACKET_FIELD_B;

  static {
    Field field = null;
    try {
      field = PacketPlayOutPlayerListHeaderFooter.class.getDeclaredField("b");
      field.setAccessible(true);
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    }
    PACKET_FIELD_B = field;
  }

  public static void sendHeaderFooter(Player player, TextMessage header, TextMessage footer) {
    PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter(
        IChatBaseComponent.ChatSerializer.a(header.getJsonMessage()));

    try {
      PACKET_FIELD_B.set(packet, IChatBaseComponent.ChatSerializer.a(footer.getJsonMessage()));
    } catch (Exception e) {
      e.printStackTrace();
    }

    PlayerUtil.getHandle(player).playerConnection.sendPacket(packet);
  }

}

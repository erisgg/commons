package gg.eris.commons.bukkit.player.nickname;

import gg.eris.commons.bukkit.ErisBukkitCommonsPlugin;
import gg.eris.commons.bukkit.player.ErisPlayer;
import gg.eris.commons.bukkit.util.PlayerUtil;
import java.util.Locale;
import java.util.UUID;
import lombok.experimental.UtilityClass;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@UtilityClass
public class PlayerNicknamePipeline {

  public static void updatePlayer(ErisPlayer player) {
    Player playerHandle = player.getHandle();
    if (playerHandle == null) {
      return;
    }

    EntityPlayer handle = PlayerUtil.getHandle(playerHandle);

    // All the packets to send
    PacketPlayOutPlayerInfo removePacket =
        new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER,
            handle);
    PacketPlayOutPlayerInfo addPacket = new PacketPlayOutPlayerInfo(
        PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, handle);
    PacketPlayOutEntityDestroy destroyPacket = new PacketPlayOutEntityDestroy(handle.getId());
    PacketPlayOutNamedEntitySpawn spawnPacket = new PacketPlayOutNamedEntitySpawn(handle);

    for (Player other : Bukkit.getServer().getOnlinePlayers()) {
      if (other.getUniqueId().equals(playerHandle.getUniqueId())) {
        continue;
      }

      EntityPlayer thatPlayer = PlayerUtil.getHandle(other);
      PlayerConnection connection = thatPlayer.playerConnection;
      connection.sendPacket(removePacket);
      connection.sendPacket(addPacket);
      connection.sendPacket(destroyPacket);
      connection.sendPacket(spawnPacket);
    }

    ErisBukkitCommonsPlugin.getInstance().getTablistController().refreshForAll();
  }

  public static boolean isValidNickName(String name) {
    UUID uuid = ErisBukkitCommonsPlugin.getInstance().getOfflineDataManager().getUuid(name);
    if (uuid != null) {
      return false;
    }

    String nameLower = name.toLowerCase(Locale.ROOT);
    if (PlayerNicknameDisallowedList.DISALLOWED.contains(nameLower)) {
      return false;
    }

    return !name.contains("nigg") && !name.contains("fag") && !name.contains("chink");
  }
}

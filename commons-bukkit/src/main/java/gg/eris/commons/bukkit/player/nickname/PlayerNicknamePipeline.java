package gg.eris.commons.bukkit.player.nickname;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gg.eris.commons.bukkit.ErisBukkitCommonsPlugin;
import gg.eris.commons.bukkit.player.ErisPlayer;
import gg.eris.commons.bukkit.util.PlayerUtil;
import gg.eris.commons.core.impl.redis.RedisWrapperImpl;
import gg.eris.commons.core.util.UUIDUtil;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@UtilityClass
public class PlayerNicknamePipeline {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  public static final String NICKNAME_SET = "nicknames";

  public static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{3,16}$");

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
    if (name.length() < 3 || name.length() > 16) {
      return false;
    }

    UUID uuid = ErisBukkitCommonsPlugin.getInstance().getOfflineDataManager().getUuid(name);
    if (uuid != null) {
      return false;
    }

    String nameLower = name.toLowerCase(Locale.ROOT);
    if (PlayerNicknameDisallowedList.DISALLOWED.contains(nameLower)) {
      return false;
    }

    if (!NAME_PATTERN.matcher(name).find()) {
      return false;
    }

    return !name.contains("nigg") && !name.contains("fag") && !name.contains("chink") &&
        !name.contains("penis") &&
        !name.contains("cock") &&
        !name.contains("c0ck") &&
        !name.contains("fuck") &&
        !name.contains("shit") &&
        !name.contains("pussy") &&
        !name.contains("fanny") &&
        !name.contains("vagina");
  }

  public static JsonNode getNickname(ErisPlayer player) {
    String key = getJsonKey(player);
    return ErisBukkitCommonsPlugin.getInstance().getRedisWrapper().get(key);
  }

  public static void saveNickname(ErisPlayer player) {
    String key = getJsonKey(player);

    if (!player.getNicknameProfile().isNicked()) {
      JsonNode old = ErisBukkitCommonsPlugin.getInstance().getRedisWrapper().get(key);
      if (old != null) {
        ErisBukkitCommonsPlugin.getInstance().getRedisWrapper().unset(key);
        ErisBukkitCommonsPlugin.getInstance().getRedisWrapper()
            .removeFromSet(NICKNAME_SET, old.get("name").asText().toLowerCase(Locale.ROOT));
      }
    } else {
      ObjectNode node = MAPPER.createObjectNode()
          .put("name", player.getNicknameProfile().getNickName());

      if (player.getNicknameProfile().getSkin() != null) {
        node.put("skin_key", player.getNicknameProfile().getSkin().getKey());
        node.put("skin_value", player.getNicknameProfile().getSkin().getValue());
      }

      ErisBukkitCommonsPlugin.getInstance().getRedisWrapper().set(key, node);
      ErisBukkitCommonsPlugin.getInstance().getRedisWrapper().addToSet(NICKNAME_SET,
          player.getNicknameProfile().getNickName().toLowerCase(Locale.ROOT));
    }


  }

  private static String getJsonKey(ErisPlayer player) {
    return UUIDUtil.toDashlessString(player.getUniqueId()) + "_nickname";
  }

}

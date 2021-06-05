package gg.eris.commons.bukkit.util;

import gg.eris.commons.core.util.MathUtil;
import java.util.Collection;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ParticleUtil {

  public static void spawnSpiralForPlayer(EnumParticle particleType, boolean longDistance,
      int particlesPerPoint, Location origin, int spiralPointCount,
      double spiralHeight, double spiralRadius, int spiralRotations, Player player) {

    double step = 2 * Math.PI * spiralRotations / spiralPointCount;

    for (int i = 0; i < spiralPointCount; i++) {
      double particleAngle = step * i;

      double factor = (double) i / (spiralPointCount - 1);

      Location particleLocation = new Location(origin.getWorld(),
          origin.getX() + Math.cos(particleAngle) * spiralRadius,
          origin.getY() + MathUtil.lerp(0, spiralHeight, factor),
          origin.getZ() + Math.sin(particleAngle) * spiralRadius);

      float particleData = 0;

      PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
          particleType, longDistance,
          (float) particleLocation.getX(),
          (float) particleLocation.getY(),
          (float) particleLocation.getZ(),
          0, 0, 0, particleData, particlesPerPoint, 0);

      ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
  }

  public static void spawnSpiralForPlayers(EnumParticle particleType, boolean longDistance,
      int particlesPerPoint, Location origin, int spiralPointCount,
      double spiralHeight, double spiralRadius, int spiralRotations, Collection<? extends Player> players) {

    for (Player player : players) {
      spawnSpiralForPlayer(particleType, longDistance, particlesPerPoint, origin, spiralPointCount,
          spiralHeight, spiralRadius, spiralRotations, player);
    }
  }

  public static void spawnSpiralForAllPlayers(EnumParticle particleType, boolean longDistance,
      int particlesPerPoint, Location origin, int spiralPointCount,
      double spiralHeight, double spiralRadius, int spiralRotations) {

    spawnSpiralForPlayers(particleType, longDistance, particlesPerPoint, origin, spiralPointCount,
        spiralHeight, spiralRadius, spiralRotations, Bukkit.getServer().getOnlinePlayers());
  }
}

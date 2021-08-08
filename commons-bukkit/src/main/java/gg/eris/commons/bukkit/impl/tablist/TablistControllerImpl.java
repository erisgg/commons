package gg.eris.commons.bukkit.impl.tablist;

import gg.eris.commons.bukkit.ErisBukkitCommonsPlugin;
import gg.eris.commons.bukkit.player.ErisPlayer;
import gg.eris.commons.bukkit.player.ErisPlayerManager;
import gg.eris.commons.bukkit.rank.Rank;
import gg.eris.commons.bukkit.rank.RankRegistry;
import gg.eris.commons.bukkit.tablist.TablistController;
import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.PlayerUtil;
import java.util.function.BiFunction;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

public final class TablistControllerImpl implements TablistController {

  private final ErisBukkitCommonsPlugin plugin;
  private final ErisPlayerManager erisPlayerManager;
  private final RankRegistry rankRegistry;
  private String header;
  private String footer;
  private BiFunction<ErisPlayer, ErisPlayer, String> displayNameFunction;

  public TablistControllerImpl(ErisBukkitCommonsPlugin plugin) {
    this.plugin = plugin;
    this.erisPlayerManager = plugin.getErisPlayerManager();
    this.rankRegistry = plugin.getRankRegistry();
  }

  @Override
  public void setDisplayNameFunction(
      BiFunction<ErisPlayer, ErisPlayer, String> displayNameFunction) {
    this.displayNameFunction = displayNameFunction;
    updateAll(true);
  }

  @Override
  public BiFunction<ErisPlayer, ErisPlayer, String> getDisplayNameFunction() {
    return this.displayNameFunction;
  }

  @Override
  public void setHeader(String header) {
    this.header = header;
    updateAll(false);
  }

  @Override
  public void setFooter(String footer) {
    this.footer = footer;
    updateAll(false);
  }

  @Override
  public void updateDisplayName(Player player) {
    updateDisplayNameOfPlayer(player);
  }

  @Override
  public void updateAllDisplayNames() {
    for (Player player : Bukkit.getOnlinePlayers()) {
      updateDisplayName(player);
    }
  }

  private void updateAll(boolean displayNames) {
    if (displayNames) {
      for (Player player : Bukkit.getOnlinePlayers()) {
        updateHeaderFooter(player);
        updateDisplayNameOfPlayer(player);
      }
    } else {
      for (Player player : Bukkit.getOnlinePlayers()) {
        updateHeaderFooter(player);
      }
    }
  }

  public void onJoin(Player player) {
    updateHeaderFooter(player);
    initializeTeams(player);
    for (Player other : Bukkit.getOnlinePlayers()) {
      if (other != player) {
        addPlayer(other, player);
      }
    }

    // Update it as soon as possible but a few times for security (does not work instantly on join)
    new BukkitRunnable() {
      int count = 0;
      @Override
      public void run() {
        updateDisplayNameOfPlayer(player);
        updateDisplayNamesForPlayer(player);
        if (++count > 15) {
          cancel();
        }
      }
    }.runTaskTimer(this.plugin, 0L, 2L);
  }

  public void onQuit(Player player) {
    for (Player other : Bukkit.getOnlinePlayers()) {
      if (other != player) {
        removePlayer(other, player);
      }
    }
  }

  public void initializeTeams(Player player) {
    Scoreboard scoreboard = player.getScoreboard();

    for (Rank rank : this.rankRegistry.values()) {
      scoreboard.registerNewTeam("" + rank.getPriority());
    }

    for (Player handle : Bukkit.getOnlinePlayers()) {
      addPlayer(player, handle);
    }
  }

  public void addPlayer(Player player, Player addendum) {
    ErisPlayer erisPlayer = this.erisPlayerManager.getPlayer(addendum);
    player.getScoreboard().getTeam("" + erisPlayer.getPriorityRank().getPriority())
        .addEntry(addendum.getName());
  }

  public void removePlayer(Player player, Player removal) {
    ErisPlayer erisPlayer = this.erisPlayerManager.getPlayer(removal);
    player.getScoreboard().getTeam("" + erisPlayer.getPriorityRank().getPriority())
        .removeEntry(removal.getName());
  }

  public void updateHeaderFooter(Player handle) {
    TablistUtil.sendHeaderFooter(
        handle,
        TextController.parse(this.header),
        TextController.parse(this.footer)
    );
  }

  private void updateDisplayNameOfPlayer(Player handle) {
    ErisPlayer player = this.erisPlayerManager.getPlayer(handle);
    for (Player otherHandle : Bukkit.getOnlinePlayers()) {
      ErisPlayer other = this.erisPlayerManager.getPlayer(otherHandle);
      String tablistName = this.displayNameFunction != null ? this.displayNameFunction.apply(player,
          other) : CC.WHITE + player.getName();

      PacketPlayOutPlayerInfo packet;
      if (tablistName != null) {
        packet = new PacketPlayOutPlayerInfo();
        packet.a = EnumPlayerInfoAction.UPDATE_DISPLAY_NAME;
        packet.b.add(createPlayerInfoData(packet, handle, tablistName));
      } else {
        packet = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER,
            PlayerUtil.getHandle(handle));
      }
      PlayerUtil.getHandle(otherHandle).playerConnection.sendPacket(packet);
    }
  }

  // Only needs to be called on join, as any next joins will be sent in the other method
  private void updateDisplayNamesForPlayer(Player handle) {
    ErisPlayer player = this.erisPlayerManager.getPlayer(handle);
    EntityPlayer entityHandle = PlayerUtil.getHandle(handle);
    for (Player otherHandle : Bukkit.getOnlinePlayers()) {
      ErisPlayer other = this.erisPlayerManager.getPlayer(otherHandle);
      String tablistName = this.displayNameFunction != null ? this.displayNameFunction.apply(other,
          player) : CC.WHITE + other.getName();

      PacketPlayOutPlayerInfo packet;
      if (tablistName != null) {
        packet = new PacketPlayOutPlayerInfo();
        packet.a = EnumPlayerInfoAction.UPDATE_DISPLAY_NAME;
        packet.b.add(createPlayerInfoData(packet, otherHandle, tablistName));
      } else {
        packet = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER,
            PlayerUtil.getHandle(otherHandle));
      }
      entityHandle.playerConnection.sendPacket(packet);
    }
  }

  private static PacketPlayOutPlayerInfo.PlayerInfoData createPlayerInfoData(
      PacketPlayOutPlayerInfo packet, Player player, String name) {
    EntityPlayer handle = PlayerUtil.getHandle(player);

    return packet.constructData(
        handle.getProfile(),
        1,
        handle.playerInteractManager.getGameMode(),
        IChatBaseComponent.ChatSerializer.a("[{\"text\":\"" + name + "\"}]")
    );
  }

}

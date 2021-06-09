package gg.eris.commons.bukkit.impl.player;

import com.google.common.collect.Maps;
import gg.eris.commons.bukkit.ErisBukkitCommonsPlugin;
import gg.eris.commons.bukkit.player.ErisPlayer;
import gg.eris.commons.bukkit.player.ErisPlayerManager;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;

public final class ErisPlayerManagerImpl implements ErisPlayerManager {

  public ErisPlayerManagerImpl(ErisBukkitCommonsPlugin plugin) {
    this.players = Maps.newHashMap();
    Bukkit.getPluginManager().registerEvents(new ErisPlayerManagerListener(this), plugin);
  }

  private final Map<UUID, ErisPlayer> players;

  protected void loadPlayer(UUID uuid) {

  }

  public <T extends ErisPlayer> T addPlayer(T player) {
    this.players.put(player.getUniqueId(), player);
    return player;
  }

  public <T extends ErisPlayer> T removePlayer(ErisPlayer player) {
    return (T) this.players.remove(player.getUniqueId());
  }

  @Override
  public <T extends ErisPlayer> T getPlayer(UUID uuid) {
    return (T) this.players.get(uuid);
  }
}

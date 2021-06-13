package gg.eris.commons.bukkit.impl.player;

import com.google.common.collect.Maps;
import gg.eris.commons.bukkit.ErisBukkitCommonsPlugin;
import gg.eris.commons.bukkit.player.ErisPlayer;
import gg.eris.commons.bukkit.player.ErisPlayerManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;

public final class ErisPlayerManagerImpl implements ErisPlayerManager {

  public ErisPlayerManagerImpl(ErisBukkitCommonsPlugin plugin) {
    this.players = Maps.newHashMap();
    Bukkit.getPluginManager().registerEvents(new ErisPlayerManagerListener(plugin, this), plugin);
  }

  private final Map<UUID, ErisPlayer> players;

  protected void loadPlayer(UUID uuid) {

  }

  protected void unloadPlayer(UUID uuid) {

  }


  @Override
  public <T extends ErisPlayer> T getPlayer(UUID uuid) {
    return (T) this.players.get(uuid);
  }

  @Override
  public <T extends ErisPlayer> Collection<T> getPlayers() {
    return this.players.values().stream().map(player -> (T) player)
        .collect(Collectors.toList());
  }
}

package gg.eris.commons.bukkit.impl.player;

import com.google.common.collect.Maps;
import gg.eris.commons.bukkit.player.ErisPlayer;
import gg.eris.commons.bukkit.player.ErisPlayerManager;
import io.netty.channel.epoll.Epoll;
import java.util.Map;
import java.util.UUID;

public final class ErisPlayerManagerImpl implements ErisPlayerManager {

  public ErisPlayerManagerImpl() {
    this.players = Maps.newHashMap();
  }

  private final Map<UUID, ErisPlayer> players;

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

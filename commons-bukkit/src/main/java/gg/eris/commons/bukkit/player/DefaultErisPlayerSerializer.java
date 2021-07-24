package gg.eris.commons.bukkit.player;

import com.fasterxml.jackson.databind.JsonNode;
import gg.eris.commons.bukkit.ErisBukkitCommons;
import java.util.List;
import org.bukkit.entity.Player;

public final class DefaultErisPlayerSerializer extends ErisPlayerSerializer<ErisPlayer> {

  private final ErisBukkitCommons plugin;

  public DefaultErisPlayerSerializer(ErisBukkitCommons plugin) {
    super(ErisPlayer.class);
    this.plugin = plugin;
  }

  @Override
  public ErisPlayer newPlayer(Player player) {
    long time = System.currentTimeMillis();
    return new ErisPlayer(
        player.getUniqueId(),
        player.getName(),
        List.of(player.getName()),
        time,
        time,
        plugin.getRankRegistry().DEFAULT,
        List.of()
    );
  }

  @Override
  public ErisPlayer constructPlayer(JsonNode node) {
    return null;
  }

  @Override
  protected JsonNode appendFields(ErisPlayer player, JsonNode node) {
    return node;
  }

}

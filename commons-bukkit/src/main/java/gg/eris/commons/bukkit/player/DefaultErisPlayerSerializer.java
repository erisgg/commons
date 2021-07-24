package gg.eris.commons.bukkit.player;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gg.eris.commons.bukkit.ErisBukkitCommons;
import gg.eris.commons.bukkit.player.ErisPlayer.DefaultData;
import java.io.IOException;
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
    return new ErisPlayer(DefaultData.of(
        player.getUniqueId(),
        player.getName(),
        List.of(player.getName()),
        time,
        time,
        this.plugin.getRankRegistry().DEFAULT,
        List.of()
    ));
  }

  @Override
  public ErisPlayer deserializePlayer(JsonNode node) {
    try {
      return new ErisPlayer(DefaultData.fromNode(node));
    } catch (IOException err) {
      err.printStackTrace();
    }
    return null;
  }

  @Override
  protected ObjectNode appendFields(ErisPlayer player, ObjectNode node) {
    return node;
  }

}

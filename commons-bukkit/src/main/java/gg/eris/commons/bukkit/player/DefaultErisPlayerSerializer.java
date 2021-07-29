package gg.eris.commons.bukkit.player;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gg.eris.commons.bukkit.ErisBukkitCommons;
import gg.eris.commons.bukkit.player.ErisPlayer.DefaultData;
import java.io.IOException;
import org.bukkit.entity.Player;

public final class DefaultErisPlayerSerializer extends ErisPlayerSerializer<ErisPlayer> {

  public DefaultErisPlayerSerializer() {
    super(ErisPlayer.class);
  }

  @Override
  public ErisPlayer newPlayer(Player player) {
    return new ErisPlayer(DefaultData.newData(player));
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

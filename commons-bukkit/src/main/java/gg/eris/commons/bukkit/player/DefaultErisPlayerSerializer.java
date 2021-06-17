package gg.eris.commons.bukkit.player;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import org.bukkit.entity.Player;

public final class DefaultErisPlayerSerializer extends ErisPlayerSerializer<ErisPlayer> {

  public DefaultErisPlayerSerializer() {
    super(ErisPlayer.class);
  }


  @Override
  public ErisPlayer newPlayer(Player player) {
    long time = System.currentTimeMillis();
    return new ErisPlayer(
        player.getUniqueId(),
        player.getName(),
        List.of(player.getName()),
        time,
        time
    );
  }

  @Override
  protected JsonNode appendFields(ErisPlayer player, JsonNode node) {
    return node;
  }

}

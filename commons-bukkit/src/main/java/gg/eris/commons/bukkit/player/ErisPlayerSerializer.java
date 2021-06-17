package gg.eris.commons.bukkit.player;

import com.fasterxml.jackson.databind.JsonNode;
import org.bukkit.entity.Player;

public interface ErisPlayerSerializer<T extends ErisPlayer> {

  Class<T> getErisPlayerClass();

  T newPlayer(Player player);

  JsonNode toNode(ErisPlayer player);

  T fromNode(JsonNode node);

}

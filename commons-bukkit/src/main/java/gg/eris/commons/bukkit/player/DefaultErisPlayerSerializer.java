package gg.eris.commons.bukkit.player;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.entity.Player;

public final class DefaultErisPlayerSerializer implements ErisPlayerSerializer<ErisPlayer> {

  private final ObjectMapper mapper;

  public DefaultErisPlayerSerializer() {
    this.mapper = new ObjectMapper();
  }

  @Override
  public Class<ErisPlayer> getErisPlayerClass() {
    return ErisPlayer.class;
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
  public JsonNode toNode(ErisPlayer player) {
    ObjectNode node = mapper.createObjectNode();

    node.put("uuid", player.getUniqueId().toString())
        .put("name", player.getName())
        .put("first_login", player.getFirstLogin())
        .put("last_login", player.getLastLogin())
        .putArray("name_history");

    ArrayNode nameHistory = (ArrayNode) node.get("name_history");
    for (String name : player.getNameHistory()) {
      nameHistory.add(name);
    }

    return node;
  }

  @Override
  public ErisPlayer fromNode(JsonNode node) {
    ArrayNode namesNode = (ArrayNode) node.get("name_history");
    List<String> names = new ArrayList<>(namesNode.size());
    for (JsonNode jsonNode : namesNode) {
      names.add(jsonNode.asText());
    }

    return new ErisPlayer(
        UUID.fromString(node.get("uuid").asText()),
        node.get("name").asText(),
        names,
        node.get("first_login").asLong(),
        node.get("last_login").asLong()
    );
  }
}

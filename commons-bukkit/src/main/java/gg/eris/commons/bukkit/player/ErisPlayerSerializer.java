package gg.eris.commons.bukkit.player;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import org.bukkit.entity.Player;

public abstract class ErisPlayerSerializer<T extends ErisPlayer> {

  private final Class<T> erisPlayerClass;
  protected final ObjectMapper mapper;

  public ErisPlayerSerializer(Class<T> erisPlayerClass) {
    this.erisPlayerClass = erisPlayerClass;
    this.mapper = new ObjectMapper();
  }

  public abstract T newPlayer(Player player);

  protected abstract JsonNode appendFields(T player, JsonNode node);

  public final JsonNode toNode(ErisPlayer rootPlayer) {
    T player = cast(rootPlayer);

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

    return appendFields(player, node);
  }

  public final T cast(ErisPlayer player) {
    return this.erisPlayerClass.cast(player);
  }

}

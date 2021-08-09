package gg.eris.commons.bukkit.player;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gg.eris.commons.core.json.JsonUtil;
import java.util.Locale;
import org.bukkit.entity.Player;

public abstract class ErisPlayerSerializer<T extends ErisPlayer> {

  private final Class<T> erisPlayerClass;
  protected final ObjectMapper mapper;

  public ErisPlayerSerializer(Class<T> erisPlayerClass) {
    this.erisPlayerClass = erisPlayerClass;
    this.mapper = new ObjectMapper();
  }

  public abstract T newPlayer(Player player);

  public abstract T deserializePlayer(JsonNode node);

  protected abstract ObjectNode appendFields(T player, ObjectNode node);

  public final JsonNode serializePlayer(ErisPlayer rootPlayer) {
    T player = cast(rootPlayer);

    ObjectNode node = this.mapper.createObjectNode();

    node.put("uuid", player.getUniqueId().toString())
        .put("name", player.getName())
        .put("name_lower", player.getName().toLowerCase(Locale.ROOT))
        .put("first_login", player.getFirstLogin())
        .put("last_login", player.getLastLogin());

    JsonUtil.populateStringArray(node.putArray("name_history"), player.getNameHistory());

    return appendFields(player, node);
  }

  public final T cast(ErisPlayer player) {
    return this.erisPlayerClass.cast(player);
  }

}
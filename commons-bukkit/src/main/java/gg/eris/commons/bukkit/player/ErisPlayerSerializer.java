package gg.eris.commons.bukkit.player;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gg.eris.commons.core.json.JsonUtil;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
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

    ObjectNode node = mapper.createObjectNode();

    node.put("uuid", player.getUniqueId().toString())
        .put("name", player.getName())
        .put("first_login", player.getFirstLogin())
        .put("last_login", player.getLastLogin())
        .put("rank", player.getRank().getIdentifier().getValue());

    JsonUtil.populateStringArray(node.putArray("name_history"), player.getNameHistory());
    JsonUtil.populateStringArray(node.putArray("permissions"),
        player.getPermissions()
            .stream()
            .map(permission -> permission.getIdentifier().toString())
            .collect(Collectors.toList())
    );

    return appendFields(player, node);
  }

  public final T cast(ErisPlayer player) {
    return this.erisPlayerClass.cast(player);
  }

}
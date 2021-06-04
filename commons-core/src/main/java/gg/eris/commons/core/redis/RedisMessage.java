package gg.eris.commons.core.redis;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.UUID;
import lombok.Value;

@Value
public class RedisMessage {

  UUID senderUuid;
  String channel;
  JsonNode payload;

  public static RedisMessage of(String channel, JsonNode payload) {
    String uuidString = payload.get("uuid").asText();
    if (uuidString == null) {
      throw new IllegalArgumentException("No uuid on incoming payload");
    } else {
      return new RedisMessage(UUID.fromString(uuidString), channel, payload);
    }
  }

}

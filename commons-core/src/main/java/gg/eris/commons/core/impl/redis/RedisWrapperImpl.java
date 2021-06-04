package gg.eris.commons.core.impl.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gg.eris.commons.core.redis.RedisMessage;
import gg.eris.commons.core.redis.RedisPublisher;
import gg.eris.commons.core.redis.RedisSubscriber;
import gg.eris.commons.core.redis.RedisWrapper;
import gg.eris.commons.core.util.Validate;
import java.util.UUID;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

public class RedisWrapperImpl implements RedisWrapper {

  private final String uuidString;
  private final JedisPool pool;
  private final JsonMapper mapper;

  public RedisWrapperImpl(String address, int port) {
    this.uuidString = UUID.randomUUID().toString();
    this.pool = new JedisPool(address, port);
    this.mapper = new JsonMapper();
  }

  @Override
  public void subscribe(RedisSubscriber subscriber) {
    try (Jedis jedis = this.pool.getResource()) {
      jedis.subscribe(new JedisPubSub() {
        @Override
        public void onMessage(String channel, String message) {
          if (subscriber.isChannel(channel)) {
            try {
              subscriber
                  .accept(RedisMessage.of(channel, RedisWrapperImpl.this.mapper.readTree(message)));
            } catch (JsonProcessingException err) {
              err.printStackTrace();
            }
          }
        }
      });
    }
  }

  @Override
  public void publish(RedisPublisher publisher) {
    JsonNode prePayloadJson = publisher.getPayload();
    Validate.isTrue(!prePayloadJson.has("uuid"), "uuid field already set");

    ObjectNode payloadJson = prePayloadJson.deepCopy();
    payloadJson.put("uuid", this.uuidString);
    String payload = payloadJson.toString();
    try (Jedis jedis = this.pool.getResource()) {
      for (String channel : publisher.getChannels()) {
        jedis.publish(channel, payload);
      }
    }
  }

}

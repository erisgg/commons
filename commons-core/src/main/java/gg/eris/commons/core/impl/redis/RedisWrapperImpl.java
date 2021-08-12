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
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.params.SetParams;

public class RedisWrapperImpl implements RedisWrapper {

  private static final int TIMEOUT = 5000;

  private final String uuidString;
  private final JedisPool pool;
  private final JsonMapper mapper;

  public RedisWrapperImpl(String password, String host, int port) {
    this.uuidString = UUID.randomUUID().toString();
    this.pool = password != null && !password.isEmpty() ?
        new JedisPool(new GenericObjectPoolConfig<>(), host, port, TIMEOUT, password) :
        new JedisPool(new GenericObjectPoolConfig<>(), host, port, TIMEOUT);

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
      }, subscriber.getChannels().toArray(new String[0]));
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

  public void set(String key, JsonNode value) {
    try (Jedis jedis = this.pool.getResource()) {
      jedis.set(key, value.toString());
    }
  }

  public void set(String key, JsonNode value, SetParams params) {
    try (Jedis jedis = this.pool.getResource()) {
      jedis.set(key, value.toString(), params);
    }
  }

  @Override
  public void unset(String key) {
    try (Jedis jedis = this.pool.getResource()) {
      jedis.del(key);
    }
  }

  @Override
  public void addToSet(String set, String value) {
    try (Jedis jedis = this.pool.getResource()) {
      jedis.sadd(set, value);
    }
  }

  @Override
  public void removeFromSet(String set, String value) {
    try (Jedis jedis = this.pool.getResource()) {
      jedis.srem(set, value);
    }
  }

  @Override
  public Set<String> querySet(String set) {
    try (Jedis jedis = this.pool.getResource()) {
      return jedis.smembers(set);
    }
  }

  @Override
  public boolean setContainsValue(String set, String value) {
    try (Jedis jedis = this.pool.getResource()) {
      return jedis.smismember(set, value).get(0);
    }
  }

  public JsonNode get(String key) {
    try (Jedis jedis = this.pool.getResource()) {
      String value = jedis.get(key);
      if (value == null) {
        return null;
      } else {
        return this.mapper.readTree(value);
      }
    } catch (JsonProcessingException err) {
      err.printStackTrace();
      return null;
    }
  }

}

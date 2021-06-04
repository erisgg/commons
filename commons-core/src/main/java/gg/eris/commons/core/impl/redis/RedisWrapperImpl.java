package gg.eris.commons.core.impl.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import gg.eris.commons.core.redis.RedisMessage;
import gg.eris.commons.core.redis.RedisPublisher;
import gg.eris.commons.core.redis.RedisSubscriber;
import gg.eris.commons.core.redis.RedisWrapper;
import java.util.UUID;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

public class RedisWrapperImpl implements RedisWrapper {

  private final UUID uuid;
  private final JedisPool pool;
  private final JsonMapper mapper;

  public RedisWrapperImpl(String address, int port) {
    this.uuid = UUID.randomUUID();
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
    String payload = publisher.getPayload().toString();
    try (Jedis jedis = this.pool.getResource()) {
      for (String channel : publisher.getChannels()) {
        jedis.publish(channel, payload);
      }
    }
  }

}

package gg.eris.commons.core.impl.redis;

import gg.eris.commons.core.redis.RedisPublisher;
import gg.eris.commons.core.redis.RedisSubscriber;
import gg.eris.commons.core.redis.RedisWrapper;
import java.util.UUID;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisWrapperImpl implements RedisWrapper {

  private final UUID uuid;
  private final JedisPool pool;

  public RedisWrapperImpl(String address, int port) {
    this.uuid = UUID.randomUUID();
    this.pool = new JedisPool(address, port);
  }

  @Override
  public void subscribe(RedisSubscriber subscriber) {
    try (Jedis jedis = this.pool.getResource()) {
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

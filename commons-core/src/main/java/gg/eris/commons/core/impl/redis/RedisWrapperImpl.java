package gg.eris.commons.core.impl.redis;

import gg.eris.commons.core.redis.RedisPublisher;
import gg.eris.commons.core.redis.RedisSubscriber;
import gg.eris.commons.core.redis.RedisWrapper;
import lombok.Getter;
import redis.clients.jedis.JedisPool;

public class RedisWrapperImpl implements RedisWrapper {

  private final JedisPool pool;

  public RedisWrapperImpl(String address, int port) {
    this.pool = new JedisPool(address, port);
  }

  @Override
  public void subscribe(RedisSubscriber subscriber) {

  }

  @Override
  public void publish(RedisPublisher publisher) {

  }
}

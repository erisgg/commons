package gg.eris.commons.core.redis;

import gg.eris.commons.core.impl.redis.RedisWrapperImpl;

/**
 * The {@link RedisWrapper} is a wrapper around the {@link redis.clients.jedis.JedisPool}. It leaves
 * all thread management to the user and simply does as told. It provides methods for pub/sub
 * handling, as well as basic caching.
 */
public interface RedisWrapper {

  int DEFAULT_PORT = 6379;

  static RedisWrapper newWrapper(String password, String address, int port) {
    return new RedisWrapperImpl(password, address, port);
  }

  /**
   * Subscribes to a {@link RedisSubscriber}. Will lock thread.
   *
   * @param subscriber is the {@link RedisSubscriber} to subscribe to
   */
  void subscribe(RedisSubscriber subscriber);

  /**
   * Publishes a {@link RedisPublisher}. Will lock thread.
   *
   * @param publisher is the {@link RedisPublisher} to publish
   */
  void publish(RedisPublisher publisher);

}

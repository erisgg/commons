package gg.eris.commons.core.redis;

import com.fasterxml.jackson.databind.JsonNode;
import gg.eris.commons.core.impl.redis.RedisWrapperImpl;
import java.util.Set;
import redis.clients.jedis.params.SetParams;

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

  /**
   * Sets something in cache
   *
   * @param key   is the key to set
   * @param value is the value
   */
  void set(String key, JsonNode value);

  /**
   * Sets something in cache
   *
   * @param key   is the key to set
   * @param value is the value
   */
  void set(String key, JsonNode value, SetParams setParams);

  /**
   * Unsets a key
   *
   * @param key is the key to unset
   */
  void unset(String key);

  void addToSet(String set, String value);

  void removeFromSet(String set, String value);

  Set<String> querySet(String set);

  boolean setContainsValue(String set, String value);

  /**
   * Gets a value at a key
   *
   * @param key is the key to get
   * @return the JsonNode
   */
  JsonNode get(String key);

}

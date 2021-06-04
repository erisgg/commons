package gg.eris.commons.core.redis;

@FunctionalInterface
public interface RedisSubscriberCallback {

  enum Priority {
    VERY_HIGH,
    HIGH,
    NORMAL,
    LOW,
    VERY_LOW;
  }

  /**
   * Consumes a {@link RedisMessage}
   * @param message is the {@link RedisMessage} that was received
   */
  void accept(RedisMessage message);

}

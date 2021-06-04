package gg.eris.commons.core.redis;

import java.util.Arrays;

@FunctionalInterface
public interface RedisSubscriberCallback {

  enum Priority {
    VERY_HIGH,
    HIGH,
    NORMAL,
    LOW,
    VERY_LOW;

    private static final Priority[] priorityOrder = {
        VERY_HIGH,
        HIGH,
        NORMAL,
        LOW,
        VERY_LOW
    };

    public static Priority[] priorityOrder() {
      return Arrays.copyOf(priorityOrder, priorityOrder.length);
    }
  }

  /**
   * Consumes a {@link RedisMessage}
   * @param message is the {@link RedisMessage} that was received
   */
  void accept(RedisMessage message);

}

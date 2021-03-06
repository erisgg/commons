package gg.eris.commons.core.redis;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import gg.eris.commons.core.redis.RedisSubscriberCallback.Priority;
import gg.eris.commons.core.util.Validate;
import java.util.Set;
import lombok.Getter;

public final class RedisSubscriber {

  @Getter
  private final Set<String> channels;
  private final Multimap<RedisSubscriberCallback.Priority, RedisSubscriberCallback> callbacks;

  private RedisSubscriber(Set<String> channels,
      Multimap<Priority, RedisSubscriberCallback> callbacks) {
    this.channels = channels;
    this.callbacks = callbacks;
  }

  public void accept(RedisMessage message) {
    for (Priority priority : Priority.priorityOrder()) {
      callbacks.get(priority).forEach(callback -> callback.accept(message));
    }
  }

  public boolean isChannel(String channel) {
    return this.channels.contains(channel);
  }

  public static Builder builder(String... channels) {
    return new Builder(channels);
  }

  public static RedisSubscriber of(RedisSubscriberCallback callback, String... channels) {
    return builder(channels).withCallback(callback).build();
  }

  public static class Builder {

    private final Set<String> channels;
    private final Multimap<Priority, RedisSubscriberCallback> callbacks;

    private Builder(String... channels) {
      Validate.notEmpty(channels);
      this.channels = ImmutableSet.copyOf(channels);
      this.callbacks = ArrayListMultimap.create();
    }

    public Builder withCallback(RedisSubscriberCallback callback) {
      return withCallback(Priority.NORMAL, callback);
    }

    public Builder withCallback(Priority priority, RedisSubscriberCallback callback) {
      this.callbacks.put(priority, callback);
      return this;
    }

    public RedisSubscriber build() {
      Validate.notEmpty(this.callbacks);
      return new RedisSubscriber(this.channels, Multimaps.unmodifiableMultimap(this.callbacks));
    }
  }


}

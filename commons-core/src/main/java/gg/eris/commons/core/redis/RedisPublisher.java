package gg.eris.commons.core.redis;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class RedisPublisher {

  private final Set<String> channels;

  public static RedisPublisher.Builder builder(String... channels) {
    return new Builder(channels);
  }

  public static class Builder {

    private final Set<String> channels;

    private Builder(String... channels) {
      this.channels = ImmutableSet.copyOf(channels);
    }

    public RedisPublisher build() {
      return new RedisPublisher(this.channels);
    }

  }

}

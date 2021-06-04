package gg.eris.commons.core.redis;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableSet;
import gg.eris.commons.core.util.Validate;
import java.util.Set;
import lombok.Getter;

public final class RedisPublisher {

  @Getter
  private final JsonNode payload;
  @Getter
  private final Set<String> channels;

  public RedisPublisher(JsonNode payload, Set<String> channels) {
    this.payload = payload;
    this.channels = channels;
  }

  public static RedisPublisher of(JsonNode payload, String... channels) {
    return builder(payload, channels).build();
  }

  public static RedisPublisher.Builder builder(JsonNode payload, String... channels) {
    return new Builder(payload, channels);
  }

  public static class Builder {

    private final Set<String> channels;
    private final JsonNode payload;

    private Builder(JsonNode payload, String... channels) {
      Validate.notEmpty(channels);
      this.payload = payload;
      this.channels = ImmutableSet.copyOf(channels);
    }

    public RedisPublisher build() {
      return new RedisPublisher(this.payload, this.channels);
    }

  }

}

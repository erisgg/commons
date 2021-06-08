package gg.eris.commons.core.util;

import gg.eris.commons.core.util.Validate;
import java.util.UUID;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UUIDUtil {

  public static UUID fromLong(long[] value) {
    Validate.isTrue(value.length == 2, "long array must be of length 2);");
    return fromLong(value[0], value[1]);
  }

  public static UUID fromLong(long mostSignificantBits, long leastSignificantBits) {
    return new UUID(mostSignificantBits, leastSignificantBits);
  }

  public static long[] toLongs(UUID uuid) {
    return new long[] {
        uuid.getMostSignificantBits(),
        uuid.getLeastSignificantBits()
    };
  }

}

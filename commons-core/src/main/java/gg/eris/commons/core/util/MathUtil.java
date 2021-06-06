package gg.eris.commons.core.util;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class MathUtil {

  /**
   * Performs linear interpolation between a and b.
   * @param a The starting value.
   * @param b The ending value.
   * @param factor The interpolation factor (bounded between 0 and 1).
   * @return The interpolated value.
   */
  public static double lerp(double a, double b, double factor) {
    return a + (b - a) * factor;
  }

  /**
   * Returns a random instance from {@link ThreadLocalRandom}
   * @return the {@link ThreadLocalRandom} current {@link Random}
   */
  public static Random getRandom() {
    return ThreadLocalRandom.current();
  }
}

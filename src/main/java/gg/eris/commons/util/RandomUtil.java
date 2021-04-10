package gg.eris.commons.util;

import gg.eris.commons.Validate;
import java.util.concurrent.ThreadLocalRandom;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RandomUtil {

  /**
   * Returns a random number between given bounds
   *
   * @param lower is the lower bound (inclusive)
   * @param upper is the upper bound (exclusive)
   * @return a random number between the lower and upper
   */
  public static int randomInt(int lower, int upper) {
    Validate.isTrue(lower <= upper);
    if (lower == upper) {
      return lower;
    }
    return ThreadLocalRandom.current().nextInt(upper - lower) + lower;
  }

}

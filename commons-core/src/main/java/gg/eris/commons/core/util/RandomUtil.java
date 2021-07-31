package gg.eris.commons.core.util;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.Validate;

@UtilityClass
public class RandomUtil {

  /**
   * Returns a random integer between given bounds
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

  /**
   * Returns a random double-precision floating point number between given bounds
   *
   * @param lower is the lower bound (inclusive)
   * @param upper is the upper bound (exclusive)
   * @return a random number between the lower and upper
   */
  public static double randomDouble(double lower, double upper) {
    Validate.isTrue(lower <= upper);
    if (lower == upper) {
      return lower;
    }
    return ThreadLocalRandom.current().nextDouble(upper - lower) + lower;
  }

  /**
   * Returns a random boolean
   *
   * @return a random boolean
   */
  public static boolean randomBoolean() {
    return ThreadLocalRandom.current().nextBoolean();
  }

  /**
   * Returns a random item from a list
   *
   * @param list is the list
   * @param <T>  is the list type
   * @return a random item from the list
   */
  public static <T> T selectRandom(List<T> list) {
    Validate.notNull(list, "list cannot be null");
    Validate.isTrue(!list.isEmpty(), "list cannot be empty");
    return list.size() == 1 ? list.get(0) :
        list.get(ThreadLocalRandom.current().nextInt(list.size()));
  }

}

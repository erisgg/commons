package gg.eris.commons.core.util;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RandomUtil {

  /**
   * Returns a random integer between 0 and given bound
   *
   * @param upper is the upper bound (exclusive)
   * @return a random number between the lower and upper
   */
  public static int randomInt(int upper) {
    return randomInt(0, upper);
  }

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

  public static boolean percentChance(int percent) {
    Validate.isTrue(0 <= percent && 100 >= percent);
    return randomInt(100) <= percent;
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

  /**
   * Returns a shuffled list of 0 to n-1
   *
   * @param n is the number of elements into the list, exclusive
   * @return the int list
   */
  public static IntList randomList(int n) {
    IntList list = new IntArrayList(n);
    for (int i = 0; i < n; i++) {
      list.add(i);
    }
    Collections.shuffle(list);
    return list;
  }

}

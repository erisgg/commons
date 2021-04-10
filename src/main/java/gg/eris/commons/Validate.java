package gg.eris.commons;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Validate {

  public static void isTrue(boolean expression) {
    isTrue(expression, null);
  }

  public static void isTrue(boolean expression, String message) {
    if (!expression) {
      throw new RuntimeException(message);
    }
  }

}

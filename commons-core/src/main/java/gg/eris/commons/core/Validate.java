package gg.eris.commons.core;

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

  public static void notNull(Object object) {
    notNull(object, null);
  }

  public static void notNull(Object object, String message) {
    if (object == null) {
      throw new RuntimeException(message);
    }
  }

}

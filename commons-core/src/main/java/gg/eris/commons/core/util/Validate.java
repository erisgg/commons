package gg.eris.commons.core.util;

import com.google.common.collect.Multimap;
import java.util.Collection;
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

  public static void isNull(Object object) {
    isNull(object, null);
  }

  public static void isNull(Object object, String message) {
    if (object != null) {
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

  public static void notEmpty(String string) {
    notEmpty(string, null);
  }

  public static void notEmpty(String string, String message) {
    if (string == null || string.isEmpty()) {
      throw new RuntimeException(message);
    }
  }

  public static void notEmpty(Collection<?> collection) {
    notEmpty(collection, null);
  }

  public static void notEmpty(Collection<?> collection, String message) {
    if (collection.isEmpty()) {
      throw new RuntimeException(message);
    }
  }

  public static void notEmpty(Multimap<?, ?> multimap) {
    notEmpty(multimap, null);
  }

  public static void notEmpty(Multimap<?, ?> multimap, String message) {
    if (multimap.isEmpty()) {
      throw new RuntimeException(message);
    }
  }

  public static void notEmpty(Object[] array) {
    notEmpty(array, null);
  }

  public static void notEmpty(Object[] array, String message) {
    if (array.length == 0) {
      throw new RuntimeException(message);
    }
  }

}

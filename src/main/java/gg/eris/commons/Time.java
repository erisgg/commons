package gg.eris.commons;

import java.util.concurrent.TimeUnit;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Time {

  private static final TimeUnit DEFAULT_UNIT = TimeUnit.MILLISECONDS;

  public static long fromDisplayTime(String message) {
    return fromDisplayTime(message, DEFAULT_UNIT);
  }

  public static long fromDisplayTime(String message, TimeUnit to) {

  }

  public static String toDisplayTime(long time) {
    return toDisplayTime(time, DEFAULT_UNIT);
  }

  public static String toDisplayTime(long time, TimeUnit to) {
    return toDisplayTime(time, to, true);
  }

  public static String toDisplayTime(long time, TimeUnit to, boolean shorthand) {

  }



}

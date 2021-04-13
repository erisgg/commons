package gg.eris.commons.core;

import java.util.concurrent.TimeUnit;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.Validate;

/**
 * Shorthand format example: 5d4h3m2s Longhand format example: 5 days, 4 hours, 3 minutes and 2
 * seconds
 */
@UtilityClass
public class Time {

  private static final TimeUnit DEFAULT_UNIT = TimeUnit.MILLISECONDS;

  /**
   * Returns the time from a string
   *
   * @param message   is the message to convert to a long time
   * @param shorthand is whether the message is in shorthand time format
   * @return the display time as a numeric value in the {@value #DEFAULT_UNIT} time unit
   */
  public static long fromDisplayTime(String message, boolean shorthand) {
    return fromDisplayTime(message, DEFAULT_UNIT, shorthand);
  }

  /**
   * Returns the time from a string
   *
   * @param message   is the message to convert to a long time
   * @param unit      is the time unit the long represents
   * @param shorthand is whether the message is in shorthand time format
   * @return the display time as a numeric value in the given time unit
   */
  public static long fromDisplayTime(String message, TimeUnit unit, boolean shorthand) {
    if (shorthand) {
      return fromShorthandTime(message, unit);
    } else {
      return fromLonghandTime(message, unit);
    }
  }

  /**
   * Converts a long time into a shorthand display time using the {@value #DEFAULT_UNIT} time unit
   *
   * @param time is the time to convert to a display time
   * @return the display time
   */
  public static String toDisplayTime(long time) {
    return toDisplayTime(time, DEFAULT_UNIT);
  }

  /**
   * Converts a long time into a shorthand display time using the given time unit
   *
   * @param time is the time to convert to a display time
   * @param unit is the time unit the time value represents
   * @return the display time
   */
  public static String toDisplayTime(long time, TimeUnit unit) {
    return toDisplayTime(time, unit, true);
  }

  /**
   * Converts a long time into a display time using the given time unit and format
   *
   * @param time      is the time to convert to a display time
   * @param unit      is the time unit the time value represents
   * @param shorthand is whether the display time is in shorthand form
   * @return the display time
   */
  public static String toDisplayTime(long time, TimeUnit unit, boolean shorthand) {
    if (shorthand) {
      return toShorthandTime(time, unit);
    } else {
      return toLonghandTime(time, unit);
    }
  }

  /*
   TODO: Implement this shit
   */

  private static long fromShorthandTime(String message, TimeUnit unit) {
    return -1;
  }

  private static long fromLonghandTime(String message, TimeUnit unit) {
    return -1;
  }

  private static String toShorthandTime(long time, TimeUnit unit) {
    Validate.isTrue(time >= 0, "Time cannot be negative");
    long days = unit.toDays(time);
    long hours = unit.toHours(time) % 24;
    long minutes = unit.toMinutes(time) % 60;
    long seconds = unit.toSeconds(time) % 60;

    StringBuilder builder = new StringBuilder();

    if (days > 0) {
      builder.append(days).append("d");
    }

    if (hours > 0) {
      builder.append(hours).append("h");
    }

    if (minutes > 0) {
      builder.append(minutes).append("m");
    }

    if (seconds > 0) {
      builder.append(seconds).append("s");
    }

    return builder.length() == 0 ? "0s" : builder.toString();
  }

  private static String toLonghandTime(long time, TimeUnit unit) {
    Validate.isTrue(time >= 0, "Time cannot be negative");
    long days = unit.toDays(time);
    long hours = unit.toHours(time) % 24;
    long minutes = unit.toMinutes(time) % 60;
    long seconds = unit.toSeconds(time) % 60;

    int valueCount = (days == 0 ? 0 : 1)
        + (hours == 0 ? 0 : 1)
        + (minutes == 0 ? 0 : 1)
        + (seconds == 0 ? 0 : 1);

    StringBuilder builder = new StringBuilder();

    if (days > 0) {
      builder.append(days).append(" days ");
      if (--valueCount == 1) {
        builder.append("and ");
      } else if (valueCount > 1) {
        builder.append(", ");
      }
    }

    if (hours > 0) {
      builder.append(hours).append(" hours ");
      if (--valueCount == 1) {
        builder.append("and ");
      } else if (valueCount > 1) {
        builder.append(", ");
      }
    }

    if (minutes > 0) {
      builder.append(minutes).append(" minutes ");
      if (--valueCount == 1) {
        builder.append("and ");
      } else if (valueCount > 1) {
        builder.append(", ");
      }
    }

    if (seconds > 0) {
      builder.append(seconds).append(" seconds");
    }

    return builder.length() == 0 ? "0 seconds" : builder.toString();
  }


}

package gg.eris.commons.core;

import static org.junit.Assert.assertEquals;

import gg.eris.commons.core.util.Time;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

public class TimeTest {

  @Test
  public void shorthandTimeTest() {
    long time = 5398535L;
    String display = Time.toShortDisplayTime(time, TimeUnit.SECONDS);
    assertEquals("62d11h35m35s", display);
    long back = Time.fromDisplayTime(display, TimeUnit.SECONDS, true);
    assertEquals(time, back);
  }

  @Test
  public void longhandTimeTest() {
    long time = 5398535L;
    String display = Time.toLongDisplayTime(time, TimeUnit.SECONDS);
    assertEquals("62 days, 11 hours, 35 minutes and 35 seconds", display);
    long back = Time.fromDisplayTime(display, TimeUnit.SECONDS, false);
    assertEquals(time, back);
  }

}

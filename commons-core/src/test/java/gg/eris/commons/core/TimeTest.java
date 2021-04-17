package gg.eris.commons.core;

import java.util.concurrent.TimeUnit;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TimeTest {

  @Test
  public void shorthandTimeTest() {
    long time = 5398535L;
    String display = Time.toDisplayTime(time, TimeUnit.SECONDS, true);
    assertEquals("62d11h35m35s", display);
    assertEquals(time, Time.fromDisplayTime(display, TimeUnit.SECONDS, true));
  }

  @Test
  public void longhandTimeTest() {
    long time = 5398535L;;
    String display = Time.toDisplayTime(time, TimeUnit.SECONDS, false);
    assertEquals("62 days, 11 hours, 35 minutes and 35 seconds", display);
  }

}

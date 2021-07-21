package gg.eris.commons.bukkit;

import static org.junit.Assert.assertEquals;

import gg.eris.commons.bukkit.text.TextComponent;
import gg.eris.commons.bukkit.text.TextMessage;
import org.junit.Test;

public final class ChatTest {

  @Test
  public void simpleTest1() {
    String expected = "[\"\",{\"text\":\"hello\"}]";
    String actual = TextMessage.of(
        TextComponent.builder("hello").build()
    ).getJsonMessage();
    assertEquals(expected, actual);
  }

}

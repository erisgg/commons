package gg.eris.commons.bukkit;

import static org.junit.Assert.assertEquals;

import gg.eris.commons.bukkit.impl.text.ComponentParser;
import gg.eris.commons.bukkit.text.TextComponent;
import gg.eris.commons.bukkit.text.TextMessage;
import gg.eris.commons.bukkit.text.TextType;
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

  @Test
  public void regexMatcherTest1() {
    ComponentParser.parse(
        TextType.INFORMATION,
        "<bold>Bold test mightymouse</bold> this is my test",
        null,
        null
    );
  }

}

package gg.eris.commons;

import gg.eris.commons.bukkit.impl.text.ComponentParser;
import gg.eris.commons.bukkit.text.ClickEvent;
import gg.eris.commons.bukkit.text.ClickEvent.Action;
import gg.eris.commons.bukkit.text.HoverEvent;
import gg.eris.commons.bukkit.text.TextMessage;
import gg.eris.commons.bukkit.text.TextType;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import org.junit.Test;

public class ChatTest {

  @Test
  public void regexMatcherTest1() {
    TextMessage message = ComponentParser.parse(
        TextType.INFORMATION,
        Int2ObjectMaps.singleton(0, ClickEvent.of(Action.SUGGEST_COMMAND, "test")),
        Int2ObjectMaps.singleton(0, HoverEvent.of("<col=yellow>Hover event test</col>")),
        "This is my <h>highlighted bit</h> and this isn't, but I want to underline and make this "
            + "bit fancy <raw><u><event=0>because I'm a twat</event></u></raw><h>test</h>"
    );

    System.out.println(message.getJsonMessage());
  }

}

package gg.eris.commons.bukkit.text;

import lombok.Value;

@Value(staticConstructor = "of")
public class HoverEvent {

  TextMessage contents;

  public String toJsonMessage() {
    return "{\"action\":\"show_text\",\"value\":" + contents.getJsonMessage() + "}";
  }

}

package gg.eris.commons.bukkit.text;

import com.google.common.collect.Lists;
import gg.eris.commons.core.util.Validate;
import java.util.Arrays;
import java.util.List;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;

public final class TextMessage {

  private static final TextMessage EMPTY = TextMessage.of(TextComponent.empty());

  private final TextComponent[] components;

  private TextMessage(TextComponent... components) {
    Validate.notEmpty(components, "components cannot be empty");
    this.components = components;
  }

  public String getJsonMessage() {
    StringBuilder message = new StringBuilder("[\"\",");
    for (int i = 0; i < this.components.length; i++) {
      TextComponent component = components[i];
      message.append(component.toString());
      if (i + 1 < this.components.length) {
        message.append(",");
      }
    }
    message.append("]");
    return message.toString();
  }

  public BaseComponent[] getBaseComponent() {
    return ComponentSerializer.parse(getJsonMessage());
  }

  public boolean isEmpty() {
    return this.getJsonMessage().equals(EMPTY.getJsonMessage());
  }

  public static TextMessage join(TextMessage... messages) {
    List<TextComponent> components = Lists.newArrayList();
    for (TextMessage message : messages) {
      components.addAll(Arrays.asList(message.components));
    }
    return new TextMessage(components.toArray(new TextComponent[0]));
  }

  public static TextMessage of(TextComponent... components) {
    return new TextMessage(components);
  }

  public static TextMessage empty() {
    return EMPTY;
  }

}

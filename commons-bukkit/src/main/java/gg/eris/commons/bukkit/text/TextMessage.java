package gg.eris.commons.bukkit.text;

import com.google.common.collect.Lists;
import gg.eris.commons.core.util.Validate;
import java.util.List;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;

public final class TextMessage {

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

  public static TextMessage join(TextMessage... messages) {
    List<TextComponent> components = Lists.newArrayList();
    for (TextMessage message : messages) {
      for (TextComponent component : message.components) {
        components.add(component);
      }
    }
    return new TextMessage(components.toArray(new TextComponent[0]));
  }

  public static TextMessage of(TextComponent... components) {
    return new TextMessage(components);
  }

}

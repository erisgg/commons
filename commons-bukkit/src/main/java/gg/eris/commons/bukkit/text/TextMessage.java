package gg.eris.commons.bukkit.text;

import gg.eris.commons.core.util.Validate;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;

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

  public static TextMessage of(TextComponent... components) {
    return new TextMessage(components);
  }

}

package gg.eris.commons.bukkit.command;

import java.util.Map;

public class CommandContext {

  private final Map<String, Object> values;

  public CommandContext(Map<String, Object> values) {
    this.values = values;
  }

  @SuppressWarnings("unchecked")
  public <T> T get(String key) {
    Object object = this.values.get(key);
    if (object == null) {
      throw new NullPointerException("No key in command: " + key);
    }
    return (T) object;
  }

}

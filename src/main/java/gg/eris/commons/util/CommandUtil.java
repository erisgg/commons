package gg.eris.commons.util;

import java.lang.reflect.Field;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

@UtilityClass
public class CommandUtil {

  public static CommandMap getCommandMap() {
    try {
      Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
      field.setAccessible(true);

      return (CommandMap) field.get(Bukkit.getServer());
    } catch (NoSuchFieldException | IllegalAccessException err) {
      err.printStackTrace();
    }
    return null;
  }

}

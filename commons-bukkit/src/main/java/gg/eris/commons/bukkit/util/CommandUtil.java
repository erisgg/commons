package gg.eris.commons.bukkit.util;

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
      CommandMap map = (CommandMap) field.get(Bukkit.getServer());
      field.setAccessible(false);
      return map;
    } catch (NoSuchFieldException | IllegalAccessException err) {
      err.printStackTrace();
    }

    return null;
  }

}

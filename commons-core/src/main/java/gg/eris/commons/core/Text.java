package gg.eris.commons.core;

import com.google.common.collect.Lists;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Text {

  /**
   * Replaces {0}, {1} etc...placeholders with variables
   *
   * @param message   is the message to replace the variables for
   * @param variables are the variables to replace with
   * @return the replaced message
   */
  public static String replaceVariables(String message, Object... variables) {
    for (int i = 0; i < variables.length; i++) {
      message = message.replace("{" + i + "}", variables[i].toString());
    }

    return message;
  }

  /**
   * Replaces placeholders in a list
   *
   * @param messages  is the messages to replace variables for
   * @param variables are the variables to replace with
   * @return a new list with replaced placeholders
   */
  public static List<String> replaceVariables(List<String> messages, Object... variables) {
    List<String> list = Lists.newArrayList();
    for (String message : messages) {
      list.add(replaceVariables(message, variables));
    }
    return list;
  }

}

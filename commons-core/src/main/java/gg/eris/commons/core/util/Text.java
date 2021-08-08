package gg.eris.commons.core.util;

import com.google.common.collect.Lists;
import java.util.List;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
public class Text {

  private static final String CLEAR = StringUtils.repeat(" \n", 100);

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

  public static int countMatches(String text, String sequence) {
    return StringUtils.countMatches(text, sequence);
  }

  public static List<String> splitWords(String words, int wordsPerSegment) {
    String[] split = words.split(" ");
    List<String> list = Lists.newArrayList();
    StringBuilder stack = new StringBuilder();
    int stackCounter = 0;
    for (String s : split) {
      if (stack.length() > 0) {
        stack.append(" ");
      }
      stack.append(s);
      if (++stackCounter == wordsPerSegment) {
        list.add(stack.toString());
        stack = new StringBuilder();
        stackCounter = 0;
      }
    }

    if (stack.length() > 0) {
      list.add(stack.toString());
    }

    return list;
  }

}

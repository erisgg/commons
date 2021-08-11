package gg.eris.commons.core.util;

import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DebugUtil {

  public static void printList(List<Object> list) {
    for (Object o : list) {
      System.out.println(o);
    }
  }

}

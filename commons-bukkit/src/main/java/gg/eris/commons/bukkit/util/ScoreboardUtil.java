package gg.eris.commons.bukkit.util;

import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ScoreboardUtil {

  // TODO:
  public static List<String> generateAnimatedString(String base, CC highlight, int n) {
    String rawBase = CC.strip(base);

    for (int i = 0; i < rawBase.length() + n + 1; i++) {
      int minIndex = Math.max(0, i - n);
      int maxIndex = Math.min(rawBase.length(), i);

      String leftSegment = rawBase.substring(0, minIndex);
      String highlightedSegment = rawBase.substring(minIndex, maxIndex);
      String rightSegment = rawBase.substring(maxIndex);

      System.out.println(String.format("\"%s\", \"%s\", \"%s\"", leftSegment, highlightedSegment, rightSegment));
    }

    return null;
  }
}

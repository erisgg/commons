package gg.eris.commons.bukkit.util;

import java.util.TreeMap;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RomanNumeral {

  private final static TreeMap<Integer, String> NUMERALS = new TreeMap<>();

  static {
    NUMERALS.put(1000, "M");
    NUMERALS.put(900, "CM");
    NUMERALS.put(500, "D");
    NUMERALS.put(400, "CD");
    NUMERALS.put(100, "C");
    NUMERALS.put(90, "XC");
    NUMERALS.put(50, "L");
    NUMERALS.put(40, "XL");
    NUMERALS.put(10, "X");
    NUMERALS.put(9, "IX");
    NUMERALS.put(5, "V");
    NUMERALS.put(4, "IV");
    NUMERALS.put(1, "I");
  }

  /**
   * Gets the roman numeral related to the specified integer.
   *
   * @param number The integer to get the roman numeral of
   * @return The roman numeral for the specified number
   */
  public static String toRoman(int number) {
    int l = NUMERALS.floorKey(number);

    if (number == l) {
      return NUMERALS.get(number);
    }

    return NUMERALS.get(l) + toRoman(number - l);
  }

}

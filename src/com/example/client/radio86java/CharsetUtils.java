package com.example.client.radio86java;

import java.util.HashMap;
import java.util.Map;

public class CharsetUtils {

  /**
   * UTF-8 character map to the corresponding code defined in ROM
   */
  private static final Map<Character, Integer> mapU2R = new HashMap<>();

  static {
    String u
            = " !\"#$%&'()*+,-./" // 20 - 2F
            + "0123456789:;<=>?" // 30 - 3F
            + "@ABCDEFGHIJKLMNO" // 40 - 4F
            + "PQRSTUVWXYZ[\\]^_" // 50 - 5F
            + "ЮАБЦДЕФГХИЙКЛМНО" // 60 - 6F
            + "ПЯРСТУЖВЬЇЗШЄЩЧҐ";   // 70 - 7F

    String u2
            = "-abcdefghijklmno" // 40 - 4F
            + "pqrstuvwxyz-----" // 50 - 5F
            + "юабцдефгхийклмно" // 60 - 6F
            + "пярстужвьїзшєщчґ"; // 70 - 7F

    for (int i = 0; i < u.length(); i++) {
      mapU2R.put(u.charAt(i), 32 + i);
    }
    for (int i = 0; i < u2.length(); i++) {
      if (u2.charAt(i) != '-') {
        mapU2R.put(u2.charAt(i), 64 + i);
      }
    }
  }

  /**
   * Convert UTF-8 character to the corresponding 20-7F code defined in ROM
   */
  public static char converse(char input) {
    Integer input2 = mapU2R.get(input);
    if (input2 != null) {
      return (char) (input2.intValue());
    }
    if (input < 32) {
      return input;
    }
    return '?';
  }

}

package com.example.client.radio86java;

import java.util.HashMap;
import java.util.Map;

public class CharsetUtils {

  // UTF-8 to radio charset;
  private static final Map<Character, Integer> mapU2R = new HashMap<>();

  static {
    String u
            = " !\"#$%&'()*+,-./" // 20 - 2F
            + "0123456789:;<=>?" // 30 - 3F
            + "@ABCDEFGHIJKLMNO" // 40 - 4F
            + "PQRSTUVWXYZ[\\]^_" // 50 - 5F
            + "ЮАБЦДЕФГХИЙКЛМНО" // 60 - 6F
            + "ПЯРСТУЖВЬЫЗШЭЩЧ";   // 70 - 7E

    String u2
            = "-abcdefghijklmno" // 40 - 4F
            + "pqrstuvwxyz-----" // 50 - 5F
            + "юабцдефгхийклмно" // 60 - 6F
            + "пярстужвьызшэщч"; // 70 - 7E

    for (int i = 0; i < u.length(); i++) {
      mapU2R.put(u.charAt(i), 32 + i);
      //System.out.println(u.charAt(i) + "=>" + (32+i));
    }
    for (int i = 0; i < u2.length(); i++) {
      if (u2.charAt(i) != '-') {
        mapU2R.put(u2.charAt(i), 64 + i);
      }
    }
  }

  public static char converse(char input) {
    Integer input2 = mapU2R.get(input);
    if (input2 != null) {
      //System.out.println("Conversion [" + input + "] to radio [" + mapU2R.get(input) + "]");
      return (char) (input2.intValue());
    }
    if (input < 32) {
      return input;
    }
    return '?';
  }

}

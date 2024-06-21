package com.example.client.radio86java;

import java.util.Arrays;

/**
 * 76D0 - 7FFF or 7FF3 screen area 
 * 77C2 (30658) - 7F51 (32593) visible screen area (25 lines * (8 + 64 + 6))
 *
 * @author lampshade1
 */
public class Memory {

  private final int[] memory = new int[32768];

  private final int LEFT_PADDING = 8;
  private final int RIGHT_PADDING = 6;
  private final int LEFT_TOP_CORNER = 30658 - LEFT_PADDING;
  private final int RIGHT_BOTTOM_CORNER = 32593 + RIGHT_PADDING;
  private final int WIDTH = LEFT_PADDING + 64 + RIGHT_PADDING; // 78 // 0 .. 7  8 .. 71  72 .. 77
  private final int START_X = LEFT_PADDING;
  private final int END_X = LEFT_PADDING + 64 - 1; // 71

  public static final int UNKNOWN_COORD = -1;

  public Memory() {
    Arrays.fill(memory, 0);
  }

  public int peek(int addr) {
    if (addr < memory.length) {
      return memory[addr];
    } else {
      return 0;
    }
  }

  public void poke(int addr, int value) {
    if (addr < memory.length) {
      memory[addr] = value;
    }
  }

  public boolean inPossibleVisibleArea(int addr) {
    return addr >= LEFT_TOP_CORNER && addr <= RIGHT_BOTTOM_CORNER;
  }

  public boolean inVisibleArea(int addr) {
    if (inPossibleVisibleArea(addr)) {
      int a = addr - LEFT_TOP_CORNER;
      int y = a / WIDTH;
      int x = a - y * WIDTH;
      if (x >= START_X && x <= END_X) {
        return true;
      }
    }
    return false;
  }

  public int[] getYX(int addr) {
    if (inPossibleVisibleArea(addr)) {
      int a = addr - LEFT_TOP_CORNER;
      int y = a / WIDTH;
      int x = a - y * WIDTH;
      if (x >= START_X && x <= END_X) {
        return new int[]{y, x - LEFT_PADDING};
      }
    }
    return new int[]{UNKNOWN_COORD, UNKNOWN_COORD};
  }

  public int getAddr(int y, int x) {
    int a = LEFT_TOP_CORNER;
    a = a + y * WIDTH;
    a = a + LEFT_PADDING + x;
    return a;
  }
}

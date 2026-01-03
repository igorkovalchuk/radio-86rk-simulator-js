package com.example.client.radio86java;

/**
 * TerminalModel (former Console.java)
 */
public class TerminalModel implements TerminalModelIntf {

  private final TerminalParameters parameters;
  private final int maxX;
  private final int maxY;
  private final int lastX;
  private final int lastY;
  private final char[][] screen;

  private final Memory memory = new Memory();

  private int pointX = 0;
  private int pointY = 0;

  private int cursorX = 0;
  private int cursorY = 0;

  private final int BOTTOM_TO_TOP = 1;
  //private final int TOP_TO_BOTTOM = 0;

  // In this place we define a coordinate system type;
  // 1 => 0 at bottom, 24 at top; 0 => 0 at top, 24 at bottom;
  private final int directionUp; // 0 or 1; default is 1;

  public TerminalModel(TerminalParameters parameters) {
	this.directionUp = BOTTOM_TO_TOP;

	this.parameters = parameters;
	this.maxX = parameters.terminalWidth;
	this.maxY = parameters.terminalHeight;
	this.lastX = maxX - 1;
	this.lastY = maxY - 1;
	screen = new char[maxY][maxX];

    cls();
    pointUpLeft();
  }

  private void modifyYX(int y, int x, char c) {
    screen[y][x] = c;
    if (directionUp == 1) {
      y = lastY - y;
    }
    memory.poke(memory.getAddr(y, x), c);
  }

  @Override
  public void poke(int addr, int value) {
    memory.poke(addr, value);
    int[] yx = memory.getYX(addr);
    if (yx[0] != Memory.UNKNOWN_COORD) {
      if (directionUp == 1) {
        yx[0] = lastY - yx[0];
      }
      screen[yx[0]][yx[1]] = (char) value;
    }
  }

  @Override
  public int peek(int addr) {
    return memory.peek(addr);
  }

  @Override
  public int getDirectionUp() {
    return directionUp;
  }

  private void pointUpLeft() {
    point(0, lastY);
  }

  @Override
  public int getCursorX() {
    return cursorX;
  }

  @Override
  public int getCursorY() {
    return cursorY;
  }

  @Override
  public final void cls() {
    // reset cursor
    point(0, parameters.terminalHeight - 1);

    // reset plot/line initial point
    pointX = 0;
    pointY = 0;

    for (int y = 0; y < maxY; y++) {
      for (int x = 0; x < maxX; x++) {
        modifyYX(y, x, ' ');
      }
    }
  }

  @Override
  public void print(String s) {
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      print(c, false);
    }
  }

  @Override
  public void println(String s) {
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      print(c, false);
    }
    cr();
    lf();
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////
  private boolean inScreen(int x, int y) {
    return ((y >= 0) && (y <= lastY) && (x >= 0) && (x <= lastX));
  }

  private void print(char c, boolean fixed) {
    if (c == 10) {
      // line feed;
      lf(fixed);
    } else if (c == 13) {
      // carriage return;
      cr();
    } else {
      move1(fixed);
      if (inScreen(cursorX, cursorY)) {
        modifyYX(cursorY, cursorX, CharsetUtils.converse(c));
      }
      move2(fixed);
    }
  }

  // move cursor
  private void move1(boolean fixed) {
    if (cursorX > lastX) {
      if (fixed) {
        cursorX = lastX;
      } else {
        cursorX = 0;
        lf(false);
      }
    }
  }

  // move cursor
  private void move2(boolean fixed) {
    cursorX++;
  }

  public void lf() {
    lf(false);
  }

  private void lf(boolean fixed) {
    if (directionUp > 0) {
      if (cursorY <= 0) {
        cursorY = 0;
        if (!fixed) {
          scroll();
        }
      } else {
        cursorY--;
      }
    } else {
      cursorY++;
      if (cursorY > lastY) {
        cursorY = lastY;
        if (!fixed) {
          scroll();
        }
      }
    }
  }

  public void cr() {
    cursorX = 0;
  }

  private void scroll() {
    if (directionUp > 0) {
      for (int y = lastY; y > 0; y--) {
        for (int x = 0; x < maxX; x++) {
          modifyYX(y, x, getYX(y - 1, x));
        }
      }
      for (int x = 0; x < maxX; x++) {
        modifyYX(0, x, ' ');
      }
    } else {
      for (int y = 0; y < lastY; y++) {
        for (int x = 0; x < maxX; x++) {
          modifyYX(y, x, getYX(y + 1, x));
        }
      }
      for (int x = 0; x < maxX; x++) {
        modifyYX(lastY, x, ' ');
      }
    }
  }

  @Override
  public char get(int x, int y) {
    if (inScreen(x, y)) {
      return screen[y][x];
    }
    return 0;
  }

  private char getYX(int y, int x) {
    return screen[y][x];
  }

  public void set(int x, int y, char c) {
    int cX = cursorX;
    int cY = cursorY;
    point(x, y);
    print(c, true);
    cursorX = cX;
    cursorY = cY;
  }

  @Override
  public void point(int x, int y) {
    if (x > lastX) {
      x = lastX;
    }
    if (y > lastY) {
      y = lastY;
    }
    cursorX = x;
    cursorY = y;
  }

  @Override
  public void tab(int x) {
    if ((cursorX + x) > lastX) {
      // ignore - this is an incorrect value;
    } else {
      cursorX = cursorX + x;
    }
  }

  @Override
  public void plot(int x, int y, int z) {

    pointX = x;
    pointY = y;
    int x1 = x / 2;
    int y1 = y / 2;
    if (x1 < 0 || y1 < 0 || x1 > lastX || y1 > lastY) {
      return;
    }
    int rx = x - x1 * 2; // rest 1 or 0;
    int ry;
    if (directionUp > 0) {
      ry = 1 - (y - y1 * 2);
    } else {
      ry = (y - y1 * 2);
    }
    int pseudo1 = 0;
    if (rx == 0 && ry == 0) {
      pseudo1 = 1;
    } else if (rx == 1 && ry == 0) {
      pseudo1 = 2;
    } else if (rx == 0 && ry == 1) {
      pseudo1 = 16;
    } else if (rx == 1 && ry == 1) {
      pseudo1 = 4;
    }
    char c = get(x1, y1);
    int ci = (int) c;
    if ((ci >= 1 && ci <= 7) || (ci >= 16 && ci <= 23)) {
      if (z == 0) {
        ci = ci & (~pseudo1);
        c = (char) ci;
      } else if (z == 1) {
        ci = ci | pseudo1;
        c = (char) ci;
      } else {

      }
    } else {
      if (z == 0) {
        // don't draw empty points at all;
      } else {
        c = (char) pseudo1;
      }
    }
    
    set(x1, y1, c);
  }

  @Override
  public void line(int toX, int toY) {
      int x1 = pointX;
      int y1 = pointY;
      GraphicUtil.line(x1, y1, toX, toY, (x, y) -> plot(x, y, 1));
      pointX = toX;
      pointY = toY;
  }

  public TerminalParameters getParameters() {
	  return parameters;
  }
}

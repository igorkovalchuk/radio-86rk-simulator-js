package com.example.client.radio86java;

public interface TerminalModelIntf {

  int getCursorX();
  int getCursorY();

  int getDirectionUp();

  char get(int x, int y);

  void point(int x, int y);

  void cls();

  void line(int toX, int toY);
  void plot(int x, int y, int z);

  void print(String s);
  void println(String s);
  void tab(int x);

  void poke(int addr, int value);
  int peek(int addr);

  TerminalParameters getParameters();
}

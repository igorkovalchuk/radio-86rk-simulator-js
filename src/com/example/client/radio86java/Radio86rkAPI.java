package com.example.client.radio86java;

import java.util.Arrays;
//import radio86java.file.FileUtils;
//import radio86java.file.SimpleFileInterface;

import com.example.client.myWebApp;
import com.google.gwt.core.client.GWT;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

/**
 * The screen has 0-24 (25 Y), 0-63 (64 X) chars;
 * Coords start at left bottom corner;
 */
@JsType(name="r86", namespace = JsPackage.GLOBAL)
public class Radio86rkAPI {

  private static Radio86rkAPI instance; 

  private final ComputerModelIntf computerModel;
  private final UserInterfaceIntf ui;

  public Radio86rkAPI(ComputerModelIntf computerModel, UserInterfaceIntf ui) {
    this.computerModel = computerModel;
    this.ui = ui;
  }

  public static Radio86rkAPI createInstance(ComputerModelIntf computerModel, UserInterfaceIntf ui) {
	  instance = new Radio86rkAPI(computerModel, ui);
	  return instance ;
  }

  public static Radio86rkAPI getInstance() {
	  return instance;
  }

  private TerminalModelIntf getTerminalModel() {
    return computerModel.getTerminalModel();
  }

  private void updateScreen() {
    ui.updateScreen();
  }

  public void interactive(boolean interact) {
    getTerminalModel().setInteractive(interact);
  }

  public void print(Object message) {

    getTerminalModel().print(String.valueOf(message));
    updateScreen();
  }

  public void println(Object message) {
    getTerminalModel().println(String.valueOf(message));
    updateScreen();
  }

  public void cr() {
    print("\r");
  }

  public void lf() {
    print("\n");
  }

  public void crlf() {
    print("\r\n");
  }

  public void freeze() {
    ui.setFreeze(true);
  }

  public void unfreeze() {
    ui.setFreeze(false);
  }

  public String spc(double nn) {
    int n = toInt(nn);
    if (n <= 0) {
      return "";
    }
    char[] c = new char[n];
    Arrays.fill(c, ' ');
    return String.valueOf(c);
  }

  public void cur(double x, double y) {
    //System.out.println("CUR " + x + " " + y);
    getTerminalModel().point(toInt(x), toInt(y));
  }

  public void printtab(double x) {
    getTerminalModel().tab(toInt(x));
  }

  public void cls() {
    //System.out.println("CLS");
    getTerminalModel().cls();
    updateScreen();
  }

  public void plot(double x, double y, int z) {
    //System.out.println("PLOT " + x + " " + y + " " + z);
    getTerminalModel().plot(toInt(x), toInt(y), z);
    updateScreen();
  }

  // arc in degrees;
  public void arcD(double x, double y, double r, double a1, double a2) {
    TerminalModelIntf c = getTerminalModel();
    for (double i = a1 * Math.PI; i <= a2 * Math.PI; i += 0.03) {
      c.plot(
              (int) Math.rint(x + Math.cos(i) * r),
              (int) Math.rint(y + Math.sin(i) * r), 1);
    }
    updateScreen();
  }

  public void circle(double x, double y, double r) {
    TerminalModelIntf c = getTerminalModel();
    for (double i = 0; i <= 2 * Math.PI; i += 0.03) {
      c.plot((int) Math.rint(x + Math.cos(i) * r),
              (int) Math.rint(y + Math.sin(i) * r), 1);
    }
    updateScreen();
  }

  public String inkey(int mode) {
	return Keyboard.get();
  }

  public String screen(double x, double y) {
    String value;
    value = String.valueOf(getTerminalModel().get(toInt(x), toInt(y)));
    return value;
  }

  public void line(double x, double y) {
    getTerminalModel().line(toInt(x), toInt(y));
    updateScreen();
  }

  private int toInt(double x) {
    if (x >= 0) {
      return (int) (x + 0.5);
    }
    return (int) (x - 0.5);
  }

  public void poke(int addr, int value) {
    getTerminalModel().poke(addr, value);
    // TODO: optimize
    updateScreen();
  }

  public int peek(int addr) {
    return getTerminalModel().peek(addr);
  }

  public String chr(int value) {
    return String.valueOf((char) value);
  }

  public int asc(String value) {
    if (value.length() == 0) {
      return 0; // TODO: error
    }
    return (int) value.toCharArray()[0];
  }

  //public SimpleFileInterface localFile(String path, String fileName) {
    //SimpleFileInterface file = FileUtils.loadLocalFile(path, fileName);
    //return file;
  //}

  //public SimpleFileInterface remoteFile(String path) {
    //SimpleFileInterface file = FileUtils.loadRemoteFile(path);
    //return file;
  //}

  public void log(Object message) {
    GWT.log("LOG: " + message);
  }
}

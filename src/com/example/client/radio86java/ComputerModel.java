package com.example.client.radio86java;

import jsinterop.annotations.JsType;

@JsType
public class ComputerModel implements ComputerModelIntf {

  private final TerminalModel terminalModel;

  /**
   * Additional hardware thing to draw a graphics layer, or a color mosaic,
   * over the terminal picture
   */
  private final GraphicOverlay overlay;

  public ComputerModel(TerminalParameters parameters) {
	  terminalModel = new TerminalModel(parameters);
	  overlay = new GraphicOverlay(parameters);
  }

  @Override
  public TerminalModelIntf getTerminalModel() {
    return terminalModel;
  }

  public GraphicOverlay getOverlay() {
      return overlay;
  }
}

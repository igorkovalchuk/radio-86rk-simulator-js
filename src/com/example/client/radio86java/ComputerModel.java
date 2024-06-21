package com.example.client.radio86java;

import jsinterop.annotations.JsType;

@JsType
public class ComputerModel implements ComputerModelIntf {

  private final TerminalModel terminalModel;

  public ComputerModel(TerminalParameters parameters) {
	  terminalModel = new TerminalModel(parameters);
  }

  @Override
  public TerminalModelIntf getTerminalModel() {
    return terminalModel;
  }

}

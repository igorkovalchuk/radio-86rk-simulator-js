package com.example.client.radio86java.command;

import com.example.client.radio86java.TerminalParameters;
import com.google.gwt.canvas.dom.client.Context2d;

public interface Command {

    void render(Context2d context, TerminalParameters parameters);

    double[] getLastXY();

    int getColor();
}

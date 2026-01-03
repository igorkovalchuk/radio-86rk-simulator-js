package com.example.client.radio86java;

import java.util.ArrayList;
import java.util.List;

import com.example.client.radio86java.command.Command;
import com.google.gwt.canvas.dom.client.Context2d;

public class GraphicOverlay {

    private TerminalParameters parameters;

    // This is probably a temporary solution, 
    // normally this should be a RAM memory-based model
    private List<Command> commands = new ArrayList<>();

    private double[] lastXY = {0, 0};

    private int lastColor = 0;

    public GraphicOverlay(TerminalParameters parameters) {
        this.parameters = parameters;
    }

    public void apply(Command command) {
        commands.add(command);
        lastXY = command.getLastXY();
        lastColor = command.getColor();
    }

    public double[] getLastXY() {
        return lastXY;
    }

    public int getLastColor() {
        return lastColor;
    }

    public void render(Context2d context) {
        for(Command command : commands) {
            command.render(context, parameters);
        }
    }

    public void cls() {
        commands.clear();
    }
}

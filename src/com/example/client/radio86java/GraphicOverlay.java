package com.example.client.radio86java;

import com.example.client.radio86java.command.Command;
import com.google.gwt.canvas.dom.client.Context2d;

public class GraphicOverlay {

    private final GraphicOverlayMemory memory = new GraphicOverlayMemory();

    private final GraphicOverlayRenderer renderer;

    private double[] lastXY = {0, 0};

    private int lastColor = 0;

    public GraphicOverlay(TerminalParameters parameters) {
        this.renderer = new GraphicOverlayRenderer(parameters, memory);
    }

    public void apply(Command command) {
        lastXY = command.getLastXY();
        lastColor = command.getColor();
        command.apply(memory);
    }

    public double[] getLastXY() {
        return lastXY;
    }

    public int getLastColor() {
        return lastColor;
    }

    public void render(Context2d context) {
        renderer.render(context);
    }

    public void cls() {
        memory.cls();
    }

    public int[][] cloneMemory() {
        return memory.cloneMemory();
    }
}

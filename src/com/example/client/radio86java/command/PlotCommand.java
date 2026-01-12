package com.example.client.radio86java.command;

import com.example.client.radio86java.GraphicOverlayMemory;

public class PlotCommand implements Command {

    private final double x;
    private final double y;
    private final int color;

    public PlotCommand(double x, double y, int color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    @Override
    public double[] getLastXY() {
        return new double[] {x, y};
    }

    @Override
    public int getColor() {
        return this.color;
    }

    @Override
    public void apply(GraphicOverlayMemory memory) {
        memory.setColor((int)x, (int)y, color);
    }
}

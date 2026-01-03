package com.example.client.radio86java.command;

import com.example.client.radio86java.GraphicUtil;
import com.example.client.radio86java.TerminalParameters;
import com.google.gwt.canvas.dom.client.Context2d;

public class LineCommand implements Command {

    private final double x;
    private final double y;
    private final double toX;
    private final double toY;
    private final int color;

    public LineCommand(double x, double y, double toX, double toY, int color) {
        this.x = x;
        this.y = y;
        this.toX = toX;
        this.toY = toY;
        this.color = color;
    }

    @Override
    public double[] getLastXY() {
        return new double[] {toX, toY};
    }

    @Override
    public int getColor() {
        return this.color;
    }

    @Override
    public void render(Context2d context, TerminalParameters parameters) {
        GraphicUtil.line((int)x, (int)y, (int)toX, (int)toY, 
                (lx, ly) -> (new PlotCommand(lx, ly, color)).render(context, parameters));
    }
}

package com.example.client.radio86java.command;

import com.example.client.radio86java.GraphicUtil;
import com.example.client.radio86java.TerminalParameters;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;

public class PlotCommand implements Command {

    private final CssColor BLACK = GraphicUtil.getColor(0, 0, 0);
    private final CssColor WHITE = GraphicUtil.getColor(255, 255, 255);
    private final CssColor RED = GraphicUtil.getColor(255, 0, 0);
    private final CssColor GREEN = GraphicUtil.getColor(0, 255, 0);

    // TODO: the palette should be configurable in GraphicOverlay
    private final CssColor[] palette = new CssColor[] {BLACK, WHITE, RED, GREEN};

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
    public void render(Context2d context, TerminalParameters parameters) {

        double characterY = Math.floor(y / 2);
        double remainderY = y % 2;
        double y1 = characterY * (parameters.scaledCharsetHeight + parameters.yGap);
        y1 += ( remainderY == 0 ) ? 0 : parameters.scaledCharsetHeight / 2;
        double terminalHeight = parameters.terminalHeight * (parameters.scaledCharsetHeight + parameters.yGap);
        y1 = terminalHeight - y1;

        double characterX = Math.floor(x / 2);
        double remainderX = x % 2;
        double x1 = characterX * (parameters.scaledCharsetWidth + parameters.xGap);
        x1 += (remainderX == 0) ? 0 : parameters.scaledCharsetWidth / 2;

        double w = parameters.scaledCharsetWidth / 2;
        double h = parameters.scaledCharsetHeight / 2;

        context.setFillStyle(palette[color]);
        context.fillRect(x1, y1 - h , w, h);
    }
}

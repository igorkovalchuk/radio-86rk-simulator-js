package com.example.client.radio86java;

import com.google.gwt.canvas.dom.client.Context2d;

import static com.example.client.radio86java.GraphicOverlayMemory.OVERLAY_WIDTH;
import static com.example.client.radio86java.GraphicOverlayMemory.OVERLAY_HEIGHT;

class GraphicOverlayRenderer {

    private final TerminalParameters parameters;
    private final GraphicOverlayMemory memory;

    private final double halfWidth;
    private final double halfHeight;

    GraphicOverlayRenderer(TerminalParameters parameters, GraphicOverlayMemory memory) {
        this.parameters = parameters;
        this.memory = memory;
        halfWidth = parameters.scaledCharsetWidth / 2;
        halfHeight = parameters.scaledCharsetHeight / 2;
    }

    void render(Context2d context) {
        int color;
        for(int y = 0; y < OVERLAY_HEIGHT; y++) {
            double canvasY = calculateCanvasY(y);
            for(int x = 0; x < OVERLAY_WIDTH; x++) {
                color = memory.getColor(x, y);
                if (color != 0) {
                    double canvasX = calculateCanvasX(x);
                    render(context, canvasX, canvasY, color);
                }
            }
        }
    }

    private double calculateCanvasY(int y) {
        double characterY = Math.floor(y / 2);
        double remainderY = y % 2;
        double y1 = characterY * (parameters.scaledCharsetHeight + parameters.yGap);
        y1 += ( remainderY == 0 ) ? 0 : halfHeight;
        double terminalHeight = parameters.terminalHeight * (parameters.scaledCharsetHeight + parameters.yGap);
        y1 = terminalHeight - y1;
        return y1;
    }

    private double calculateCanvasX(int x) {
        double characterX = Math.floor(x / 2);
        double remainderX = x % 2;
        double x1 = characterX * (parameters.scaledCharsetWidth + parameters.xGap);
        x1 += (remainderX == 0) ? 0 : halfWidth;
        return x1;
    }

    private void render(Context2d context, double canvasX, double canvasY, int color) {
        context.setFillStyle(Palette.getInstance().getColor(color));
        context.fillRect(canvasX, canvasY - halfHeight , halfWidth, halfHeight);
    }
}

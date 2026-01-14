package com.example.client.radio86java;

import com.google.gwt.canvas.dom.client.CssColor;

public class Palette {

    private static Palette instance = new Palette();

    private final CssColor BLACK = GraphicUtil.getColor(0, 0, 0);
    private final CssColor WHITE = GraphicUtil.getColor(255, 255, 255);
    private final CssColor RED = GraphicUtil.getColor(255, 0, 0);
    private final CssColor GREEN = GraphicUtil.getColor(0, 255, 0);

    // it is expected to have the CssColor objects here, but in reality this is the String objects 
    private Object[] palette = new Object[] {BLACK, WHITE, RED, GREEN};

    public static Palette getInstance() {
        return instance;
    }

    public void setPalette(String[] colors) {
        int i = 0;
        for(String color : colors) {
            palette[i] = CssColor.make(color);
            i++;
        }
    }

    /**
     *  Example: green, #00ff00, #0f0, rgb(0,255,0)
     */
    public void setColor(int index, String color) {
        palette[index] = CssColor.make(color);
    }

    public String getColor(int index) {
        return (String)palette[index];
    }

    public int size() {
        return palette.length;
    }
}

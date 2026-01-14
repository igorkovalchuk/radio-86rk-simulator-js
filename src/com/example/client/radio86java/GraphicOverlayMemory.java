package com.example.client.radio86java;

import static com.example.client.radio86java.Constants.*;

public class GraphicOverlayMemory {

    static final int OVERLAY_WIDTH = TERMINAL_WIDTH * 2;
    static final int OVERLAY_HEIGHT = TERMINAL_HEIGHT * 2;

    private final int[][] memory = new int[OVERLAY_HEIGHT][OVERLAY_WIDTH];

    public void cls() {
        for(int y = 0; y < OVERLAY_HEIGHT; y ++) {
            for(int x = 0; x < OVERLAY_WIDTH; x++) {
                memory[y][x] = 0; 
            }
        }
    }

    public void setColor(int x, int y, int color) {
        if (x < 0 || y < 0 || x >= OVERLAY_WIDTH || y >= OVERLAY_HEIGHT ) {
            return;
        }
        memory[y][x] = color;
    }

    public int getColor(int x, int y) {
        if (x < 0 || y < 0 || x >= OVERLAY_WIDTH || y >= OVERLAY_HEIGHT ) {
            return 0;
        }
        return memory[y][x];
    }

    public int[][] cloneMemory() {
        int[][] copy = new int[OVERLAY_HEIGHT][OVERLAY_WIDTH];
        for(int y = 0; y < OVERLAY_HEIGHT; y ++) {
            for(int x = 0; x < OVERLAY_WIDTH; x++) {
                copy[y][x] = memory[y][x]; 
            }
        }
        return copy;
    }
}

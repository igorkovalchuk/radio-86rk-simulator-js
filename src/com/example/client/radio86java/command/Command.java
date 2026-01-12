package com.example.client.radio86java.command;

import com.example.client.radio86java.GraphicOverlayMemory;

public interface Command {

    void apply(GraphicOverlayMemory memory);

    double[] getLastXY();

    int getColor();
}

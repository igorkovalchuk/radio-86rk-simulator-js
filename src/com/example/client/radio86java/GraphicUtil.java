package com.example.client.radio86java;

import java.util.function.BiConsumer;

import com.google.gwt.canvas.dom.client.CssColor;

public final class GraphicUtil {

    public static CssColor getColor(int red, int green, int blue) {
        return CssColor.make(red, green, blue);
    }

    public static void line(int x1, int y1, int x2, int y2, BiConsumer<Integer, Integer> f) {
        int tmp;

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        if (x1 == x2) {
          if (y1 > y2) {
            tmp = y1;
            y1 = y2;
            y2 = tmp;
          }
          for (int y = y1; y <= y2; y++) {
            f.accept(x1, y);
          }
        } else if (y1 == y2) {
          if (x1 > x2) {
            tmp = x1;
            x1 = x2;
            x2 = tmp;
          }
          for (int x = x1; x <= x2; x++) {
            f.accept(x, y1);
          }
        } else {
          if (dx > dy) {
            if (x1 > x2) {
              tmp = x1;
              x1 = x2;
              x2 = tmp;
              tmp = y1;
              y1 = y2;
              y2 = tmp;
            }
            dy = y2 - y1;
            for (int x = x1; x <= x2; x++) {
              f.accept(x, y1 + (x - x1) * dy / dx);
            }
          } else {
            if (y1 > y2) {
              tmp = x1;
              x1 = x2;
              x2 = tmp;
              tmp = y1;
              y1 = y2;
              y2 = tmp;
            }
            dx = x2 - x1;
            for (int y = y1; y <= y2; y++) {
              f.accept(x1 + (y - y1) * dx / dy, y);
            }
          }
        }
      }

}

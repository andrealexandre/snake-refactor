package org.snake.views;

import java.awt.*;

public class GrassView implements FigureView {
    public static final GrassView INSTANCE = new GrassView();

    private GrassView() {
        // don't allow direct instantiation
    }

    @Override
    public boolean isEmptySpace() {
        return true;
    }

    @Override
    public void draw(Graphics canvas, int x, int y, int width, int height) {
        // Do nothing
    }
}

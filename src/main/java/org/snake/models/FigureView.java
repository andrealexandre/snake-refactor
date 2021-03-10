package org.snake.models;

import java.awt.Graphics;

public interface FigureView {
    boolean isEmptySpace();
	void draw(Graphics canvas, int width, int height);
}
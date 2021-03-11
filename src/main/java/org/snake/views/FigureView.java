package org.snake.views;

import java.awt.Graphics;

public interface FigureView {
	boolean isEmptySpace();
	void draw(Graphics canvas, int x, int y, int width, int height);
}
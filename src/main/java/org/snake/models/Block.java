package org.snake.models;

import java.awt.Color;
import java.awt.Graphics;

public class Block implements FigureView {

	@Override
	public boolean isEmptySpace() {
		return false;
	}

	@Override
	public void draw(Graphics canvas, int x, int y){
		canvas.setColor(Color.lightGray);
		canvas.fill3DRect(0, 0, x, y, true);	
	}

}

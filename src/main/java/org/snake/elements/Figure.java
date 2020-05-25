package org.snake.elements;

import java.awt.Graphics;

public abstract class Figure{	
	public Figure(){
		super();
	}
	
	public abstract void draw(Graphics canvas, int x, int y);	
}

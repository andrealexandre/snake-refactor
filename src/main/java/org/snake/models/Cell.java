package org.snake.models;

import org.snake.views.FigureView;

import java.awt.Graphics;

import javax.swing.JPanel;

public class Cell extends JPanel {
	public static final long serialVersionUID = 0;

	private FigureView view;
		
	public Cell() {
		super();
		this.setOpaque(false);		
	}
		
	public void setFigure(FigureView f){
		view = f;
		this.repaint();
	}
	
	public void removeFigure(){
		view = null;
		this.repaint();
	}
	
	public void paintComponent(Graphics canvas){		
		super.paintComponent(canvas);				
		
		if(view != null){
			view.draw(canvas, this.getWidth(), this.getHeight());
		}
	}	
	
	public boolean isGrass() {
		return view == null;
	}
	
	public boolean isEmptySpace() {
		if(view != null) return view.isEmptySpace();
		else return true;
	}
}

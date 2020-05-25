package elements;

import java.awt.Graphics;

import javax.swing.JPanel;

import elements.Snake.SnakeVertebrae;

public class Cell extends JPanel {
	public static final long serialVersionUID = 0;	
	
	private Figure image;	
		
	public Cell() {
		super();
		this.setOpaque(false);		
	}
		
	public void setFigure(Figure f){
		image = f;
		this.repaint();
	}
	
	public void removeFigure(){
		image = null;
		this.repaint();
	}
	
	public void paintComponent(Graphics canvas){		
		super.paintComponent(canvas);				
		
		if(image != null){
			image.draw(canvas, this.getWidth(), this.getHeight());
		}
	}	
	
	public boolean isGrass(){return image == null;}
	
	public boolean isVertebraeOrBlock()
	{return (image instanceof SnakeVertebrae) || (image instanceof Block);}	
}

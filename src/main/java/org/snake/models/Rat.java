package org.snake.models;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;

import org.snake.game.Board;
import org.snake.game.GameDataDisplay;

public class Rat implements ActionListener{	
	private Cell[][] board;
	private GameDataDisplay display;
	
	private Cell position;
	
	private Timer timer;
	private Random rn;
	private int numberOfRats;
	private int count;	
	
	private static final int SECOND = 1000;
	public static final int DESAPIRING_SECONDS = 10;
	
	public Rat(Board board, GameDataDisplay display){
		super();
		this.display = display;
		this.board = board.getBoard();
		rn = new Random();
		
		setRandomRat();
	}
		
	public void setRandomRat(){		
		int x ;
		int y ;
		
		do{
		x = rn.nextInt(this.board.length);
		y = rn.nextInt(this.board[0].length);
		}while(!(board[x][y].isGrass()));
		
		if(rn.nextInt(101) > 95){
			timer = new Timer(SECOND, this);		
			count = DESAPIRING_SECONDS;
			numberOfRats = rn.nextInt(5) + 1;			
			display.tempRats.setText("" + count);
			display.tempRats.setVisible(true);
			timer.start();
		}
		
		position = board[x][y];
		position.setFigure(new RatFigureView());
	}
	
	public int getNumberOfRats(){return numberOfRats;}
	
	public boolean isTimed(){return timer != null;}
	
	public Cell getPosition(){return position;}
	
	public void actionPerformed(ActionEvent a){
		if(count-- == 0){			
			timer.stop(); 
			removeRat(); 
			display.tempRats.setVisible(false);
			timer = null;		
		}
		
		display.tempRats.setText("" + count);
	}
	
	public void restart(){		
		position.removeFigure();
		setRandomRat();
	}
	
	public void removeRat(){
		position.removeFigure();
		setRandomRat();		
	}
	
	public void eatRat(){
		if(timer != null){
			timer.stop();			
			display.tempRats.setVisible(false);
			timer = null;			
		}
		
		setRandomRat();		
	}
	
	private class RatFigureView implements FigureView {
		private final Color BROWN = new Color(128, 64, 0);

		@Override
		public boolean isEmptySpace() {
			return true;
		}

		public void draw(Graphics canvas, int width, int height) {
			if (Rat.this.timer == null) {
				canvas.setColor(BROWN);
			} else {
				canvas.setColor(Color.yellow);
			}

			canvas.fillOval(0, 0, width, height);
			canvas.setColor(Color.BLACK);
			canvas.drawOval(0, 0, width, height);
		}
	}
}
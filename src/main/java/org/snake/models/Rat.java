package org.snake.models;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;

import org.snake.game.GameDataDisplay;
import org.snake.models.basic.Point;
import org.snake.views.FigureView;

public class Rat implements ActionListener {
	private final GameMatrix board;
	private final GameDataDisplay display;
	
	private Point position = null;
	
	private Timer timer;
	private final Random random;
	private int numberOfRats;
	private int count;	
	
	private static final int SECOND = 1000;
	public static final int DISAPPEARING_SECONDS = 10;
	
	public Rat(GameMatrix board, GameDataDisplay display){
		super();
		this.board = board;
		this.display = display;
		random = new Random();
		
		setRandomRat();
	}
		
	public void setRandomRat(){		
		int x ;
		int y ;

		do{
		x = random.nextInt(this.board.width());
		y = random.nextInt(this.board.height());
		}while(!(board.isEmpty(x, y)));
		
		if(random.nextInt(101) > 95){
			timer = new Timer(SECOND, this);		
			count = DISAPPEARING_SECONDS;
			numberOfRats = random.nextInt(5) + 1;
			display.tempRats.setText("" + count);
			display.tempRats.setVisible(true);
			timer.start();
		}

		position = new Point(x, y);
		board.setView(new RatFigureView(), position);
	}

	public int getNumberOfRats() {
		return numberOfRats;
	}

	public boolean isTimed() {
		return timer != null;
	}

	public Point getPosition() {
		return position;
	}
	
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
		board.removeView(position);
		setRandomRat();
	}
	
	public void removeRat(){
		board.removeView(position);
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

	public boolean inPosition(Point point) {
		return position.equals(point);
	}

	private class RatFigureView implements FigureView {
		private final Color BROWN = new Color(128, 64, 0);

		@Override
		public boolean isEmptySpace() {
			return true;
		}

		@Override
		public void draw(Graphics canvas, int x, int y, int width, int height) {
			if (Rat.this.timer == null) {
				canvas.setColor(BROWN);
			} else {
				canvas.setColor(Color.yellow);
			}

			canvas.fillOval(x, y, width, height);
			canvas.setColor(Color.BLACK);
			canvas.drawOval(x, y, width, height);
		}

	}
}
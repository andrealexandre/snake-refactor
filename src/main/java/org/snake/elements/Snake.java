package org.snake.elements;

import org.snake.game.Board;
import org.snake.game.GameWindow;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.Timer;

import org.snake.settings.GameConfiguration;
import org.snake.settings.SnakeSpeed;

public class Snake implements ActionListener{
	private GameWindow frame;
	private Cell[][] board;
	private Rat rat;
	
	private LinkedList<SnakeVertebrae> snake;
	private Timer timer;
	
	private int direction;
	private int snakeSpeed;
	private int points;
	
	private static final int STATIC = 0;		
	private static final int UP = KeyEvent.VK_UP;
	private static final int DOWN = KeyEvent.VK_DOWN;
	private static final int LEFT = KeyEvent.VK_LEFT;
	private static final int RIGHT = KeyEvent.VK_RIGHT;
	private static final int ADDITION_POINTS = 5;
	
	public Snake(GameWindow frame,Board board, Rat rat, GameConfiguration config){
		this.frame = frame;	
		this.rat = rat;
		this.board = board.getBoard();
		
		direction = STATIC;
		if(config != null){
			snakeSpeed = config.getSnakeSpeed().snakeSpeed;
		}else{
			snakeSpeed = SnakeSpeed.MEDIUM.snakeSpeed;
		}		
		
		timer = new Timer(snakeSpeed, this);
		Random rn = new Random();		
		snake = new LinkedList<SnakeVertebrae>();		
				
		int x = rn.nextInt(this.board.length);
		int y = rn.nextInt(this.board[0].length);
		
		SnakeVertebrae sv = new SnakeVertebrae(x, y);
		sv.setHead();
		snake.add(sv);
		this.board[x][y].setFigure(sv);		
	}
	
	public void restartSnake(){
		Iterator<SnakeVertebrae> i = snake.iterator();
		
		while(i.hasNext()){
			SnakeVertebrae p = i.next();		
			board[p.x][p.y].removeFigure();
		}
		
		timer = new Timer(snakeSpeed, this);
		direction = STATIC;
		points = 0;
		frame.display.points.setText("" + (points));
		snake = new LinkedList<SnakeVertebrae>();
		
		Random rn = new Random();						
		int x = rn.nextInt(this.board.length);
		int y = rn.nextInt(this.board[0].length);
		
		SnakeVertebrae sv = new SnakeVertebrae(x, y);
		sv.setHead();
		snake.add(sv);
		board[x][y].setFigure(sv);			
	}
		
	public void stopSnake(){timer.stop();}
	public void startSnake(){timer.start();}
	public int getPoints(){return points;}
	
	
	public void actionPerformed(ActionEvent a){		
		SnakeVertebrae last = snake.getLast();
		
		try{board[last.x][last.y].removeFigure();}
		catch(ArrayIndexOutOfBoundsException e){}
		
		SnakeVertebrae first = snake.getFirst();		
		first.setVertebrae();		
		int x = first.x;
		int y = first.y;		
		board[x][y].repaint();
		
		if(rat.getPosition() == board[x][y]){			
			if(rat.isTimed()){				
				points = points + ADDITION_POINTS + (SnakeSpeed.EASY.snakeSpeed - snakeSpeed) * rat.getNumberOfRats();
			}else{
				grow();
				board[x][y].repaint();
				points = points + ADDITION_POINTS + SnakeSpeed.EASY.snakeSpeed - snakeSpeed;;
			}
			
			frame.display.points.setText(String.valueOf(points));
			rat.eatRat();
		}
		
		switch(direction){
		case UP:
			y = --y < 0 ? board[0].length - 1 : y;
			break;
		case DOWN:
			y = ++y >= board[0].length ? 0 : y;
			break;
		case LEFT:
			x = --x < 0 ? board.length - 1 : x;
			break;
		case RIGHT:
			x = ++x >= board.length ? 0 : x;
			break;				
		}
		
		if(board[x][y].isVertebraeOrBlock()){
			timer.stop(); frame.gameover(); return;
		}
		
		last.x = x;
		last.y = y;
		
		board[x][y].setFigure(last);
		snake.addFirst(last);
		last.setHead();
		
		snake.removeLast();
	}
			
	public void moveSnake(int keyCode){		
		if(direction == STATIC){timer.start();}		
		
		switch(keyCode){
		case UP:			
			direction = direction == DOWN ? direction:UP;
			break;
		case DOWN:
			direction = direction == UP ? direction:DOWN;			
			break;
		case LEFT:
			direction = direction == RIGHT ? direction:LEFT;			
			break;
		case RIGHT:
			direction = direction == LEFT ? direction:RIGHT;
			break;				
		}
	}	
	
	public void grow(){
		SnakeVertebrae last = snake.getLast();		
		
		int x = last.x;
		int y = last.y;
		
		switch(direction){
		case UP:
			y = ++y > board[0].length ? 0 : y;						
			break;
		case DOWN: 
			y = --y < 0 ? board[0].length : y;
			break;
		case LEFT:
			x = ++x > board[0].length ? 0 : x;
			break;
		case RIGHT:
			x = --x < 0 ? board[0].length : x;
			break;				
		}		
		
		SnakeVertebrae sv = new SnakeVertebrae(x, y);
		sv.setVertebrae();	
		snake.addFirst(sv);
	}
	
	public class SnakeVertebrae extends Figure{		
		private int x;
		private int y;
		private boolean isHead;
		
		public SnakeVertebrae(int x, int y){
			super();
			this.x = x;
			this.y = y;
		}
		
		public void setHead(){isHead = true;}
		public void setVertebrae(){isHead = false;}
		
		public void draw(Graphics canvas, int x, int y){
			canvas.setColor(new Color(105, 210, 0));
			canvas.fillOval(0, 0, x, y);		
			canvas.setColor(Color.BLACK);
			canvas.drawOval(0, 0, x, y);	
			
			if(isHead){
				canvas.setColor(Color.BLUE);
				canvas.fillOval((x)/4, y/4, x/2, y/2);
				canvas.fillOval((x * 3)/4, y/4, x/2, y/2);		
			}	
		}
	}
}
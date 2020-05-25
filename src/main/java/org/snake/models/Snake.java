package org.snake.elements;

import org.snake.game.Board;
import org.snake.game.GameWindow;
import org.snake.settings.GameConfiguration;
import org.snake.settings.SnakeSpeed;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;

import static com.google.common.collect.Lists.newLinkedList;

public class Snake implements ActionListener{
	private GameWindow frame;
	private Cell[][] board;
	private Rat rat;
	
	private Deque<SnakeNode> snake;
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
		snake = new LinkedList<SnakeNode>();
				
		int x = rn.nextInt(this.board.length);
		int y = rn.nextInt(this.board[0].length);

		final SnakeNode snakeNode = new SnakeNode(new Point(x, y), true);
		snake.add(snakeNode);
		this.board[x][y].setFigure(snakeNode);
	}
	
	public void restart(){

		for (SnakeNode p : snake) {
			board[p.getPoint().x][p.getPoint().y].removeFigure();
		}
		
		timer = new Timer(snakeSpeed, this);
		direction = STATIC;
		points = 0;
		frame.display.points.setText("" + (points));
		snake = newLinkedList();
		
		Random rn = new Random();						
		int x = rn.nextInt(this.board.length);
		int y = rn.nextInt(this.board[0].length);
		
		SnakeNode sv = new SnakeNode(new Point(x, y), true);
		snake.add(sv);
		board[x][y].setFigure(sv);			
	}
		
	public void stopSnake(){timer.stop();}
	public void startSnake(){timer.start();}
	public int getPoints(){return points;}

	public void actionPerformed(ActionEvent a){		
		SnakeNode last = snake.getLast();

		try {
			board[last.getPoint().x][last.getPoint().y].removeFigure();
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		
		SnakeNode first = snake.getFirst();
		first.asTail();
		int x = first.getPoint().x;
		int y = first.getPoint().y;
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
		
		last.setPoint(new Point(x, y));
		
		board[x][y].setFigure(last);
		snake.addFirst(last);
		last.asHead();
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
		SnakeNode last = snake.getLast();
		
		int x = last.getPoint().x;
		int y = last.getPoint().y;
		
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
		
		SnakeNode sv = new SnakeNode(new Point(x, y), false);
		sv.asTail();
		snake.addFirst(sv);
	}

}
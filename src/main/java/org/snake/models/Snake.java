package org.snake.models;

import org.snake.game.Board;
import org.snake.game.GameWindow;
import org.snake.settings.GameConfiguration;
import org.snake.settings.SnakeSpeed;
import org.snake.views.SnakeNodeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;

import static com.google.common.collect.Lists.newLinkedList;

public class Snake implements ActionListener{
	private final GameWindow frame;
	private final Cell[][] board;
	private final Rat rat;
	
	private Deque<SnakeNodeView> snake;
	private Timer timer;
	
	private int direction;
	private final int snakeSpeed;
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
		snake = new LinkedList<>();
				
		int x = rn.nextInt(this.board.length);
		int y = rn.nextInt(this.board[0].length);

		final SnakeNodeView snakeNode = new SnakeNodeView(new SnakeNode(new Point(x, y), true));
		snake.add(snakeNode);
		this.board[x][y].setFigure(snakeNode);
	}
	
	public void restart(){

		for (SnakeNodeView p : snake) {
			final Point point = p.getModel().getPoint();
			board[point.x][point.y].removeFigure();
		}
		
		timer = new Timer(snakeSpeed, this);
		direction = STATIC;
		points = 0;
		frame.display.points.setText("" + (points));
		snake = newLinkedList();
		
		Random rn = new Random();						
		int x = rn.nextInt(this.board.length);
		int y = rn.nextInt(this.board[0].length);
		
		SnakeNodeView sv = new SnakeNodeView(new SnakeNode(new Point(x, y), true));
		snake.add(sv);
		board[x][y].setFigure(sv);
	}
		
	public void stopSnake() { timer.stop(); }
	public void startSnake() { timer.start(); }
	public int getPoints() { return points; }

	public void actionPerformed(ActionEvent a){		
		SnakeNodeView last = snake.getLast();

		try {
			final Point point = last.getModel().getPoint();
			board[point.x][point.y].removeFigure();
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		
		SnakeNodeView first = snake.getFirst();
		first.getModel().asTail();
		final Point point = first.getModel().getPoint();
		board[point.x][point.y].repaint();
		
		if(rat.getPosition() == board[point.x][point.y]){
			if(rat.isTimed()){				
				points = points + ADDITION_POINTS + (SnakeSpeed.EASY.snakeSpeed - snakeSpeed) * rat.getNumberOfRats();
			}else{
				grow();
				board[point.x][point.y].repaint();
				points = points + ADDITION_POINTS + SnakeSpeed.EASY.snakeSpeed - snakeSpeed;
			}
			
			frame.display.points.setText(String.valueOf(points));
			rat.eatRat();
		}

		int x = point.x;
		int y = point.y;
		
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

		if(!board[x][y].isEmptySpace()){
			timer.stop(); frame.gameover(); return;
		}
		
		last.getModel().setPoint(new Point(x, y));
		
		board[x][y].setFigure(last);
		snake.addFirst(last);
		last.getModel().asHead();
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
		SnakeNodeView last = snake.getLast();
		
		int x = last.getModel().getPoint().x;
		int y = last.getModel().getPoint().y;
		
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
		
		SnakeNodeView sv = new SnakeNodeView(new SnakeNode(new Point(x, y), false));
		snake.addFirst(sv);
	}

}
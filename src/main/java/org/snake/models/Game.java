package org.snake.models;

import org.snake.game.Board;
import org.snake.settings.GameConfiguration;
import org.snake.settings.SnakeSpeed;
import org.snake.views.SnakeNodeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;

import static com.google.common.collect.Lists.newLinkedList;

public class Game {
	private final OnChangeListener onChangeListener;

	private final Cell[][] board;
	private final Rat rat;
	private final PointSpace pointSpace;

	private Deque<SnakeNodeView> snake;
	private Timer timer;

	private Vector direction;
	private final int snakeSpeed;
	private int points;

	private static final int UP = KeyEvent.VK_UP;
	private static final int DOWN = KeyEvent.VK_DOWN;
	private static final int LEFT = KeyEvent.VK_LEFT;
	private static final int RIGHT = KeyEvent.VK_RIGHT;
	private static final int ADDITION_POINTS = 5;
	
	public Game(OnChangeListener onChangeListener, Board board, Rat rat, GameConfiguration config) {
		this.onChangeListener = onChangeListener;
		this.rat = rat;
		this.board = board.getBoard();
		this.pointSpace = new PointSpace(new Range(this.board.length), new Range(this.board[0].length));

		direction = new Vector(0, 0);
		if(config != null){
			snakeSpeed = config.getSnakeSpeed().snakeSpeed;
		}else{
			snakeSpeed = SnakeSpeed.MEDIUM.snakeSpeed;
		}		
		
		timer = new Timer(snakeSpeed, this::actionPerformed);
		Random rn = new Random();		
		snake = new LinkedList<>();
				
		int x = rn.nextInt(this.board.length);
		int y = rn.nextInt(this.board[0].length);

		final SnakeNodeView snakeNode = new SnakeNodeView(new SnakeNode(new Point(x, y), true));
		snake.add(snakeNode);
		this.board[x][y].setFigure(snakeNode);
	}
	
	public void restart() {
		for (SnakeNodeView p : snake) {
			final Point point = p.getModel().getPoint();
			board[point.x][point.y].removeFigure();
		}
		
		timer = new Timer(snakeSpeed, this::actionPerformed);
		direction = new Vector();
		points = 0;
		onChangeListener.onPointsChange(points);
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

		if (rat.getPosition() == board[point.x][point.y]) {
			if (rat.isTimed()) {
				points = points + ADDITION_POINTS + (SnakeSpeed.EASY.snakeSpeed - snakeSpeed) * rat.getNumberOfRats();
			} else {
				grow();
				board[point.x][point.y].repaint();
				points = points + ADDITION_POINTS + SnakeSpeed.EASY.snakeSpeed - snakeSpeed;
			}
			onChangeListener.onPointsChange(points);
			rat.eatRat();
		}

		final Point newPoint = pointSpace.around(direction.add(point));

		if(!board[newPoint.x][newPoint.y].isEmptySpace()){
			timer.stop();
			onChangeListener.onGameOver();
			return;
		}
		
		last.getModel().setPoint(newPoint);
		
		board[newPoint.x][newPoint.y].setFigure(last);
		snake.addFirst(last);
		last.getModel().asHead();
		snake.removeLast();
	}
			
	public void moveSnake(int keyCode){		
		if(direction.isOrigin()) {
			timer.start();
		}

		Vector newDirection = null;
		
		switch(keyCode){
		case UP:
			newDirection = new Vector(0, -1);
			break;
		case DOWN:
			newDirection = new Vector(0, +1);
			break;
		case LEFT:
			newDirection = new Vector(-1, 0);
			break;
		case RIGHT:
			newDirection = new Vector(+1, 0);
			break;				
		}

		if(newDirection != null) {
			Vector result = direction.add(newDirection);
			if (!result.isOrigin()) {
				direction = newDirection;
			}
		}
	}	
	
	public void grow(){
		SnakeNodeView last = snake.getLast();
		final Point newPoint = pointSpace.around(direction.add(last.getModel().getPoint()));

		SnakeNodeView sv = new SnakeNodeView(new SnakeNode(newPoint, false));
		snake.addFirst(sv);
	}


	public interface OnChangeListener {
		void onPointsChange(int points);
		void onGameOver();
	}
}
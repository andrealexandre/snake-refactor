package org.snake.game;

import org.snake.models.Game;
import org.snake.models.GameMatrix;
import org.snake.models.Rat;
import org.snake.models.basic.Vector;
import org.snake.settings.FileManager;
import org.snake.settings.GameConfiguration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class GameWindow extends JFrame {
	public static final long serialVersionUID = 1L;
	public static final int WIDTH = 30;
	public static final int HEIGHT = 35;

	public FileManager fileManager; // Game window shouldn't care about this
	
	private final GameMenu callerMenu; // GameMenu should be mentioned here, we are creating direct cyclic dependencies
	public GameDataDisplay display = new GameDataDisplay(); // Game window shouldn't instantiate this

	private final GameMatrix gameMatrix = new GameMatrix(WIDTH, HEIGHT);
	private final Board board = new Board(gameMatrix); // Game window shouldn't instantiate this
	private final Rat rat = new Rat(gameMatrix, display); // Game window shouldn't instantiate this
	private final Game game;

	private GameState gameState = GameState.PLAYING;

	public GameWindow(String windowTitle, GameMenu callerMenu, GameConfiguration config){
		super(windowTitle);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addKeyListener(new KeyboardInterface());

		this.callerMenu = callerMenu;
		
		fileManager = new FileManager(config.getLabyrinthPath());
		fileManager.loadLabyrinth(gameMatrix); // side effects leak

		game = new Game(new Game.OnChangeListener() {
			@Override
			public void onPointsChange(int points) {
				display.points.setText(String.valueOf(points));
			}

			@Override
			public void onGameOver() {
				gameover();
			}

			@Override
			public void onGameMatrixUpdate() {
				board.repaint();
			}
		}, gameMatrix, rat, config); // Game window shouldn't instantiate this
		
		setLayout(new BorderLayout());
		
		add(display, BorderLayout.NORTH);
		add(board, BorderLayout.SOUTH);
		
		pack();
		setVisible(true);
	}
	
	public int getCurrentPoint(){return game.getPoints();}
	
	public GameState getGameState() {
		return gameState;
	}
	
	public void restart(){
		gameState = GameState.PLAYING;
		game.restart();
		rat.restart();
	}
	
	public void close(){
		callerMenu.setVisible(true);		
		this.dispose();}
	
	public void setGameState(GameState code){
		if (code == GameState.PLAYING) {
			game.startSnake();
		}
		gameState = code;
	}
	
	public void gameover(){
		updateGameState(GameState.GAMEOVER);
	}
	
	public void callDialog(){
		game.stopSnake();
		new GameDialog(this);
	}

	private class KeyboardInterface extends KeyAdapter{
		public void keyPressed(KeyEvent k){
			int code = k.getKeyCode();

			final List<Integer> keys = newArrayList(
					KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT);

			if (keys.contains(code) && !(gameState == GameState.PAUSED || gameState == GameState.GAMEOVER)) {
				game.moveSnake(code);
			} else {
				if (code == KeyEvent.VK_ESCAPE) {
					updateGameState(GameState.PAUSED);
				}
			}
		}
	}

	private void updateGameState(GameState paused) {
		gameState = paused;
		callDialog();
	}
}
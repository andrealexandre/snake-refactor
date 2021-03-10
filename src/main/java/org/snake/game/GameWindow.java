package org.snake.game;

import org.snake.elements.Rat;
import org.snake.elements.Snake;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JFrame;

import org.snake.settings.FileManager;
import org.snake.settings.GameConfiguration;

import static com.google.common.collect.Lists.newArrayList;

public class GameWindow extends JFrame{
	public static final long serialVersionUID = 1L;
	
	public FileManager fileManager; // Game window shouldn't care about this
	
	private GameMenu callerMenu; // GameMenu should be mentioned here, we are creating direct cyclic dependencies
	public GameDataDisplay display = new GameDataDisplay(); // Game window shouldn't instantiate this
	
	private Board board = new Board(); // Game window shouldn't instantiate this
	private Rat rat = new Rat(board, display); // Game window shouldn't instantiate this
	private Snake snake;

	
	private GameState gameState = GameState.PLAYING;

	public GameWindow(String windowTitle, GameMenu callerMenu, GameConfiguration config){
		super(windowTitle);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addKeyListener(new KeyboardInterface());

		this.callerMenu = callerMenu;
		
		fileManager = new FileManager(config.getLabyrinthPath());
		fileManager.loadLabyrinth(board.getBoard()); // side effects leak

		snake = new Snake(this, board, rat, config); // Game window shouldn't instantiate this
		
		setLayout(new BorderLayout());
		
		add(display, BorderLayout.NORTH);
		add(board, BorderLayout.SOUTH);
		
		pack();
		setVisible(true);
	}
	
	public int getCurrentPoint(){return snake.getPoints();}
	
	public GameState getGameState() {
		return gameState;
	}
	
	public void restart(){
		gameState = GameState.PLAYING;
		snake.restart();
		rat.restart();
	}
	
	public void close(){
		callerMenu.setVisible(true);		
		this.dispose();}
	
	public void setGameState(GameState code){
		if (code == GameState.PLAYING) {
			snake.startSnake();
		}
		gameState = code;
	}
	
	public void gameover(){
		updateGameState(GameState.GAMEOVER);
	}
	
	public void callDialog(){
		snake.stopSnake();
		new GameDialog(this);
	}

	private class KeyboardInterface extends KeyAdapter{
		public void keyPressed(KeyEvent k){
			int code = k.getKeyCode();

			final List<Integer> keys = newArrayList(
					KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT);

			if (keys.contains(code) && !(gameState == GameState.PAUSED || gameState == GameState.GAMEOVER)) {
				snake.moveSnake(code);
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
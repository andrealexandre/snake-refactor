package org.snake.game;

import org.snake.elements.Rat;
import org.snake.elements.Snake;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import org.snake.settings.FileManager;
import org.snake.settings.GameConfiguration;

public class GameWindow extends JFrame{
	public static final long serialVersionUID = 1L;
	
	public FileManager fileManager;
	
	private GameMenu callerMenu;	
	public GameDataDisplay display;
	
	private Board board = new Board();
	private Snake snake;
	private Rat rat;
	
	private GameState gameState = GameState.PLAYING;

	public GameWindow(String windowTitle, GameMenu callerMenu, GameConfiguration config){
		super(windowTitle);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addKeyListener(new KeyboardInterface());

		this.callerMenu = callerMenu;
		
		fileManager = new FileManager(config.getLabyrinthPath());
		fileManager.loadLabyrinth(board.getBoard());
		
		display = new GameDataDisplay();
		rat = new Rat(board, display);
		snake = new Snake(this, board, rat, config);
		
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
		snake.restartSnake();
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
		gameState = GameState.GAMEOVER;
		callDialog();
	}
	
	public void callDialog(){
		snake.stopSnake();
		new GameDialog(this);
	}

	private class KeyboardInterface extends KeyAdapter{
		public void keyPressed(KeyEvent k){
			int code = k.getKeyCode();					
			
			if((code == KeyEvent.VK_UP || 
				code == KeyEvent.VK_DOWN || 
				code == KeyEvent.VK_LEFT || 
				code == KeyEvent.VK_RIGHT)
				&& !((gameState == GameState.PAUSED) || (gameState == GameState.GAMEOVER) )){
				snake.moveSnake(code);
			}else{
				if(code == KeyEvent.VK_ESCAPE){
					gameState = GameState.PAUSED;
					callDialog();
				}
			}
		}
	}
}
package game;

import elements.Rat;
import elements.Snake;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import settings.FileManager;
import settings.GameConfiguration;

public class GameWindow extends JFrame{
	public static final long serialVersionUID = 1L;
	
	public FileManager fileManager;
	
	private GameMenu callerMenu;	
	public GameDataDisplay display;
	
	private Board board;
	private Snake snake;
	private Rat rat;
	
	private int gameState;
	
	public static final int PLAYING = 0;
	public static final int PAUSED = 1;
	public static final int GAMEOVER = 2;
	
	public GameWindow(GameMenu callerMenu, GameConfiguration config){
		super("Snake");
		
		this.setResizable(false);
		this.addKeyListener(new KeyboardInterface());		
		this.callerMenu = callerMenu;		
		
		gameState = PLAYING;
		
		fileManager = new FileManager(config.getLabyrinthPath());		
				
		board = new Board();
		
		fileManager.loadLabyrinth(board.getBoard());	
		
		display = new GameDataDisplay();
		rat = new Rat(board, display);
		snake = new Snake(this, board, rat, config);
		
		setLayout(new BorderLayout());
		
		add(display, BorderLayout.NORTH);
		add(board, BorderLayout.SOUTH);
		
		pack();		
		setLocationRelativeTo(null);		
		setVisible(true);		
		setDefaultCloseOperation(EXIT_ON_CLOSE);		
	}
	
	public int getCurrentPoint(){return snake.getPoints();}
	
	public int getGameState() {return gameState;} 
	
	public void restart(){
		gameState = PLAYING;
		snake.restartSnake();
		rat.restart();
	}
	
	public void close(){
		callerMenu.setVisible(true);		
		this.dispose();}
	
	public void setGameState(int code){
		if(code == PLAYING){snake.startSnake();}
		gameState = code;
	}
	
	public void gameover(){
		gameState = GAMEOVER;
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
				&& !((gameState == PAUSED) || (gameState == GAMEOVER) )){				
				snake.moveSnake(code);
			}else{
				if(code == KeyEvent.VK_ESCAPE){
					gameState = PAUSED;
					callDialog();
				}
			}
		}
	}
}
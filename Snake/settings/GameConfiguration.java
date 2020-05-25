package settings;

public class GameConfiguration {
	private int snakeSpeed;
	private String labyrinth;
	
	public static final int SNAKE_SPEED_EASY = 100;
	public static final int SNAKE_SPEED_MEDIUM = 80;
	public static final int SNAKE_SPEED_HARD = 50;
	
	public GameConfiguration(){
		snakeSpeed = SNAKE_SPEED_MEDIUM;
		labyrinth = null;
	}
	
	public void setLabyrinth(String labyrinthName){labyrinth = labyrinthName;}
	public String getLabyrinthPath(){return labyrinth;}
	
	public void setSnakeSpeedAtEasy(){this.snakeSpeed = SNAKE_SPEED_EASY;}
	public void setSnakeSpeedAtMedium(){this.snakeSpeed = SNAKE_SPEED_MEDIUM;}
	public void setSnakeSpeedAtHard(){this.snakeSpeed = SNAKE_SPEED_HARD;}
	public int getSnakeSpeed(){return snakeSpeed;}
}

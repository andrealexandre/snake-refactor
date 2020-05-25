package org.snake.settings;

public class GameConfiguration {

	private SnakeSpeed snakeSpeed;
	private String labyrinthPath;

	public GameConfiguration(SnakeSpeed snakeSpeed, String labyrinthPath) {
		this.snakeSpeed = snakeSpeed;
		this.labyrinthPath = labyrinthPath;
	}

	public GameConfiguration() {
		this(SnakeSpeed.MEDIUM, null);
	}

	public SnakeSpeed getSnakeSpeed() {
		return snakeSpeed;
	}
	public void setSnakeSpeed(SnakeSpeed snakeSpeed) {
		this.snakeSpeed = snakeSpeed;
	}

	public String getLabyrinthPath() {
		return labyrinthPath;
	}
	public void setLabyrinthPath(String labyrinthPath) {
		this.labyrinthPath = labyrinthPath;
	}
}

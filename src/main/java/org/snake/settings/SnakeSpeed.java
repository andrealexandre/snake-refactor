package org.snake.settings;

public enum SnakeSpeed {
    EASY(100),
    MEDIUM(80),
    HARD(50);

    public final int snakeSpeed;

    SnakeSpeed(int snakeSpeed) {
        this.snakeSpeed = snakeSpeed;
    }
}
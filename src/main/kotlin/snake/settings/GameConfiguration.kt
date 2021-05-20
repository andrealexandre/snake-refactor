package snake.settings

data class GameConfiguration(
    val snakeSpeed: SnakeSpeed = SnakeSpeed.MEDIUM,
    val labyrinthPath: String? = null
)
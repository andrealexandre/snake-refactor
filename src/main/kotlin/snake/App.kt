package snake

import snake.game.GameMenu
import snake.settings.GameConfiguration

fun main() {
    // Date
    val menuTitle = "Snake Game Menu"
    val gameWindowTitle = "Snake"

    // Setup
    val gameConfiguration = GameConfiguration()
    GameMenu(menuTitle, gameConfiguration).start(gameWindowTitle)
}
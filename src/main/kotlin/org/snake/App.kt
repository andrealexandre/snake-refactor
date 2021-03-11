package org.snake

import org.snake.game.GameMenu
import kotlin.jvm.JvmStatic
import org.snake.settings.GameConfiguration

class App {
    val greeting: String
        get() = "Hello world."

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            // Date
            val menuTitle = "Snake Game Menu"
            val gameWindowTitle = "Snake"

            // Setup
            val gameConfiguration = GameConfiguration()
            GameMenu(menuTitle, gameConfiguration).start(gameWindowTitle)
        }
    }
}
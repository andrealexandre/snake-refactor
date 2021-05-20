package snake.game

import snake.settings.GameConfiguration
import java.awt.BorderLayout
import javax.swing.JButton
import javax.swing.JFrame
import kotlin.system.exitProcess

class GameMenu(menuTitle: String, var config: GameConfiguration) : JFrame(menuTitle) {
    fun start(gameWindowTitle: String) {
        // Component setup
        layout = BorderLayout()
        setSize(250, 110)
        isResizable = false
        setLocationRelativeTo(null)
        defaultCloseOperation = EXIT_ON_CLOSE

        // Components
        val start = JButton("Start").apply {
            addActionListener {
                this@GameMenu.isVisible = false
                GameWindow(gameWindowTitle, this@GameMenu, config)
            }
        }
        val options = JButton("Options").apply {
            addActionListener { OptionsFrame(this@GameMenu) }
        }
        val exit = JButton("Exit").apply {
            addActionListener { exitProcess(0) }
        }

        add(start, BorderLayout.NORTH)
        add(options, BorderLayout.CENTER)
        add(exit, BorderLayout.SOUTH)

        isVisible = true
    }

}
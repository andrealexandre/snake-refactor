package org.snake.game

import org.snake.settings.GameConfiguration
import java.awt.BorderLayout
import java.awt.event.ActionEvent
import javax.swing.JButton
import javax.swing.JFrame

class GameMenu(menuTitle: String?, var config: GameConfiguration) : JFrame(menuTitle) {
    fun start(gameWindowTitle: String?) {
        // Component setup
        layout = BorderLayout()
        setSize(250, 110)
        isResizable = false
        setLocationRelativeTo(null)
        defaultCloseOperation = EXIT_ON_CLOSE

        // Components
        val start = JButton("Start")
        val options = JButton("Options")
        val exit = JButton("Exit")
        add(start, BorderLayout.NORTH)
        add(options, BorderLayout.CENTER)
        add(exit, BorderLayout.SOUTH)

        // Listeners
        start.addActionListener { event: ActionEvent? ->
            this.isVisible = false
            GameWindow(gameWindowTitle, this, config)
        }
        options.addActionListener { event: ActionEvent? -> OptionsFrame(this) }
        exit.addActionListener { event: ActionEvent? -> System.exit(0) }
        isVisible = true
    }

    companion object {
        const val serialVersionUID = 1L
    }
}
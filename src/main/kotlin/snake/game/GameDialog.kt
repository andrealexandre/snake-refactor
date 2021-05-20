package snake.game

import snake.settings.FileManager
import snake.settings.Player
import java.awt.BorderLayout
import java.awt.FlowLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.*

class GameDialog(private val gameWindow: GameWindow,
                 private val fileManager: FileManager) : JDialog(gameWindow), ActionListener {

    override fun actionPerformed(a: ActionEvent) {
        when(a.actionCommand) {
            "Play" -> {
                gameWindow.setGameState(GameState.PLAYING)
                gameWindow.isVisible = true
                dispose()
            }
            "Restart", "Close" -> {
                if (fileManager.isNewHighScore(gameWindow.currentPoint)) {
                    NameDialog(a.actionCommand, gameWindow, fileManager)
                } else {
                    when(a.actionCommand) {
                        "Restart" -> gameWindow.restart()
                        "Close" -> gameWindow.close()
                    }
                    gameWindow.isVisible = true
                    dispose()
                }
            }
        }
    }

    private class NameDialog(val action: String,
                             val gameWindow: GameWindow,
                             val fileManager: FileManager) : JDialog(gameWindow, "New HighScore"), ActionListener {
        private val name: JTextField

        override fun actionPerformed(a: ActionEvent) {
            if (isNameValid(name.text)) {
                fileManager.addNewPlayerScore(Player(name.text, gameWindow.currentPoint))
                when (action) {
                    "Restart" -> gameWindow.restart()
                    "Close" -> gameWindow.close()
                }
                dispose()
                TimedHighScore(gameWindow, fileManager)
            }
        }

        fun isNameValid(name: String): Boolean {
            return if (name.isEmpty()) false else name.all { Character.isLetterOrDigit(it) }
        }

        init {
            layout = BorderLayout()
            val j = JPanel()
            j.layout = FlowLayout()
            val b = JButton("OK")
            b.addActionListener(this)
            name = JTextField(10)
            j.add(name)
            j.add(b)
            add(JLabel("Your name", JLabel.CENTER), BorderLayout.NORTH)
            add(j, BorderLayout.CENTER)
            pack()
            isResizable = false
            isAlwaysOnTop = true
            setLocationRelativeTo(null)
            isVisible = true
            defaultCloseOperation = DO_NOTHING_ON_CLOSE
            name.requestFocusInWindow()
        }
    }

    private class TimedHighScore(g: GameWindow, fileManager: FileManager) : JFrame("HighScore") {
        val timer: Timer = Timer(5000) { dispose() }

        override fun dispose() {
            timer.stop()
            super.dispose()
        }

        init {
            add(fileManager.displayHighScore())
            setSize(256, 128)
            isResizable = false
            setLocationRelativeTo(null)
            isVisible = true
            defaultCloseOperation = DISPOSE_ON_CLOSE
            timer.start()
        }
    }

    companion object {
        const val serialVersionUID = 1L
    }

    init {
        gameWindow.isVisible = false
        this.isResizable = false
        layout = BorderLayout()

        //Buttons Panel************
        val j1 = JPanel()
        j1.layout = FlowLayout()
        if (gameWindow.getGameState() == GameState.PAUSED) {
            title = "Pause"
            val play = JButton("Play")
            play.addActionListener(this)
            j1.add(play)
        } else {
            if (gameWindow.getGameState() == GameState.GAMEOVER) {
                title = "GameOver"
            }
        }
        val restart = JButton("Restart")
        val close = JButton("Close")
        restart.addActionListener(this)
        close.addActionListener(this)
        j1.add(restart)
        j1.add(close)
        //*******************************
        add(fileManager.displayHighScore(), BorderLayout.NORTH)
        add(j1, BorderLayout.CENTER)
        pack()
        isResizable = false
        isAlwaysOnTop = true
        setLocationRelativeTo(null)
        isVisible = true
        defaultCloseOperation = DO_NOTHING_ON_CLOSE
    }
}
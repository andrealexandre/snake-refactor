package org.snake.game

import java.awt.BorderLayout
import java.awt.FlowLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.*

class GameDialog(j: GameWindow) : JDialog(j), ActionListener {
    override fun actionPerformed(a: ActionEvent) {
        val s = a.actionCommand
        val g = this.owner as GameWindow
        if (s == "Play") {
            g.setGameState(GameState.PLAYING)
            g.isVisible = true
            dispose()
        } else {
            if (g.fileManager.isNewHighScore(g.currentPoint)) {
                NameDialog(g, s)
                g.isVisible = true
                dispose()
                return
            }
            if (s == "Restart") {
                g.restart()
                g.isVisible = true
            } else {
                g.close()
            }
            dispose()
        }
    }

    private class NameDialog(d: GameWindow?, action: String) : JDialog(d, "New HighScore"), ActionListener {
        private val name: JTextField
        private val action: String
        override fun actionPerformed(a: ActionEvent) {
            if (!isNameValid(name.text)) {
                return
            }
            val g = this.owner as GameWindow
            g.fileManager.addNewScore(name.text, g.currentPoint)
            if (action == "Restart") {
                g.restart()
            } else {
                g.close()
            }
            dispose()
            TimedHighScore(g)
        }

        companion object {
            const val serialVersionUID = 1L
            fun isNameValid(s: String): Boolean {
                if (s.isEmpty()) {
                    return false
                }
                for (i in 0 until s.length) {
                    val c = s[i]
                    if (!Character.isAlphabetic(c.toInt()) && !Character.isDigit(c)) return false
                }
                return true
            }
        }

        init {
            layout = BorderLayout()
            val j = JPanel()
            j.layout = FlowLayout()
            val b = JButton("OK")
            b.addActionListener(this)
            name = JTextField(10)
            this.action = action
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

    private class TimedHighScore(g: GameWindow) : JFrame("HighScore") {
        val timer: Timer = Timer(5000) { dispose() }

        override fun dispose() {
            timer.stop()
            super.dispose()
        }

        init {
            add(g.fileManager.displayHighScore)
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
        j.isVisible = false
        this.isResizable = false
        layout = BorderLayout()

        //Buttons Panel************
        val j1 = JPanel()
        j1.layout = FlowLayout()
        if (j.getGameState() == GameState.PAUSED) {
            title = "Pause"
            val play = JButton("Play")
            play.addActionListener(this)
            j1.add(play)
        } else {
            if (j.getGameState() == GameState.GAMEOVER) {
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
        add(j.fileManager.displayHighScore, BorderLayout.NORTH)
        add(j1, BorderLayout.CENTER)
        pack()
        isResizable = false
        isAlwaysOnTop = true
        setLocationRelativeTo(null)
        isVisible = true
        defaultCloseOperation = DO_NOTHING_ON_CLOSE
    }
}
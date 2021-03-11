package org.snake.game

import org.snake.models.GameMatrix
import org.snake.settings.FileManager
import org.snake.settings.SnakeSpeed
import java.awt.BorderLayout
import java.awt.FlowLayout
import java.awt.Font
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.File
import javax.swing.*
import javax.swing.border.EtchedBorder

class OptionsFrame(caller: GameMenu) : JFrame("Options"), ActionListener {
    private val caller: GameMenu
    private val lp: LabyrinthPreviewer
    private val snakeSpeedEasy: JRadioButton
    private val snakeSpeedMedium: JRadioButton
    private val snakeSpeedHard: JRadioButton
    private fun creatGameConfiguration() {
        val gameConfiguration = caller.config
        if (snakeSpeedEasy.isSelected) {
            gameConfiguration.snakeSpeed = SnakeSpeed.EASY
        } else if (snakeSpeedMedium.isSelected) {
            gameConfiguration.snakeSpeed = SnakeSpeed.MEDIUM
        } else if (snakeSpeedHard.isSelected) {
            gameConfiguration.snakeSpeed = SnakeSpeed.HARD
        } else {
            gameConfiguration.snakeSpeed = SnakeSpeed.MEDIUM
        }
        gameConfiguration.labyrinthPath = lp.getLabyrinthName()
        caller.config = gameConfiguration!!
    }

    override fun actionPerformed(a: ActionEvent) {
        val s = a.actionCommand
        if (s == "OK") {
            creatGameConfiguration()
            dispose()
            caller.isVisible = true
        } else {
            if (s == "Cancel") {
                dispose()
                caller.isVisible = true
            }
        }
    }

    private inner class LabyrinthPreviewer : JPanel(), ActionListener {
        val cells = GameMatrix(30, 35)
        val board = Board(cells)
        var labyrinthName: JLabel
        var labyrinths: Array<String>
        var idx: Int
        fun getLabyrinthName(): String? {
            return if (idx < 0 || idx >= labyrinths.size) {
                null
            } else labyrinths[idx]
        }

        override fun actionPerformed(a: ActionEvent) {
            val action = a.actionCommand
            board.clearLabyrinth()
            if (action == "Next") {
                ++idx
                if (idx >= labyrinths.size) {
                    idx = -1
                    labyrinthName.text = "No Labyrinth"
                } else {
                    val LabyrinthPath: String = FileManager.Companion.LABYRINTH_PATH + labyrinths[idx] + FileManager.Companion.LABYRINTH_EXT
                    FileManager.Companion.loadLabyrinth(cells, LabyrinthPath)
                    labyrinthName.text = labyrinths[idx]
                }
            } else {
                if (action == "Previous") {
                    --idx
                    if (idx < 0) {
                        idx = labyrinths.size
                        labyrinthName.text = "No Labyrinth"
                    } else {
                        val LabyrinthPath: String = FileManager.Companion.LABYRINTH_PATH + labyrinths[idx] + FileManager.Companion.LABYRINTH_EXT
                        FileManager.Companion.loadLabyrinth(cells, LabyrinthPath)
                        labyrinthName.text = labyrinths[idx]
                    }
                }
            }
            board.repaint()
        }

        init {
            layout = BorderLayout()
            border = EtchedBorder()
            val next = JButton("Next")
            val previous = JButton("Previous")
            next.addActionListener(this)
            previous.addActionListener(this)
            labyrinthName = JLabel("NoLabyrinth", JLabel.CENTER)
            labyrinthName.font = Font("Comic Sans MS", Font.BOLD, 14)
            add(next, BorderLayout.EAST)
            add(labyrinthName, BorderLayout.CENTER)
            add(previous, BorderLayout.WEST)
            add(board, BorderLayout.NORTH)
            val f = File("labyrinths/")
            labyrinths = f.list()
            for (i in labyrinths.indices) {
                labyrinths[i] = labyrinths[i].substring(0, labyrinths[i].lastIndexOf("."))
            }
            idx = -1
        }
    }

    companion object {
        const val serialVersionUID = 1L
    }

    init {
        this.isResizable = false
        this.caller = caller
        this.caller.isVisible = false
        layout = BorderLayout()

        //******Choosing Buttons***********
        val ok = JButton("OK")
        val cancel = JButton("Cancel")
        val buttons = JPanel()
        buttons.add(ok)
        buttons.add(cancel)
        add(buttons, BorderLayout.SOUTH)
        ok.addActionListener(this)
        cancel.addActionListener(this)

        //*******RadioButtons*****
        snakeSpeedEasy = JRadioButton("Easy")
        snakeSpeedMedium = JRadioButton("Medium", true)
        snakeSpeedHard = JRadioButton("Hard")
        val group = ButtonGroup()
        group.add(snakeSpeedEasy)
        group.add(snakeSpeedMedium)
        group.add(snakeSpeedHard)
        val radio = JPanel()
        radio.layout = FlowLayout()
        radio.add(snakeSpeedEasy)
        radio.add(snakeSpeedMedium)
        radio.add(snakeSpeedHard)
        val j = JPanel()
        j.layout = BorderLayout()
        j.border = EtchedBorder()
        j.add(JLabel("Snake Speed", JLabel.CENTER), BorderLayout.NORTH)
        j.add(radio, BorderLayout.CENTER)
        add(j, BorderLayout.NORTH)

        //**LabyrintPreviewer**
        lp = LabyrinthPreviewer()
        add(lp, BorderLayout.CENTER)
        pack()
        setLocationRelativeTo(null)
        isResizable = false
        isVisible = true
        defaultCloseOperation = EXIT_ON_CLOSE
    }
}
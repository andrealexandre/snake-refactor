package org.snake.game

import java.awt.BorderLayout
import java.awt.Color
import java.awt.Font
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.border.EtchedBorder

class GameDataDisplay : JPanel() {
    var points: JLabel
    var tempRats: JLabel

    companion object {
        const val serialVersionUID = 1L
    }

    init {
        layout = BorderLayout()
        border = EtchedBorder()
        val l = JLabel("Points: ")
        points = JLabel("0")
        tempRats = JLabel("", JLabel.CENTER)
        l.font = Font("Comic Sans MS", Font.BOLD, 12)
        points.font = Font("Comic Sans MS", 0, 12)
        tempRats.font = Font("Comic Sans MS", Font.BOLD, 15)
        tempRats.foreground = Color.BLUE
        val left = JPanel()
        left.add(l)
        left.add(points)
        add(left, BorderLayout.WEST)
        add(tempRats, BorderLayout.CENTER)
        tempRats.isVisible = false
    }
}
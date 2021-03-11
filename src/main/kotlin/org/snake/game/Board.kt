package org.snake.game

import org.snake.models.GameMatrix
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import javax.swing.JPanel
import javax.swing.border.EtchedBorder

class Board(private val matrix: GameMatrix) : JPanel() {
    override fun paintComponent(canvas: Graphics) {
        super.paintComponent(canvas)
        for (y in 0 until matrix.height()) {
            for (x in 0 until matrix.width()) {
                matrix[x, y]!!.draw(canvas, x * GRID_THICKNESS, y * GRID_THICKNESS, GRID_THICKNESS, GRID_THICKNESS)
            }
        }
    }

    fun clearLabyrinth() {
        for (y in 0 until matrix.height()) {
            for (x in 0 until matrix.width()) {
                matrix.removeView(x, y)
            }
        }
    }

    companion object {
        const val serialVersionUID = 1L
        val BOARD_DEFAULT_COLOR = Color(0, 128, 0)
        const val GRID_THICKNESS = 10
    }

    init {
        background = BOARD_DEFAULT_COLOR
        border = EtchedBorder()
        layout = null
        setSize(GRID_THICKNESS * matrix.width(), GRID_THICKNESS * matrix.height())
        preferredSize = Dimension(GRID_THICKNESS * matrix.width(), GRID_THICKNESS * matrix.height())
    }
}
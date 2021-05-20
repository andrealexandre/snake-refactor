package snake.views

import java.awt.Graphics

interface View {
    val isEmptySpace: Boolean
    fun draw(canvas: Graphics, x: Int, y: Int, width: Int, height: Int)
}
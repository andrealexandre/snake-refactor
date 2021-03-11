package org.snake.views

import org.snake.models.SnakeNode
import java.awt.Color
import java.awt.Graphics

class SnakeNodeView(val model: SnakeNode) : FigureView {
    override val isEmptySpace: Boolean
        get() = false

    override fun draw(canvas: Graphics, x: Int, y: Int, width: Int, height: Int) {
        canvas.color = SNAKE_NODE_COLOR
        canvas.fillOval(x, y, width, height)
        canvas.color = Color.BLACK
        canvas.drawOval(x, y, width, height)
        if (model.isHead) {
            canvas.color = Color.BLUE
            canvas.fillOval(x + width / 4, y + height / 4, width / 2, height / 2)
            canvas.fillOval(x + width * 3 / 4, y + height / 4, width / 2, height / 2)
        }
    }

}
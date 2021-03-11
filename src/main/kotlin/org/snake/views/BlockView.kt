package org.snake.views

import org.snake.models.Block
import java.awt.Graphics

class BlockView(val model: Block) : FigureView {
    override val isEmptySpace: Boolean
        get() = model.isEmptySpace

    override fun draw(canvas: Graphics, x: Int, y: Int, width: Int, height: Int) {
        canvas.color = BLOCK_COLOR
        canvas.fill3DRect(x, y, width, height, true)
    }
}
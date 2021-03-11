package org.snake.models

import org.snake.models.basic.Point

class Block(point: Point?) : AbstractFigure(point) {
    override val isEmptySpace: Boolean
        get() = false
}
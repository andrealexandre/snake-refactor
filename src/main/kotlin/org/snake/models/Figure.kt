package org.snake.models

import org.snake.models.basic.Point

interface Figure {
    val isEmptySpace: Boolean
    var point: Point?
}
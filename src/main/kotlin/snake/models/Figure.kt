package snake.models

import snake.models.basic.Point

interface Figure {
    val isEmptySpace: Boolean
    var point: Point?
}
package snake.models

import snake.models.basic.Point

class SnakeNode(var point: Point, var isHead: Boolean) {
    fun asHead() {
        isHead = true
    }

    fun asTail() {
        isHead = false
    }
}
package org.snake.models.basic

class PointSpace(private val xRange: Range, private val yRange: Range) {
    fun around(point: Point?): Point? {
        return if (xRange.contained(point!!.x) && yRange.contained(point.y)) {
            point
        } else {
            Point(xRange.around(point.x), yRange.around(point.y))
        }
    }
}
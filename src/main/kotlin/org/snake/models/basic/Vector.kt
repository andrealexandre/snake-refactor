package org.snake.models.basic

data class Vector(private val x: Int = 0, private val y: Int = 0) {
    val isOrigin: Boolean = x == 0 && y == 0

    fun add(point: Point): Point = Point(point.x + x, point.y + y)
    fun add(other: Vector): Vector = Vector(x + other.x, y + other.y)

    override fun toString(): String {
        return String.format("Vector{x=%d, y=%d}", x, y)
    }
}
package snake.models.basic

data class Point(val x: Int, val y: Int) {
    override fun toString(): String {
        return String.format("Point{x=%d, y=%d}", x, y)
    }

    companion object {
        val ORIGIN = Point(0, 0)
    }
}
package snake.models

import snake.models.basic.Point
import snake.views.GrassView
import snake.views.View

class GameMatrix(val width: Int, val height: Int) {
    private val matrix: Array<Array<View>> = Array(width) { Array(height) { GrassView } }

    operator fun get(x: Int, y: Int): View = matrix[x][y]
    operator fun get(point: Point): View = get(point.x, point.y)

    fun setView(view: View, position: Point) = setView(view, position.x, position.y)
    fun setView(view: View, x: Int, y: Int) { matrix[x][y] = view }

    fun removeView(point: Point) = removeView(point.x, point.y)
    fun removeView(x: Int, y: Int) { matrix[x][y] = GrassView }

    fun isEmpty(x: Int, y: Int): Boolean = matrix[x][y].isEmptySpace
    fun isEmpty(newPoint: Point): Boolean = isEmpty(newPoint.x, newPoint.y)

}
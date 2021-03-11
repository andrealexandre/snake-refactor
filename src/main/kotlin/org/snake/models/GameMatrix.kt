package org.snake.models

import org.snake.models.basic.Point
import org.snake.views.FigureView
import org.snake.views.GrassView
import java.util.*

class GameMatrix(private val width: Int, private val height: Int) {
    private val matrix: Array<Array<FigureView?>>
    operator fun get(x: Int, y: Int): FigureView? {
        return matrix[x][y]
    }

    operator fun get(point: Point): FigureView? {
        return get(point.x, point.y)
    }

    fun setView(view: FigureView?, position: Point?) {
        setView(view, position!!.x, position.y)
    }

    fun setView(view: FigureView?, x: Int, y: Int) {
        matrix[x][y] = view ?: GrassView
    }

    fun removeView(point: Point?) {
        removeView(point!!.x, point.y)
    }

    fun removeView(x: Int, y: Int) {
        matrix[x][y] = GrassView
    }

    fun width(): Int {
        return width
    }

    fun height(): Int {
        return height
    }

    fun isEmpty(x: Int, y: Int): Boolean {
        return matrix[x][y]!!.isEmptySpace
    }

    fun isEmpty(newPoint: Point?): Boolean {
        return isEmpty(newPoint!!.x, newPoint.y)
    }

    init {
        matrix = Array(width) { arrayOfNulls(height) }
        for (figureViews in matrix) {
            Arrays.fill(figureViews, GrassView)
        }
    }
}
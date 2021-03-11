package org.snake.models

import org.snake.models.basic.Point
import org.snake.models.basic.PointSpace
import org.snake.models.basic.Range
import org.snake.models.basic.Vector
import org.snake.settings.GameConfiguration
import org.snake.settings.SnakeSpeed
import org.snake.utils.FCons
import org.snake.utils.FList
import org.snake.utils.FNil
import org.snake.views.SnakeNodeView
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import java.util.*
import javax.swing.Timer

class Game(private val onChangeListener: OnChangeListener, private val matrix: GameMatrix, private val rat: Rat, config: GameConfiguration?) {
    private val pointSpace: PointSpace
    private var snakeList: FList<SnakeNodeView?>
    private val timer: Timer
    private var direction: Vector
    private var snakeSpeed = 0
    var points = 0
        private set

    fun update() {
        val last = snakeList.getLast()
        try {
            val point = last!!.model.point
            matrix.removeView(point)
        } catch (e: ArrayIndexOutOfBoundsException) {
            e.printStackTrace()
        }
        val head = snakeList.head
        head!!.model.asTail()
        val point = head.model.point
        if (rat.inPosition(point)) {
            points = if (rat.isTimed) {
                points + ADDITION_POINTS + (SnakeSpeed.EASY.snakeSpeed - snakeSpeed) * rat.numberOfRats
            } else {
                grow()
                points + ADDITION_POINTS + SnakeSpeed.EASY.snakeSpeed - snakeSpeed
            }
            onChangeListener.onPointsChange(points)
            rat.eatRat()
        }
        val newPoint = pointSpace.around(direction.add(point!!))
        if (!matrix.isEmpty(newPoint)) {
            timer.stop()
            onChangeListener.onGameOver()
            return
        }
        last!!.model.point = newPoint
        matrix.setView(last, newPoint)
        snakeList = FCons(last, snakeList)
        last.model.asHead()
        snakeList = snakeList.removeLast()
        onChangeListener.onGameMatrixUpdate()
    }

    fun restart() {
        snakeList.foreach { v: SnakeNodeView? ->
            matrix.removeView(v!!.model.point)
            Unit
        }
        timer.delay = snakeSpeed
        direction = Vector()
        points = 0
        onChangeListener.onPointsChange(points)
        snakeList = FNil
        val rn = Random()
        val x = rn.nextInt(matrix.width())
        val y = rn.nextInt(matrix.height())
        val sv = SnakeNodeView(SnakeNode(Point(x, y), true))
        snakeList = FCons(sv, snakeList)
        matrix.setView(sv, x, y)
    }

    fun stopSnake() {
        timer.stop()
    }

    fun startSnake() {
        timer.start()
    }

    fun moveSnake(keyCode: Int) {
        if (direction.isOrigin) {
            timer.start()
        }
        var newDirection: Vector? = null
        when (keyCode) {
            UP -> newDirection = Vector(0, -1)
            DOWN -> newDirection = Vector(0, +1)
            LEFT -> newDirection = Vector(-1, 0)
            RIGHT -> newDirection = Vector(+1, 0)
        }
        if (newDirection != null) {
            val result = direction.add(newDirection)
            if (!result!!.isOrigin) {
                direction = newDirection
            }
        }
    }

    fun grow() {
        val last = snakeList.getLast()
        val newPoint = pointSpace.around(direction.add(last!!.model.point!!))
        val sv = SnakeNodeView(SnakeNode(newPoint, false))
        snakeList = FCons(sv, snakeList)
    }

    interface OnChangeListener {
        fun onPointsChange(points: Int)
        fun onGameOver()
        fun onGameMatrixUpdate()
    }

    companion object {
        private const val UP = KeyEvent.VK_UP
        private const val DOWN = KeyEvent.VK_DOWN
        private const val LEFT = KeyEvent.VK_LEFT
        private const val RIGHT = KeyEvent.VK_RIGHT
        private const val ADDITION_POINTS = 5
    }

    init {
        pointSpace = PointSpace(Range(matrix.width()), Range(matrix.height()))
        direction = Vector(0, 0)
        snakeSpeed = config?.snakeSpeed?.snakeSpeed ?: SnakeSpeed.MEDIUM.snakeSpeed
        timer = Timer(snakeSpeed) { event: ActionEvent? -> update() }
        val rn = Random()
        val x = rn.nextInt(matrix.width())
        val y = rn.nextInt(matrix.height())
        val snakeNode = SnakeNodeView(SnakeNode(Point(x, y), true))
        snakeList = FCons(snakeNode, FNil)
        matrix.setView(snakeNode, x, y)
    }
}
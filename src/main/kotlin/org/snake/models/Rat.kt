package org.snake.models

import org.snake.game.GameDataDisplay
import org.snake.models.basic.Point
import org.snake.views.FigureView
import java.awt.Color
import java.awt.Graphics
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.util.*
import javax.swing.Timer

class Rat(private val board: GameMatrix, private val display: GameDataDisplay) : ActionListener {
    var position: Point? = null
        private set
    private var timer: Timer? = null
    private val random: Random
    var numberOfRats = 0
        private set
    private var count = 0
    fun setRandomRat() {
        var x: Int
        var y: Int
        do {
            x = random.nextInt(board.width())
            y = random.nextInt(board.height())
        } while (!board.isEmpty(x, y))
        if (random.nextInt(101) > 95) {
            timer = Timer(SECOND, this)
            count = DISAPPEARING_SECONDS
            numberOfRats = random.nextInt(5) + 1
            display.tempRats.text = "" + count
            display.tempRats.isVisible = true
            timer!!.start()
        }
        position = Point(x, y)
        board.setView(RatFigureView(), position)
    }

    val isTimed: Boolean
        get() = timer != null

    override fun actionPerformed(a: ActionEvent) {
        if (count-- == 0) {
            timer!!.stop()
            removeRat()
            display.tempRats.isVisible = false
            timer = null
        }
        display.tempRats.text = "" + count
    }

    fun restart() {
        board.removeView(position)
        setRandomRat()
    }

    fun removeRat() {
        board.removeView(position)
        setRandomRat()
    }

    fun eatRat() {
        if (timer != null) {
            timer!!.stop()
            display.tempRats.isVisible = false
            timer = null
        }
        setRandomRat()
    }

    fun inPosition(point: Point?): Boolean {
        return position == point
    }

    private inner class RatFigureView : FigureView {
        private val BROWN = Color(128, 64, 0)
        override val isEmptySpace: Boolean
            get() = true

        override fun draw(canvas: Graphics, x: Int, y: Int, width: Int, height: Int) {
            if (timer == null) {
                canvas.color = BROWN
            } else {
                canvas.color = Color.yellow
            }
            canvas.fillOval(x, y, width, height)
            canvas.color = Color.BLACK
            canvas.drawOval(x, y, width, height)
        }
    }

    companion object {
        private const val SECOND = 1000
        const val DISAPPEARING_SECONDS = 10
    }

    init {
        random = Random()
        setRandomRat()
    }
}
package snake.models

import snake.game.GameDataDisplay
import snake.models.basic.Point
import snake.views.RatView
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.util.*
import javax.swing.Timer

class Rat(private val board: GameMatrix, private val display: GameDataDisplay) : ActionListener {
    var position: Point? = null
        private set
    private val timer: Timer = Timer(SECOND, this)
    private val random: Random = Random()
    var numberOfRats = 0
        private set
    private var count = 0

    private fun setRandomRat() {
        var x: Int
        var y: Int
        do {
            x = random.nextInt(board.width)
            y = random.nextInt(board.height)
        } while (!board.isEmpty(x, y))
        if (random.nextInt(100) > 90) {
            count = DISAPPEARING_SECONDS
            numberOfRats = random.nextInt(5) + 1
            display.tempRats.text = "" + count
            display.tempRats.isVisible = true
            timer.start()
        }
        val position = Point(x, y)
        board.setView(RatView(isTimed), position)
        this.position = position
    }

    val isTimed: Boolean = timer.isRunning

    override fun actionPerformed(a: ActionEvent) {
        if (count-- == 0) {
            timer.stop()
            removeRat()
            display.tempRats.isVisible = false
        }
        display.tempRats.text = "" + count
    }

    fun restart() {
        board.removeView(position!!)
        setRandomRat()
    }

    fun removeRat() {
        board.removeView(position!!)
        setRandomRat()
    }

    fun eatRat() {
        if (timer.isRunning) {
            timer.stop()
            display.tempRats.isVisible = false
        }
        setRandomRat()
    }

    fun inPosition(point: Point?): Boolean {
        return position == point
    }

    companion object {
        private const val SECOND = 1000
        const val DISAPPEARING_SECONDS = 10
    }

    init {
        setRandomRat()
    }
}
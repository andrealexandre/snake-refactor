package snake.views

import java.awt.Graphics

object GrassView : View {
    override val isEmptySpace: Boolean = true

    override fun draw(canvas: Graphics, x: Int, y: Int, width: Int, height: Int) {
        // Do nothing
    }
}
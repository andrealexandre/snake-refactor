package snake.views

import java.awt.Color
import java.awt.Graphics

class RatView(val special: Boolean) : View {
    private val regularRatColor = Color(128,64,0)
    private val specialRatColor = Color.yellow

    override val isEmptySpace: Boolean = true

    override fun draw(canvas: Graphics, x:Int, y:Int, width:Int, height:Int){
        canvas.color = if(special) specialRatColor else regularRatColor
        canvas.fillOval(x,y,width,height)
        canvas.color=Color.BLACK
        canvas.drawOval(x,y,width,height)
    }
}